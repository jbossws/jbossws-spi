/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata.config;

import java.util.Collections;
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
}
