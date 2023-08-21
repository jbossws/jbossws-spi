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

import java.util.Set;

import javax.management.ObjectName;

import org.jboss.wsf.spi.deployment.Endpoint;

/**
 * A general endpoint registry.
 * 
 * @author Thomas.Diesler@jboss.com
 * @author Heiko.Braun@jboss.com
 * 
 * @since 20-Apr-2007 
 */
public interface EndpointRegistry
{
   /**
    * Get the list of registered endpoints
    * @return  list of registered endpoints
    */
   Set<ObjectName> getEndpoints();

   /**
    *  Get the registered endpoint
    * @param epName   endpoint to return
    * @return     registered endpoint
    */
   Endpoint getEndpoint(ObjectName epName);

   /**
    * Resolve endpoints through a resolve instance
    * @param resolver   resolver endpoint
    * @return  found resolved endpoint
    */
   Endpoint resolve(EndpointResolver resolver);

   /**
    *  True is an endpoint for that name is registered
    * @param epName   endpoint to lookup
    * @return     True if endpoint for name is registered
    */
   boolean isRegistered(ObjectName epName);
}
