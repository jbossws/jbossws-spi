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
 * The container independent metadata of a stateless session bean. 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2006
 */
public class SLSBMetaData extends EJBMetaData
{

   protected SLSBMetaData(String ejbName, String ejbClass, String homeClass, String localHomeClass, String seiName,
         String jndiName, String localJndiName, String portComponentName, String portComponentURI,
         EJBSecurityMetaData securityMetaData)
   {
      super(ejbName, ejbClass, homeClass, localHomeClass, seiName, jndiName, localJndiName, portComponentName,
            portComponentURI, securityMetaData);
   }
   
   public static class Builder {
      private String ejbName;
      private String ejbClass;
      private String homeClass;
      private String localHomeClass;
      private String seiName;
      private String jndiName;
      private String localJndiName;
      private String portComponentName;
      private String portComponentURI;
      private EJBSecurityMetaData securityMetaData;
      
      public SLSBMetaData build()
      {
         return new SLSBMetaData(ejbName, ejbClass, homeClass, localHomeClass, seiName, jndiName, localJndiName, portComponentName, portComponentURI, securityMetaData);
      }
      
      public String getEjbName()
      {
         return ejbName;
      }
      public void setEjbName(String ejbName)
      {
         this.ejbName = ejbName;
      }
      public String getEjbClass()
      {
         return ejbClass;
      }
      public void setEjbClass(String ejbClass)
      {
         this.ejbClass = ejbClass;
      }
      public String getHomeClass()
      {
         return homeClass;
      }
      public void setHomeClass(String homeClass)
      {
         this.homeClass = homeClass;
      }
      public String getLocalHomeClass()
      {
         return localHomeClass;
      }
      public void setLocalHomeClass(String localHomeClass)
      {
         this.localHomeClass = localHomeClass;
      }
      public String getSeiName()
      {
         return seiName;
      }
      public void setSeiName(String seiName)
      {
         this.seiName = seiName;
      }
      public String getJndiName()
      {
         return jndiName;
      }
      public void setJndiName(String jndiName)
      {
         this.jndiName = jndiName;
      }
      public String getLocalJndiName()
      {
         return localJndiName;
      }
      public void setLocalJndiName(String localJndiName)
      {
         this.localJndiName = localJndiName;
      }
      public String getPortComponentName()
      {
         return portComponentName;
      }
      public void setPortComponentName(String portComponentName)
      {
         this.portComponentName = portComponentName;
      }
      public String getPortComponentURI()
      {
         return portComponentURI;
      }
      public void setPortComponentURI(String portComponentURI)
      {
         this.portComponentURI = portComponentURI;
      }
      public EJBSecurityMetaData getSecurityMetaData()
      {
         return securityMetaData;
      }
      public void setSecurityMetaData(EJBSecurityMetaData securityMetaData)
      {
         this.securityMetaData = securityMetaData;
      }
   }
}
