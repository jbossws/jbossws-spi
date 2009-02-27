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
package org.jboss.wsf.spi.metadata.j2ee;

import java.lang.annotation.Annotation;

/**
 * @author Heiko.Braun <heiko.braun@jboss.com>
 */
public class PortComponentMD implements PortComponentSpec
{
   private static final long serialVersionUID = 1;

   private String portComponentName;
   private String portComponentURI;
   private String authMethod;
   private String transportGuarantee;
   private boolean secureWSDLAccess;

   public PortComponentMD()
   {
   }

   public String portComponentName()
   {
      return portComponentName;
   }

   public void setPortComponentName(String portComponentName)
   {
      this.portComponentName = portComponentName;
   }

   public String portComponentURI()
   {
      return portComponentURI;
   }

   public void setPortComponentURI(String portComponentURI)
   {
      this.portComponentURI = portComponentURI;
   }

   public String urlPattern()
   {
      String pattern = "/*";
      if (portComponentURI != null)
         pattern = portComponentURI;

      return pattern;
   }

   public String authMethod()
   {
      return authMethod;
   }

   public void setAuthMethod(String authMethod)
   {
      this.authMethod = authMethod;
   }

   public String transportGuarantee()
   {
      return transportGuarantee;
   }

   public void setTransportGuarantee(String transportGuarantee)
   {
      this.transportGuarantee = transportGuarantee;
   }

   public boolean secureWSDLAccess()
   {
      return secureWSDLAccess;
   }

   public void setSecureWSDLAccess(boolean secureWSDLAccess)
   {
      this.secureWSDLAccess = secureWSDLAccess;
   }

   public Class<? extends Annotation> annotationType()
   {
      return PortComponentSpec.class;
   }
}
