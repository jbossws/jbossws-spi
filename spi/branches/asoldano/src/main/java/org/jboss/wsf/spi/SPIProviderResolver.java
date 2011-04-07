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

import org.jboss.wsf.spi.util.ServiceLoader;

/**
 * Locates an SPIProvider.
 *
 * @author Heiko.Braun@jboss.com
 *         Created: Jul 18, 2007
 */
public abstract class SPIProviderResolver
{
   public final static String DEFAULT_SPI_PROVIDER_RESOLVER = "org.jboss.wsf.framework.DefaultSPIProviderResolver";
   
   /**
    * Get the SPIProviderResolver instance using the thread context classloader for lookup
    * 
    * @return
    */
   public static SPIProviderResolver getInstance()
   {
      return getInstance(SecurityActions.getContextClassLoader());
   }
   
   /**
    * Get the SPIProviderResolver instance using the provided classloader for lookup
    * 
    * @return
    */
   public static SPIProviderResolver getInstance(ClassLoader cl)
   {
      SPIProviderResolver resolver = (SPIProviderResolver)ServiceLoader.loadService(SPIProviderResolver.class.getName(), DEFAULT_SPI_PROVIDER_RESOLVER, cl);
      return resolver;
   }

   public abstract SPIProvider getProvider();
   
}
