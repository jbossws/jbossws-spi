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
package org.jboss.wsf.spi.publish;

import java.util.Map;

import org.jboss.wsf.spi.metadata.webservices.JBossWebservicesMetaData;
import org.jboss.wsf.spi.metadata.webservices.WebservicesMetaData;

/**
 * EndpointPublisher defines the interface for facilities allowing to publish/destroy
 * WS endpoints on top of the running JBoss Application Server container.
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public interface EndpointPublisher
{
   public Context publish(String contextRoot, ClassLoader loader, Map<String, String> urlPatternToClassNameMap) throws Exception;
   
   public Context publish(String contextRoot, ClassLoader loader, Map<String, String> urlPatternToClassNameMap, WebservicesMetaData metadata) throws Exception;
   
   public Context publish(String contextRoot, ClassLoader loader, Map<String, String> urlPatternToClassNameMap, WebservicesMetaData metadata, JBossWebservicesMetaData jbwsMetadata) throws Exception;
   
   public void destroy(Context context) throws Exception;
}
