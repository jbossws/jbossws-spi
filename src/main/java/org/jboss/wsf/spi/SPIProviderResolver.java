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

import org.jboss.ws.api.util.ServiceLoader;

/**
 * Locates an SPIProvider.
 *
 * @author Heiko.Braun@jboss.com
 *         Created: Jul 18, 2007
 */
public abstract class SPIProviderResolver
{
   public final static String DEFAULT_SPI_PROVIDER_RESOLVER = "org.jboss.ws.common.spi.DefaultSPIProviderResolver";
   
   /**
    * Get the SPIProviderResolver instance using the thread context classloader for lookup
    * 
    * @return  instance of this class
    */
   public static SPIProviderResolver getInstance()
   {
      return getInstance(SecurityActions.getContextClassLoader());
   }
   
   /**
    * Get the SPIProviderResolver instance using the provided classloader for lookup
    *
    * @param cl  classloader to use for lookup
    * @return   instance of this class
    */
   public static SPIProviderResolver getInstance(ClassLoader cl)
   {
      SPIProviderResolver resolver = (SPIProviderResolver)ServiceLoader.loadService(SPIProviderResolver.class.getName(), DEFAULT_SPI_PROVIDER_RESOLVER, cl);
      return resolver;
   }

   public abstract SPIProvider getProvider();
   
}
