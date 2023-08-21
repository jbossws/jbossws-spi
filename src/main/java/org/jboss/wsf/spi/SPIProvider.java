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
