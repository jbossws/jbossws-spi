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
