/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.deployment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A general extendible artifact; please note the 'attachments' and 'properties' fields are not meant to be modified concurrently,
 * as they are initialied to plain HashMap instances. Extensions of this class should override the methods accessing those fields
 * and add synchronized keyword.  
 * Most of the time, though, there will only be a single thread writing/deleting stuff in this class at the same time, for example
 * because that only happens during deployment processing (which executes in the same thread for deployment processors / aspects
 * and only the last aspect installs a service for each endpoint that run very limited stuff in different threads).
 * Visibility is ensured by the fields being final.
 * 
 * @author Thomas.Diesler@jboss.com
 * @author alessio.soldano@jboss.com
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 * @since 20-Apr-2007 
 */
public abstract class AbstractExtensible implements Extensible
{
   private final Map<Class<?>, Object> attachments;
   private final Map<String, Object> properties;
   private final Map<String, String> runtimeProperties;
   
   public AbstractExtensible()
   {
      this.attachments  = new HashMap<Class<?>, Object>();
      this.properties = new HashMap<String, Object>();
      this.runtimeProperties = new HashMap<String, String>(10);
   }
   
   public AbstractExtensible(int initialAttachmentsSize, int initialPropertiesSize)
   {
      this.attachments  = new HashMap<Class<?>, Object>(initialAttachmentsSize);
      this.properties = new HashMap<String, Object>(initialPropertiesSize);
      this.runtimeProperties = new HashMap<String, String>(10);
   }
   
   public Collection<Object> getAttachments()
   {
      return attachments.values();
   }
   
   @SuppressWarnings("unchecked")
   public <T> T getAttachment(Class<T> clazz)
   {
      return (T)attachments.get(clazz);
   }
   
   @SuppressWarnings("unchecked")
   public <T> T addAttachment(Class<T> clazz, Object obj)
   {
      return (T)attachments.put(clazz, obj);
   }

   @SuppressWarnings("unchecked")
   public <T> T removeAttachment(Class<T> key)
   {
      return (T)attachments.remove(key);
   }
   
   public Set<String> getProperties()
   {
      return properties.keySet();
   }

   public Object getProperty(String key)
   {
      return properties.get(key);
   }

   public void removeProperty(String key)
   {
      properties.remove(key);
   }

   public void setProperty(String key, Object value)
   {
      properties.put(key, value);
   }

   public void setProperties(Map<String, Object> props)
   {
      properties.putAll(props);
   }
   
   /** Get runtime changeable property*/
   public String getRuntimeProperty(String key) {
	   return this.runtimeProperties.get(key);
   }
   
   /** Set runtime changeable property*/
   public void setRuntimeProperty(String key, String value) {
	   this.runtimeProperties.put(key, value);
   }
   
   /** Remove a runtime changeable property */
   public void removeRuntimeProperty(String key) {
	   this.runtimeProperties.remove(key);
   }
   
   /** Get the set of runtime changeable property names */
   public Map<String, String> getRuntimeProperties() {
	   return this.runtimeProperties;
   }
   

}
