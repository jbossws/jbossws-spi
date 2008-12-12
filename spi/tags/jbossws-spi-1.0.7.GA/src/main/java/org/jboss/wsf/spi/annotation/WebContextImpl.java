/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.annotation;

import java.lang.annotation.Annotation;

/**
 * Represents a {@link org.jboss.wsf.spi.annotation.WebContext} annotation
 * and reflects the annotation defult values. <br>
 * This implementation is used to provide a meta data representation (descriptor overrides) for the jboss EJB3 project.
 *
 * @author Heiko.Braun@jboss.com 
 */
public class WebContextImpl implements WebContext
{
   private String contextRoot = "";
   private String authmethod = "";
   private String[] virtualHosts = new String[] {};
   private String urlpattern = "";
   private String transportGuarantee  = "";
   private boolean securedWsdl = false;

   /**
    * The contextRoot element specifies the context root that the web service endpoint is deployed to.
    * If it is not specified it will be derived from the deployment short name.
    *
    * Applies to server side port components only.
    */
   public String contextRoot()
   {
      return this.contextRoot;
   };


   public void setContextRoot(String contextRoot)
   {
      this.contextRoot = contextRoot;
   }

   /**
    * The virtual hosts that the web service endpoint is deployed to.
    *
    * Applies to server side port components only.
    */
   public String[] virtualHosts(){
      return virtualHosts;
   };


   public void setVirtualHosts(String[] virtualHosts)
   {
      this.virtualHosts = virtualHosts;
   }

   /**
    * Relative path that is appended to the contextRoot to form fully qualified
    * endpoint address for the web service endpoint.
    *
    * Applies to server side port components only.
    */
   public String urlPattern() {
      return urlpattern;
   };


   public void setUrlpattern(String urlpattern)
   {
      this.urlpattern = urlpattern;
   }

   /**
    * The authMethod is used to configure the authentication mechanism for the web service.
    * As a prerequisite to gaining access to any web service which are protected by an authorization
    * constraint, a user must have authenticated using the configured mechanism.
    *
    * Legal values for this element are "BASIC", or "CLIENT-CERT".
    */
   public String authMethod()
   {
      return authmethod;
   };


   public void setAuthmethod(String authmethod)
   {
      this.authmethod = authmethod;
   }

   /**
    * The transportGuarantee specifies that the communication
    * between client and server should be NONE, INTEGRAL, or
    * CONFIDENTIAL. NONE means that the application does not require any
    * transport guarantees. A value of INTEGRAL means that the application
    * requires that the data sent between the client and server be sent in
    * such a way that it can't be changed in transit. CONFIDENTIAL means
    * that the application requires that the data be transmitted in a
    * fashion that prevents other entities from observing the contents of
    * the transmission. In most cases, the presence of the INTEGRAL or
    * CONFIDENTIAL flag will indicate that the use of SSL is required.
    */
   public String transportGuarantee()
   {
      return transportGuarantee;
   };


   public void setTransportGuarantee(String transportGuarantee)
   {      
      this.transportGuarantee = transportGuarantee;
   }

   /**
    * A secure endpoint does not secure wsdl access by default.
    * Explicitly setting secureWSDLAccess overrides this behaviour.
    *
    * Protect access to WSDL. See http://jira.jboss.org/jira/browse/JBWS-723
    */
   public boolean secureWSDLAccess()
   {
      return securedWsdl;
   };


   public void setSecuredWsdl(boolean securedWsdl)
   {
      this.securedWsdl = securedWsdl;
   }

   public Class<? extends Annotation> annotationType()
   {
      return WebContext.class;
   }

}
