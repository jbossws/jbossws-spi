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

import java.util.List;

import org.jboss.wsf.spi.deployment.Endpoint;

public class Context {
   private final String context;
   private final List<Endpoint> endpoints;
   
   public Context(String context, List<Endpoint> endpoints) {
      this.endpoints = endpoints;
      this.context = context;
   }
   
   public String getContext() {
      return context;
   }
   
   public List<Endpoint> getEndpoints() {
      return endpoints;
   }
}
