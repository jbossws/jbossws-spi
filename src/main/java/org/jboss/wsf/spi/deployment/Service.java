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
package org.jboss.wsf.spi.deployment;

import java.util.List;

/**
 * A service collecting endpoints belonging to the same deployment. 
 * 
 * @author Thomas.Diesler@jboss.com
 * @author alessio.soldano@jboss.com
 * @since 20-Apr-2007 
 */
public interface Service extends Extensible
{

   /**
    * Get the deployment this service belongs to
    * 
    * @return the deployment this service belongs to
    */
   Deployment getDeployment();
   
   /**
    * Add an endpoint to the service
    * 
    * @param endpoint   the endpoint to be added
    */
   void addEndpoint(Endpoint endpoint);
   
   /**
    * Remove an endpoint from the service, returns true if successfull
    * 
    * @param endpoint   the endpoint to remove
    * @return           true if the endpoint was actually removed, false otherwise
    */
   boolean removeEndpoint(Endpoint endpoint);
   
   /**
    * Get the list of endpoints
    * 
    * @return   a copy of the list of endpoints
    */
   List<Endpoint> getEndpoints();
   
   /**
    * Get the list of endpoints
    * 
    * @param filter a filter for selecting endpoints
    * @return       a list of selected endpoints
    */
   List<Endpoint> getEndpoints(EndpointTypeFilter filter);
   
   /**
    * Get an endpoint by name
    * 
    * @param simpleName the name of the endpoint to get
    * @return           the selected endpoint
    */
   Endpoint getEndpointByName(String simpleName);
   
   /**
    * Get the context root for this service
    * 
    * @return   the context root for this service
    */
   String getContextRoot();
   
   /**
    * Set the context root for this service
    * 
    * @param contextRoot    the context root for this service
    */
   void setContextRoot(String contextRoot);
   
   /**
    * Get the virtual host for this service
    * 
    * @return   the virtual host for this service
    */
   String getVirtualHost();
   
   /**
    * Set the virtual host for this service
    * 
    * @param virtualHost    the virtual host for this service
    */
   void setVirtualHost(String virtualHost);
   
}
