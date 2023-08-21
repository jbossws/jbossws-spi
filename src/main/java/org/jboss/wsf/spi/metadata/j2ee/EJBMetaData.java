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
package org.jboss.wsf.spi.metadata.j2ee;

/**
 * The container independent common meta data class for the entity, message-driven and session beans. 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2006
 */
public abstract class EJBMetaData
{
   // The ejb-name element specifies an enterprise bean's name. 
   private final String ejbName;
   // The ejb-class element contains the fully-qualified name of the enterprise bean's class. 
   private final String ejbClass;
   // The home element contains the fully-qualified name of the enterprise bean's home interface. */
   private final String homeClass;
   // The local-home element contains the fully-qualified name of the enterprise bean's local home interface. 
   private final String localHomeClass;
   // The service-endpoint element contains the fully-qualified name of the beans service endpoint interface (SEI) 
   private final String seiName;
   // The JNDI name under with the home interface should be bound 
   private final String jndiName;
   // The JNDI name under with the local home interface should be bound 
   private final String localJndiName;
   // The optional port-component-name
   private final String portComponentName;
   // The optional port-component-uri
   private final String portComponentURI;
   // The optional security meta data
   private final EJBSecurityMetaData securityMetaData;
   
   protected EJBMetaData(String ejbName, String ejbClass, String homeClass, String localHomeClass, String seiName,
         String jndiName, String localJndiName, String portComponentName, String portComponentURI,
         EJBSecurityMetaData securityMetaData)
   {
      this.ejbName = ejbName;
      this.ejbClass = ejbClass;
      this.homeClass = homeClass;
      this.localHomeClass = localHomeClass;
      this.seiName = seiName;
      this.jndiName = jndiName;
      this.localJndiName = localJndiName;
      this.portComponentName = portComponentName;
      this.portComponentURI = portComponentURI;
      this.securityMetaData = securityMetaData;
   }

   public String getEjbName()
   {
      return ejbName;
   }

   public String getEjbClass()
   {
      return ejbClass;
   }

   public String getServiceEndpointInterface()
   {
      return seiName;
   }

   public String getContainerObjectNameJndiName()
   {
      return getHome() != null ? getJndiName() : getLocalJndiName();
   }

   public String getHome()
   {
      return homeClass;
   }

   public String getJndiName()
   {
      return jndiName;
   }

   public String getLocalHome()
   {
      return localHomeClass;
   }

   public String getLocalJndiName()
   {
      return localJndiName;
   }

   public String getPortComponentName()
   {
      return portComponentName;
   }

   public String getPortComponentURI()
   {
      return portComponentURI;
   }

   public EJBSecurityMetaData getSecurityMetaData()
   {
      return securityMetaData;
   }

}
