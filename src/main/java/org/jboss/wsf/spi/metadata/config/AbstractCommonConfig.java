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
package org.jboss.wsf.spi.metadata.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.wsf.spi.Messages;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData.HandlerType;

/**
 * A common configuration 
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public abstract class AbstractCommonConfig implements CommonConfig
{
   private final String configName;
   private final Map<String, Feature> features;
   private final Map<String, String> properties;
   private final List<UnifiedHandlerChainMetaData> preHandlerChains;
   private final List<UnifiedHandlerChainMetaData> postHandlerChains;
   private final Map<String, Object> attachments;
   
   protected AbstractCommonConfig(String configName,
                                  List<UnifiedHandlerChainMetaData> preHandlerChains,
                                  List<UnifiedHandlerChainMetaData> postHandlerChains,
                                  Map<String, String> properties,
                                  Map<String, Feature> features)
   {
      super();
      this.configName = configName;
      if (features != null && !features.isEmpty()) {
         this.features = Collections.unmodifiableMap(features);
      } else {
         this.features = Collections.emptyMap();
      }
      if (properties != null && !properties.isEmpty()) {
         this.properties = Collections.unmodifiableMap(properties);
      } else {
         this.properties = Collections.emptyMap();
      }
      if (preHandlerChains != null && !preHandlerChains.isEmpty()) {
         this.preHandlerChains = Collections.unmodifiableList(preHandlerChains);
      } else {
         this.preHandlerChains = Collections.emptyList();
      }
      if (postHandlerChains != null && !postHandlerChains.isEmpty()) {
         this.postHandlerChains = Collections.unmodifiableList(postHandlerChains);
      } else {
         this.postHandlerChains = Collections.emptyList();
      }
      this.attachments = new HashMap<String, Object>();
   }
   
   protected AbstractCommonConfig(AbstractCommonConfig base, AbstractCommonConfig addon)
   {
      super();
      this.configName = base.getConfigName();
      if (addon.features != null && !addon.features.isEmpty())
      {
         Map<String, Feature> map;
         if (base.features.isEmpty())
         {
            map = addon.features;
         }
         else
         {
            map = new HashMap<String, Feature>(base.features);
            map.putAll(addon.features);
         }
         this.features = Collections.unmodifiableMap(map);
      }
      else
      {
         this.features = Collections.emptyMap();
      }
      if (addon.properties != null && !addon.properties.isEmpty())
      {
         Map<String, String> map;
         if (base.properties.isEmpty())
         {
            map = addon.properties;
         }
         else
         {
            map = new HashMap<String, String>(base.properties);
            map.putAll(addon.properties);
         }
         this.properties = Collections.unmodifiableMap(map);
      }
      else
      {
         this.properties = Collections.emptyMap();
      }
      if (addon.preHandlerChains != null && !addon.preHandlerChains.isEmpty())
      {
         List<UnifiedHandlerChainMetaData> list;
         if (base.preHandlerChains.isEmpty())
         {
            list = addon.preHandlerChains;
         }
         else
         {
            list = new ArrayList<UnifiedHandlerChainMetaData>(base.preHandlerChains);
            list.addAll(addon.preHandlerChains);
         }
         this.preHandlerChains = Collections.unmodifiableList(list);
      }
      else
      {
         this.preHandlerChains = Collections.emptyList();
      }
      if (addon.postHandlerChains != null && !addon.postHandlerChains.isEmpty())
      {
         List<UnifiedHandlerChainMetaData> list;
         if (base.postHandlerChains.isEmpty())
         {
            list = addon.postHandlerChains;
         }
         else
         {
            list = new ArrayList<UnifiedHandlerChainMetaData>(base.postHandlerChains);
            list.addAll(preHandlerChains);
         }
         this.postHandlerChains = Collections.unmodifiableList(list);
      }
      else
      {
         this.postHandlerChains = Collections.emptyList();
      }
      if (addon.attachments != null && !addon.attachments.isEmpty())
      {
         Map<String, Object> map;
         if (base.attachments.isEmpty())
         {
            map = addon.attachments;
         }
         else
         {
            map = new HashMap<String, Object>(base.attachments);
            map.putAll(addon.attachments);
         }
         this.attachments = new HashMap<String, Object>(map);
      }
      else
      {
         this.attachments = new HashMap<String, Object>();
      }
   }

   public List<UnifiedHandlerChainMetaData> getPostHandlerChains()
   {
      return postHandlerChains;
   }

   public List<UnifiedHandlerChainMetaData> getPreHandlerChains()
   {
      return preHandlerChains;
   }

   public List<UnifiedHandlerChainMetaData> getHandlers(HandlerType type)
   {
      List<UnifiedHandlerChainMetaData> handlerChains;
      if (type == HandlerType.PRE)
         handlerChains = getPreHandlerChains();
      else if (type == HandlerType.POST)
         handlerChains = getPostHandlerChains();
      else throw Messages.MESSAGES.invalidHandlerType(type != null ? type.toString() : null);
      return handlerChains;
   }

   public String getConfigName()
   {
      return configName;
   }

   public boolean hasFeature(String name)
   {
      return features.containsKey(name);
   }
   
   public String getProperty(String name)
   {
      return properties.get(name);
   }

   public Map<String, String> getProperties() {
      return properties;
   }

   public Map<String, Object> getAttachments() {
      return attachments;
   }
}
