/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.wsf.spi.classloading;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;

/**
 * SPI for getting AS classloaders
 * 
 * @author alessio.soldano@jboss.com
 * @since 18-Jan-2016
 *
 */
public abstract class JAXRSClassLoaderProvider
{
   private static final RuntimePermission SET_DEFAULT_CLASSLOADER_PROVIDER = new RuntimePermission("org.jboss.wsf.spi.SET_DEFAULT_CLASSLOADER_PROVIDER");
   
   private static JAXRSClassLoaderProvider provider = new JAXRSClassLoaderProvider()
   {
      @Override
      public ClassLoader getJAXRSSubsystemClassLoader()
      {
         return getContextClassLoader();
      }

      @Override
      public ClassLoader getServerIntegrationClassLoader()
      {
         return getContextClassLoader();
      }

   };
   private static volatile boolean set = false;

   public static void setDefaultProvider(JAXRSClassLoaderProvider p)
   {
      checkPermission(SET_DEFAULT_CLASSLOADER_PROVIDER);
      provider = p;
      set = true;
   }

   public static JAXRSClassLoaderProvider getDefaultProvider()
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
   public abstract ClassLoader getJAXRSSubsystemClassLoader();

   /**
    * Return the ClassLoader instance having visibility over the all server side ws libraries (for JAXWS usage)
    * 
    * @return   classloader
    */
   public abstract ClassLoader getServerIntegrationClassLoader();

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
         AccessController.checkPermission(permission);
      }
   }
}
