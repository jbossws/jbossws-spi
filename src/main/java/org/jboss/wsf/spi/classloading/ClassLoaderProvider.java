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
package org.jboss.wsf.spi.classloading;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;

/**
 * SPI for getting AS classloaders
 * 
 * @author alessio.soldano@jboss.com
 * @since 06-Apr-2011
 *
 */
public abstract class ClassLoaderProvider
{
   private static final RuntimePermission SET_DEFAULT_CLASSLOADER_PROVIDER = new RuntimePermission("org.jboss.wsf.spi.SET_DEFAULT_CLASSLOADER_PROVIDER");
   
   private static ClassLoaderProvider provider = new ClassLoaderProvider()
   {
      @Override
      public ClassLoader getWebServiceSubsystemClassLoader()
      {
         return getContextClassLoader();
      }

      @Override
      public ClassLoader getServerIntegrationClassLoader()
      {
         return getContextClassLoader();
      }

      @Override
      public ClassLoader getServerJAXRPCIntegrationClassLoader()
      {
         return getContextClassLoader();
      }
   };
   private static volatile boolean set = false;

   public static void setDefaultProvider(ClassLoaderProvider p)
   {
      checkPermission(SET_DEFAULT_CLASSLOADER_PROVIDER);
      provider = p;
      set = true;
   }

   public static ClassLoaderProvider getDefaultProvider()
   {
      return provider;
   }
   
   public static boolean isSet()
   {
      return set;
   }

   /**
    * Return the ClassLoader instance having visibility over the application server ws subsystem only
    * 
    * @return   classloader
    */
   public abstract ClassLoader getWebServiceSubsystemClassLoader();

   /**
    * Return the ClassLoader instance having visibility over the all server side ws libraries (for JAXWS usage)
    * 
    * @return   classloader
    */
   public abstract ClassLoader getServerIntegrationClassLoader();

   /**
    * Return the ClassLoader instance having visibility over the all server side ws libraries (for JAXRPC usage)
    * 
    * @return   classloader
    */
   public abstract ClassLoader getServerJAXRPCIntegrationClassLoader();

   static ClassLoader getContextClassLoader()
   {
      if (System.getSecurityManager() == null)
      {
         return Thread.currentThread().getContextClassLoader();
      }
      else
      {
         return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>()
         {
            public ClassLoader run()
            {
               return Thread.currentThread().getContextClassLoader();
            }
         });
      }
   }

   private static void checkPermission(final Permission permission)
   {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null)
      {
         securityManager.checkPermission(permission);
      }
   }
}
