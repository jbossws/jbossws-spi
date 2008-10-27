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
package org.jboss.wsf.spi.invocation;

import org.jboss.wsf.spi.invocation.ExtensibleWebServiceContext;
import org.w3c.dom.Element;

import java.security.Principal;
import java.util.Collection;

import javax.ejb.EJBContext;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.handler.MessageContext;

/**
 * A WebServiceContext implementation that delegates to the EJBContext.
 *
 * @author Thomas.Diesler@jboss.org
 * @since 23-Jan-2007
 */
public class WebServiceContextEJB extends ExtensibleWebServiceContext
{
   public WebServiceContextEJB(MessageContext msgContext)
   {
      super(msgContext);
   }

   public Principal getUserPrincipal()
   {
      EJBContext ejbContext = getAttachment(EJBContext.class);
      Principal principal = ejbContext.getCallerPrincipal();
      return principal;
   }

   public boolean isUserInRole(String role)
   {
      EJBContext ejbContext = getAttachment(EJBContext.class);
      boolean isUserInRole = ejbContext.isCallerInRole(role);
      return isUserInRole;
   }
}
