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
package org.jboss.wsf.spi.metadata.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.jboss.ws.api.util.BundleUtils;
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
   private static final ResourceBundle bundle = BundleUtils.getBundle(AbstractCommonConfig.class);
   private String configName;
   private Map<String, Feature> features = new HashMap<String, Feature>(2);
   private Map<String, String> properties = new HashMap<String, String>(4);
   private List<UnifiedHandlerChainMetaData> preHandlerChains;
   private List<UnifiedHandlerChainMetaData> postHandlerChains;

   public List<UnifiedHandlerChainMetaData> getPostHandlerChains()
   {
      return postHandlerChains;
   }

   public void setPostHandlerChains(List<UnifiedHandlerChainMetaData> postHandlerChain)
   {
      this.postHandlerChains = postHandlerChain;
   }

   public List<UnifiedHandlerChainMetaData> getPreHandlerChains()
   {
      return preHandlerChains;
   }

   public void setPreHandlerChains(List<UnifiedHandlerChainMetaData> preHandlerChains)
   {
      this.preHandlerChains = preHandlerChains;
   }

   public List<UnifiedHandlerChainMetaData> getHandlers(HandlerType type)
   {
      List<UnifiedHandlerChainMetaData> handlerChains;
      if (type == HandlerType.PRE)
         handlerChains = getPreHandlerChains();
      else if (type == HandlerType.POST)
         handlerChains = getPostHandlerChains();
      else throw new IllegalArgumentException(BundleUtils.getMessage(bundle, "INVALID_HANDLER_TYPE",  type));
      return handlerChains;
   }

   public String getConfigName()
   {
      return configName;
   }

   public void setConfigName(String configName)
   {
      this.configName = configName;
   }

   public boolean hasFeature(String name)
   {
      return features.containsKey(name);
   }
   
   public void setFeature(Feature feature, boolean enabled) {

      if(enabled) {
         features.put(feature.getName(), feature);
      }
      else
         features.remove(feature.getName());
   }

   public void setProperty(String name, String value)
   {
      properties.put(name, value);
   }

   public String getProperty(String name)
   {
      return properties.get(name);
   }

   public Map<String, String> getProperties() {
      return properties;
   }
}
