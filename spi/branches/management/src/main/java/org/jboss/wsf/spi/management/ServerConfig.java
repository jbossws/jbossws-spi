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
package org.jboss.wsf.spi.management;

import java.io.File;
import java.net.UnknownHostException;

import org.jboss.wsf.spi.deployment.Extensible;
import org.jboss.wsf.spi.metadata.config.ClientConfig;
import org.jboss.wsf.spi.metadata.config.EndpointConfig;

/**
 * Interface to container independent config 
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 * @since 08-May-2006
 */
public interface ServerConfig extends Extensible
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
    * @param config
    */
   void registerClientConfig(ClientConfig config);
   
   /**
    * Unregister a client config from the server configuration; the new config will be removed from
    * the collection returned to callers after next endpoint store reload.
    * 
    * @param config
    */
   void unregisterClientConfig(ClientConfig config);
   
   /**
    * Reloads the client config store
    */
   void reloadClientConfigs();
   
   /**
    * Get a client config by name
    * 
    * @return
    */
   ClientConfig getClientConfig(String name);
   
   /**
    * Register an endpoint config in the server configuration; the new config will apply to runtime when the server config is started
    * or after an endpoint config store reload.
    * 
    * @param config
    */
   void registerEndpointConfig(EndpointConfig config);
   
   /**
    * Unregister an endpoint config from the server configuration; the new config will be removed from
    * the collection returned to callers after next endpoint store reload.
    * 
    * @param config
    */
   void unregisterEndpointConfig(EndpointConfig config);
   
   /**
    * Reloads the endpoint config store
    */
   void reloadEndpointConfigs();
   
   /**
    * Get an endpoint config by name
    * 
    * @return
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
