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

/**
 * SPI for getting AS classloaders
 * 
 * @author alessio.soldano@jboss.com
 * @since 06-Apr-2011
 *
 */
public abstract class ClassLoaderProvider
{
   private static ClassLoaderProvider provider = null;
   
   public static void setDefaultProvider(ClassLoaderProvider p)
   {
      provider = p;
   }
   
   public static ClassLoaderProvider getDefaultProvider()
   {
      return provider;
   }
   
   public abstract ClassLoader getWebServiceSubsystemClassLoader();
   
   public abstract ClassLoader getServerIntegrationClassLoader();
   
}
