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
