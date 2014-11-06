/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
