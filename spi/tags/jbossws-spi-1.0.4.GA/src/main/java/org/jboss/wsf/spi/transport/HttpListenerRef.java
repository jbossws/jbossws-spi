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
package org.jboss.wsf.spi.transport;

import java.net.URI;

/**
 * @author Heiko.Braun <heiko.braun@jboss.com>
 */
public class HttpListenerRef implements ListenerRef
{
   private String webContext;
   private String urlPattern;
   private URI address;

   public HttpListenerRef(String webContext, String urlPattern, URI address)
   {
      if(!webContext.startsWith("/"))
         webContext = "/"+webContext;

      if(!urlPattern.startsWith("/"))
         urlPattern = "/"+urlPattern;

      this.webContext = webContext;
      this.urlPattern = urlPattern;
      this.address = address;
   }

   public Protocol getProtocol()
   {
      return Protocol.HTTP;
   }

   public URI getAddress()
   {
      return address;
   }

   public String getUUID()
   {
      return this.webContext+this.urlPattern;
   }


   public String toString()
   {
      return getAddress().toString();
   }
}
