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

import java.security.Principal;

import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceContext;

import org.w3c.dom.Element;

/**
 * A ExtensibleWebServiceContext implementation that delegates
 * to a given WebServiceContext instance.
 * 
 * @author alessio.soldano@jboss.com
 * @since 28-Nov-2008
 */
public class WebServiceContextDelegate extends ExtensibleWebServiceContext
{
   private WebServiceContext ctx;
   
   public WebServiceContextDelegate(WebServiceContext ctx)
   {
      super(ctx.getMessageContext());
      this.ctx = ctx;
   }

   @Override
   public Principal getUserPrincipal()
   {
      return ctx.getUserPrincipal();
   }

   @Override
   public boolean isUserInRole(String s)
   {
      return ctx.isUserInRole(s);
   }
   
   @Override
   public javax.xml.ws.handler.MessageContext getMessageContext()
   {
      return ctx.getMessageContext();
   }

   @Override
   public EndpointReference getEndpointReference(Element... arg0)
   {
      return ctx.getEndpointReference(arg0);
   }

   @Override
   public <T extends EndpointReference> T getEndpointReference(Class<T> arg0, Element... arg1)
   {
      return ctx.getEndpointReference(arg0, arg1);
   }
}
