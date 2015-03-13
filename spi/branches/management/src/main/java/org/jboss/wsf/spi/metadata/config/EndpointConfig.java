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

import java.util.List;
import java.util.Map;

import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;

/** 
 * A JBossWS endpoint configuration 
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public class EndpointConfig extends AbstractCommonConfig
{
   public static final String STANDARD_ENDPOINT_CONFIG = "Standard-Endpoint-Config";
   public static final String DEFAULT_ENDPOINT_CONFIG_FILE = "jaxws-endpoint-config.xml";

   public EndpointConfig(String configName, List<UnifiedHandlerChainMetaData> preHandlerChains,
         List<UnifiedHandlerChainMetaData> postHandlerChains, Map<String, String> properties,
         Map<String, Feature> features)
   {
      super(configName, preHandlerChains, postHandlerChains, properties, features);
   }
   
   public EndpointConfig(EndpointConfig base, EndpointConfig conf) {
      super(base, conf);
   }
}
