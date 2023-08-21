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
package org.jboss.wsf.spi.management;

import java.io.File;
import java.net.UnknownHostException;

import org.jboss.wsf.spi.metadata.config.ClientConfig;
import org.jboss.wsf.spi.metadata.config.EndpointConfig;

/**
 * Interface to container independent config 
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 08-May-2006
 */
public interface ServerConfig
{
   /** The host name that is returned if there is no other defined */
   String UNDEFINED_HOSTNAME = "jbossws.undefined.host";
   
   String getImplementationTitle();

   String getImplementationVersion();
   
   File getHomeDir();
   
   File getServerTempDir();

   File getServerDataDir();

   String getWebServiceHost();
   
   void setWebServiceHost(String host) throws UnknownHostException;
   
   int getWebServicePort();
   
   void setWebServicePort(int port);
   
   int getWebServiceSecurePort();

   void setWebServiceSecurePort(int port);

   String getWebServicePathRewriteRule();
   void setWebServicePathRewriteRule(String path);
   
   String getWebServiceUriScheme();
   void setWebServiceUriScheme(String scheme);
   
   boolean isModifySOAPAddress();
   
   void setModifySOAPAddress(boolean flag);
   
   boolean isStatisticsEnabled();
   
   void setStatisticsEnabled(boolean flag);
   
   /**
    * Register a client config in the server configuration; the new config will apply to runtime when the server config is started
    * or after a client config store reload.
    * 
    * @param config  client config to register
    */
   void registerClientConfig(ClientConfig config);
   
   /**
    * Unregister a client config from the server configuration; the new config will be removed from
    * the collection returned to callers after next endpoint store reload.
    * 
    * @param config  client config to unregister
    */
   void unregisterClientConfig(ClientConfig config);
   
   /**
    * Reloads the client config store
    */
   void reloadClientConfigs();
   
   /**
    * Get a client config by name
    *
    * @param name    name of client config
    * @return   named client config
    */
   ClientConfig getClientConfig(String name);
   
   /**
    * Register an endpoint config in the server configuration; the new config will apply to runtime when the server config is started
    * or after an endpoint config store reload.
    * 
    * @param config   endpoint config to register
    */
   void registerEndpointConfig(EndpointConfig config);
   
   /**
    * Unregister an endpoint config from the server configuration; the new config will be removed from
    * the collection returned to callers after next endpoint store reload.
    * 
    * @param config    endpoint config to unregister
    */
   void unregisterEndpointConfig(EndpointConfig config);
   
   /**
    * Reloads the endpoint config store
    */
   void reloadEndpointConfigs();
   
   /**
    * Get an endpoint config by name
    *
    * @param name  name of endpoint
    * @return    found named endpoint config
    */
   EndpointConfig getEndpointConfig(String name);
   /**
    * Get port for virtual host, if there are many ports found only return the first found
    * @param virtualHost virtual host name
    * @param secure if get the secure port
    * @return port value for virtual host
   */
   Integer getVirtualHostPort(String virtualHost, boolean secure);
   
   /**
    * Get host alias which DNS can resolve
    * @param virtualHost virtual host name
    * @return host alias name
   */
   String getHostAlias(String virtualHost);
}
