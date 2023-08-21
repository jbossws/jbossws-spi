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

   public List<UnifiedHandlerChainMetaData> getPreHandlerChains();

   public List<UnifiedHandlerChainMetaData> getHandlers(HandlerType type);

   public String getConfigName();

   public boolean hasFeature(String name);
   
   public String getProperty(String name);

   public Map<String, String> getProperties();
}
