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
import java.util.Map;
import java.util.Set;

/**
 * A general extendible artifact 
 * 
 * @author Thomas.Diesler@jboss.com
 * @since 20-Apr-2007 
 */
public interface Extensible
{

   /**
    * Add arbitrary attachments
    *
    * @param key     identifier
    * @param value   value
    * @param <T>     class type
    * @return        added attachment
    */
   <T> T addAttachment(Class<T> key, Object value);

   /**
    * Get arbitrary attachments
    *
    * @param <T>  class type of attachments
    * @return     collection of attachments
    */
   <T> Collection<T> getAttachments();

   /**
    * Get an arbitrary attachment
    *
    * @param key  identifier
    * @param <T>  class type of attachments
    * @return     attachment found
    */
   <T> T getAttachment(Class<T> key);

   /**
    * Remove arbitrary attachments
    * @param key     identifier
    * @param <T>   class type of attachments
    * @return      attachment removed
    */
   <T> T removeAttachment(Class<T> key);

   /**
    * Get an property
    *
    * @param key  property identifier
    * @return     found property
    */
   Object getProperty(String key);

   /**
    * Set a property
    * @param key     property identifier
    * @param value   property value
    */
   void setProperty(String key, Object value);

   /**
    * Remove a property
    * @param key     property identifier
    */
   void removeProperty(String key);

   /**
    * Get the set of property names
    * @return  set of property values
    */
   Set<String> getProperties();

   /**
    * Set a map of properties
    * @param props   map to retain
    */
   void setProperties(Map<String, Object> props);
}
