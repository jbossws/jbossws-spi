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
import java.util.List;

/** 
 * A JBossWS configuration 
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public class ConfigRoot
{
   private List<ClientConfig> clientConfigList = new ArrayList<ClientConfig>();
   private List<EndpointConfig> endpointConfigList = new ArrayList<EndpointConfig>();

   public List<ClientConfig> getClientConfig()
   {
      return clientConfigList;
   }
   
   public void addClientConfig(ClientConfig clientConfig)
   {
      this.clientConfigList.add(clientConfig);
   }

   public void addEndpointConfig(EndpointConfig endpointConfig)
   {
      this.endpointConfigList.add(endpointConfig);
   }

   public void setClientConfig(List<ClientConfig> clientConfig)
   {
      this.clientConfigList = clientConfig;
   }

   public List<EndpointConfig> getEndpointConfig()
   {
      return endpointConfigList;
   }

   public void setEndpointConfig(List<EndpointConfig> endpointConfig)
   {
      this.endpointConfigList = endpointConfig;
   }
   
   public ClientConfig getClientConfigByName(String configName)
   {
      ClientConfig config = null;
      for(ClientConfig aux : clientConfigList)
      {
         if (aux.getConfigName().equals(configName))
         {
            config = aux;
            break;
         }
      }
      
      return config;
   }
   
   public EndpointConfig getEndpointConfigByName(String configName)
   {
      EndpointConfig config = null;
      for(EndpointConfig aux : endpointConfigList)
      {
         if (aux.getConfigName().equals(configName))
         {
            config = aux;
            break;
         }
      }
      
      return config;
   }

   public CommonConfig getConfigByName(String name)
   {
      CommonConfig config = getClientConfigByName(name);
      if(null == config)
         config = getEndpointConfigByName(name);
      return config;
   }
}
