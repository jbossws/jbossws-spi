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

import java.util.List;
import java.util.Map;

import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData.HandlerType;

/**
 * A common configuration 
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public interface CommonConfig
{
   public static final String JBOSSWS_CONFIG_FILE = "jbossws-config-file";
   public static final String JBOSSWS_CONFIG_NAME = "jbossws-config-name";
   
   public List<UnifiedHandlerChainMetaData> getPostHandlerChains();

   public void setPostHandlerChains(List<UnifiedHandlerChainMetaData> postHandlerChain);

   public List<UnifiedHandlerChainMetaData> getPreHandlerChains();

   public void setPreHandlerChains(List<UnifiedHandlerChainMetaData> preHandlerChains);

   public List<UnifiedHandlerChainMetaData> getHandlers(HandlerType type);

   public String getConfigName();

   public void setConfigName(String configName);

   public boolean hasFeature(String name);
   
   public void setFeature(Feature feature, boolean enabled);

   public void setProperty(String name, String value);

   public String getProperty(String name);

   public Map<String, String> getProperties();
}
