/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.wsf.spi.util;

import static org.jboss.wsf.spi.Loggers.ROOT_LOGGER;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.wsf.spi.Messages;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;

/**
 * Load resources through a URLClassLoader.<br>
 * NOTE: The associated classloader doesn't do parent delegation.
 *
 *
 * @author Heiko.Braun@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 25.01.2007
 */
public class URLLoaderAdapter implements UnifiedVirtualFile
{
   private static final long serialVersionUID = 8263115387770740414L;
   
   private final URL rootURL;
   private final URL resourceURL;
   private transient volatile URLClassLoader loader;
   private static final String jarFileSeparator = "/";

   public URLLoaderAdapter(URL rootURL)
   {
      this.rootURL = rootURL;
      this.resourceURL = null;
   }

   private URLLoaderAdapter(URL rootURL, URLClassLoader loader, URL resourceURL)
   {
      this.rootURL = rootURL;
      this.resourceURL = resourceURL;
      this.loader = loader;
   }

   private UnifiedVirtualFile findChild(String resourcePath, boolean throwExceptionIfNotFound) throws IOException
   {
      URL resourceURL = null;
      if (resourcePath != null)
      {
         // Try the child as URL
         try
         {
            resourceURL = new URL(resourcePath);
         }
         catch (MalformedURLException ex)
         {
            // ignore
         }

         // Try the filename as File
         if (resourceURL == null)
         {
            try
            {
               File file = new File(resourcePath);
               if (file.exists())
                  resourceURL = file.toURI().toURL();
            }
            catch (MalformedURLException e)
            {
               // ignore
            }
         }

         // Try the filename as Resource
         if (resourceURL == null)
         {
            try
            {
               resourceURL = getResourceLoader().getResource(resourcePath);
            }
            catch (Exception ex)
            {
               // ignore
            }
         }
      }

      if (resourceURL == null)
      {
         if (throwExceptionIfNotFound)
         {
            throw Messages.MESSAGES.cannotGetURLFor(resourcePath);
         }
         else
         {
            if (ROOT_LOGGER.isTraceEnabled()) ROOT_LOGGER.cannotGetURLFor(resourcePath);
            return null;
         }
      }

      return new URLLoaderAdapter(rootURL, loader, resourceURL);
   }

   public UnifiedVirtualFile findChild(String resourcePath) throws IOException
   {
      return findChild(resourcePath, true);
   }

   public UnifiedVirtualFile findChildFailSafe(String resourcePath)
   {
      try
      {
         return findChild(resourcePath, false);
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   public URL toURL()
   {
      if (resourceURL != null)
         return resourceURL;
      else
         return rootURL;
   }

   private URLClassLoader getResourceLoader()
   {
      if (loader == null)
      {
         loader = new URLClassLoader(new URL[] { rootURL });
      }
      return loader;
   }
   
   public List<UnifiedVirtualFile> getChildren() throws IOException
   {
      List<UnifiedVirtualFile> list = new LinkedList<UnifiedVirtualFile>();

      URL url = toURL();
      
      if (url.getProtocol().equals("jar"))
      {
         String urlString = url.toExternalForm();
         String jarRoot = urlString.substring(4, urlString.indexOf("ar!") + 2);
         String path = urlString.contains("!") ? urlString.substring(urlString.lastIndexOf("!") + 2) : "";
         if (path.endsWith(jarFileSeparator))
            path = path.substring(0, path.lastIndexOf(jarFileSeparator));
         
         try
         {
            String folder = jarRoot.substring(5,jarRoot.lastIndexOf(File.separator));
            String filename = jarRoot.substring(jarRoot.lastIndexOf(File.separator)+1);
            final File jar = new File(folder, filename);
            
            PrivilegedAction<JarFile> action = new PrivilegedAction<JarFile>()
            {
               public JarFile run()
               {
                  try
                  {
                     return new JarFile(jar);
                  }
                  catch (IOException e)
                  {
                     throw new RuntimeException(e);
                  }
               }
            };
            JarFile jarFile = AccessController.doPrivileged(action);
            
            if (jar.canRead())
            {
               Enumeration<JarEntry> entries = jarFile.entries();
               List<String> pathMatch = new LinkedList<String>();
               List<String> finalMatch = new LinkedList<String>();
               while (entries.hasMoreElements())
               {
                  JarEntry entry = entries.nextElement();
                  String name = entry.getName();
                  //keep entries starting with the current resource path (exclude inner classes and the current file)
                  if (name.startsWith(path + jarFileSeparator) && (name.length() > path.length() + 1) && !name.contains("$"))
                     pathMatch.add(name.substring(path.length() + 1));
               }
               
               for (String s : pathMatch)
               {
                  //do not go deeper than the current dir
                  if (!s.contains(jarFileSeparator) || s.indexOf(jarFileSeparator) == s.length() - 1)
                     finalMatch.add(s);
               }
               for (String s : finalMatch)
               {
                  URL sUrl = new URL(urlString + jarFileSeparator + s);
                  list.add(new URLLoaderAdapter(rootURL, loader, sUrl));
               }
            }
         }
         catch (Exception e)
         {
            ROOT_LOGGER.cannotGetChildrenForResource(e, url);
         }
      }
      else //std file/dir
      {
         try
         {
            File file = new File(url.toURI());
            if (file.exists() && file.isDirectory())
            {
               File[] files = file.listFiles();
               if (files != null)
               {
                  for (File f : files)
                  {
                     list.add(new URLLoaderAdapter(rootURL, loader, f.toURI().toURL()));
                  }
               }
            }
         }
         catch (Exception e)
         {
            ROOT_LOGGER.cannotGetChildrenForResource(e, url);
         }
      }
      return list;
   }
   
   public String getName()
   {
      String name = null;
      try
      {
         String filename = toURL().getFile();
         File f = new File(filename);
         name = f.getName();
         if (f.isDirectory() || (toURL().getProtocol().equals("jar") && filename.endsWith(jarFileSeparator)))
            name = name + jarFileSeparator;
      }
      catch (Exception e)
      {
         ROOT_LOGGER.cannotGetNameForResource(e, toURL());
      }
      return name;
   }
}
