/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi;

import org.jboss.wsf.spi.classloading.ClassLoaderProvider;

/**
 * Gives access to the SPI implementation.
 *
 * @author Heiko.Braun@jboss.com
 *         Created: Jul 18, 2007
 */
public abstract class SPIProvider
{
   private static SPIProvider me;

   /**
    * Gets the a singleton reference to the SPIProvider returned by the SPIProviderResolver
    * retrieved using the default server integration classloader.
    * 
    * @return  this class instance
    */
   public static SPIProvider getInstance()
   {
      if (me == null)
      {
         final ClassLoader cl = ClassLoaderProvider.getDefaultProvider().getServerIntegrationClassLoader();
         me = SPIProviderResolver.getInstance(cl).getProvider();
      }
      return me;
   }
   
   /**
    * Gets the specified SPI, using the current thread context classloader
    * 
    * @param <T>   type of spi class
    * @param spiType   spi class to retrieve
    * @return   object
    */
   public <T> T getSPI(Class<T> spiType)
   {
      return getSPI(spiType, SecurityActions.getContextClassLoader());
   }

   /**
    * Gets the specified SPI, using the provided classloader
    * 
    * @param <T>   type of spi class
    * @param spiType   spi class to retrieve
    * @param loader    classloader to user
    * @return   object
    */
   public abstract <T> T getSPI(Class<T> spiType, ClassLoader loader);
}
