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
 * @since 20-Apr-2007 
 */
public abstract class AbstractExtensible implements Extensible
{
   private final Map<Class<?>, Object> attachments;
   private final Map<String, Object> properties;
   
   public AbstractExtensible()
   {
      this.attachments  = new HashMap<Class<?>, Object>();
      this.properties = new HashMap<String, Object>();
   }
   
   public AbstractExtensible(int initialAttachmentsSize, int initialPropertiesSize)
   {
      this.attachments  = new HashMap<Class<?>, Object>(initialAttachmentsSize);
      this.properties = new HashMap<String, Object>(initialPropertiesSize);
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

}
