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

import java.util.Set;

/**
 * A deployment aspect that does nothing.
 * 
 * A deployment aspects can require/provide a set of string conditions.
 * This determines the order of deployment aspects in the deployment aspect manager. 
 * 
 * @author Thomas.Diesler@jboss.com
 * @since 20-Apr-2007 
 */
public interface DeploymentAspect
{
   public static final String LAST_DEPLOYMENT_ASPECT = "LAST_DEPLOYMENT_ASPECT";

   public void setLast(boolean isLast);
   
   public boolean isLast();
   
   public String getProvides();

   public void setProvides(String provides);

   public String getRequires();

   public void setRequires(String requires);
   
   public void setRelativeOrder(int relativeOrder);
   
   public int getRelativeOrder();

   public void start(Deployment dep);

   public void stop(Deployment dep);

   public Set<String> getProvidesAsSet();

   public Set<String> getRequiresAsSet();
   
   public ClassLoader getLoader();
}
