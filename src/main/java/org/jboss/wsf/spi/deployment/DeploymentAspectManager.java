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
 * A general service deployment manager.
 * 
 * @author Thomas.Diesler@jboss.com
 *
 * @since 20-Apr-2007 
 */
public interface DeploymentAspectManager
{
   /**
    * Get the name for this aspect manager
    * @return  name of aspect manager
    */
   String getName();

   /**
    * Get the list of registered deployment aspects
    * @return  list of registered deployment aspects
    */
   List<DeploymentAspect> getDeploymentAspects();

   /**
    * Set the list of registered deployment aspects
    * @param aspects   set of registered deployment aspects
    */
   void setDeploymentAspects(List<DeploymentAspect> aspects);

   /**
    * Deploy a web service
    * @param dep  web service to deploy
    */
   void deploy(Deployment dep);

   /**
    * Undeploy a web service
    * @param dep  web service deployment
    */
   void undeploy(Deployment dep);
}
