/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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

   public static void setDefaultProvider(ClassLoaderProvider p)
   {
      provider = p;
   }

   public static ClassLoaderProvider getDefaultProvider()
   {
      return provider;
   }

   /**
    * Return the ClassLoader instance having visibility over the application server ws subsystem only
    * 
    * @return
    */
   public abstract ClassLoader getWebServiceSubsystemClassLoader();

   /**
    * Return the ClassLoader instance having visibility over the all server side ws libraries (for JAXWS usage)
    * 
    * @return
    */
   public abstract ClassLoader getServerIntegrationClassLoader();

   /**
    * Return the ClassLoader instance having visibility over the all server side ws libraries (for JAXRPC usage)
    * 
    * @return
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
}
