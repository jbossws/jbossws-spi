/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A ClassLoader with caches for resource lookup through services (META-INF/services)
 * 
 * @author alessio.soldano@jboss.com
 * @since 18-Sep-2009
 *
 */
public class ResourceCachingClassLoader extends ClassLoader
{
   private ConcurrentMap<String, String> servicesMap = new ConcurrentHashMap<String, String>();
   
   public ResourceCachingClassLoader(ClassLoader parent)
   {
      super(parent);
      //clear maps to same memory, as this constructor is also called when this classloader becomes a father (hence the cache is not useful anymore)
      servicesMap.clear();
   }
   
   public boolean hasResourceNameFromServices(String key)
   {
      return servicesMap.containsKey(key);
   }
   
   public String getResourceNameFromServices(String key)
   {
      return servicesMap.get(key);
   }
   
   public void setResourceNameFromServices(String key, String value)
   {
      servicesMap.put(key, value);
   }
}
