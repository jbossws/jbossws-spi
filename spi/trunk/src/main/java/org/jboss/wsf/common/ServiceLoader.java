/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.wsf.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

// $Id$

/**
 * Load a service class using this ordered lookup procedure
 *
 * @author Thomas.Diesler@jboss.com
 * @since 14-Dec-2006
 */
public abstract class ServiceLoader
{
   /**
    * This method uses the algorithm below using the JAXWS Provider as an example.
    * 
    * 1. If a resource with the name of META-INF/services/javax.xml.ws.spi.Provider exists, then
    * its first line, if present, is used as the UTF-8 encoded name of the implementation class.
    * 
    * 2. If the ${java.home}/lib/jaxws.properties file exists and it is readable by the 
    * java.util.Properties.load(InputStream) method and it contains an entry whose key is 
    * javax.xml.ws.spi.Provider, then the value of that entry is used as the name of the implementation class.
    * 
    * 3. If a system property with the name javax.xml.ws.spi.Provider is defined, then its value is used
    * as the name of the implementation class.
    * 
    * 4. Finally, a default implementation class name is used.
    */
   public static Object loadService(String propertyName, String defaultFactory)
   {
      Object factory = loadFromServices(propertyName, null);
      if (factory == null)
      {
         factory = loadFromPropertiesFile(propertyName, null);
      }
      if (factory == null)
      {
         factory = loadFromSystemProperty(propertyName, defaultFactory);
      }
      return factory;
   }
   
   /** Use the Services API (as detailed in the JAR specification), if available, to determine the classname.
    */
   public static Object loadFromServices(String propertyName, String defaultFactory)
   {
      Object factory = null;
      String factoryName = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      // Use the Services API (as detailed in the JAR specification), if available, to determine the classname.
      String filename = "META-INF/services/" + propertyName;
      InputStream inStream = loader.getResourceAsStream(filename);
      if (inStream != null)
      {
         try
         {
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            factoryName = br.readLine();
            br.close();
            if (factoryName != null)
            {
               Class factoryClass = loader.loadClass(factoryName);
               factory = factoryClass.newInstance();
            }
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load " + propertyName + ": " + factoryName, t);
         }
      }

      // Use the default factory implementation class.
      if (factory == null && defaultFactory != null)
      {
         factory = loadDefault(defaultFactory);
      }

      return factory;
   }

   /** Use the system property
    */
   public static Object loadFromSystemProperty(String propertyName, String defaultFactory)
   {
      Object factory = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      PrivilegedAction action = new PropertyAccessAction(propertyName);
      String factoryName = (String)AccessController.doPrivileged(action);
      if (factoryName != null)
      {
         try
         {
            //if(log.isDebugEnabled()) log.debug("Load from system property: " + factoryName);
            Class factoryClass = loader.loadClass(factoryName);
            factory = factoryClass.newInstance();
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load " + propertyName + ": " + factoryName, t);
         }
      }

      // Use the default factory implementation class.
      if (factory == null && defaultFactory != null)
      {
         factory = loadDefault(defaultFactory);
      }

      return factory;
   }

   /**
    * Use the properties file "${java.home}/lib/jaxws.properties" in the JRE directory.
    * This configuration file is in standard java.util.Properties format and contains the 
    * fully qualified name of the implementation class with the key being the system property defined above.
    */
   public static Object loadFromPropertiesFile(String propertyName, String defaultFactory)
   {
      Object factory = null;
      String factoryName = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      // Use the properties file "lib/jaxm.properties" in the JRE directory.
      // This configuration file is in standard java.util.Properties format and contains the fully qualified name of the implementation class with the key being the system property defined above.
      PrivilegedAction action = new PropertyAccessAction("java.home");
      String javaHome = (String)AccessController.doPrivileged(action);
      File jaxmFile = new File(javaHome + "/lib/jaxws.properties");
      if (jaxmFile.exists())
      {
         try
         {
            action = new PropertyFileAccessAction(jaxmFile.getCanonicalPath());
            Properties jaxmProperties = (Properties)AccessController.doPrivileged(action);
            factoryName = jaxmProperties.getProperty(propertyName);
            if (factoryName != null)
            {
               //if(log.isDebugEnabled()) log.debug("Load from " + jaxmFile + ": " + factoryName);
               Class factoryClass = loader.loadClass(factoryName);
               factory = factoryClass.newInstance();
            }
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load " + propertyName + ": " + factoryName, t);
         }
      }

      // Use the default factory implementation class.
      if (factory == null && defaultFactory != null)
      {
         factory = loadDefault(defaultFactory);
      }

      return factory;
   }

   private static Object loadDefault(String defaultFactory)
   {
      Object factory = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      // Use the default factory implementation class.
      if (defaultFactory != null)
      {
         try
         {
            //if(log.isDebugEnabled()) log.debug("Load from default: " + factoryName);
            Class factoryClass = loader.loadClass(defaultFactory);
            factory = factoryClass.newInstance();
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load: " + defaultFactory, t);
         }
      }

      return factory;
   }

   private static class PropertyAccessAction implements PrivilegedAction
   {
      private String name;

      PropertyAccessAction(String name)
      {
         this.name = name;
      }

      public Object run()
      {
         return System.getProperty(name);
      }
   }

   private static class PropertyFileAccessAction implements PrivilegedAction
   {
      private String filename;

      PropertyFileAccessAction(String filename)
      {
         this.filename = filename;
      }

      public Object run()
      {
         try
         {
            InputStream inStream = new FileInputStream(filename);
            Properties props = new Properties();
            props.load(inStream);
            return props;
         }
         catch (IOException ex)
         {
            throw new SecurityException("Cannot load properties: " + filename, ex);
         }
      }
   }
}
