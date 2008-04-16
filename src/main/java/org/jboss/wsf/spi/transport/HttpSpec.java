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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heiko.Braun <heiko.braun@jboss.com>
 */
public class HttpSpec implements TransportSpec
{
   public static final String PROPERTY_GENERATED_WEBAPP = "org.jboss.ws.generated.webapp";
   public static final String PROPERTY_WEBAPP_CONTEXT_PARAMETERS = "org.jboss.ws.webapp.ContextParameterMap";
   public static final String PROPERTY_WEBAPP_SERVLET_CLASS = "org.jboss.ws.webapp.ServletClass";
   public static final String PROPERTY_WEBAPP_SERVLET_CONTEXT_LISTENER = "org.jboss.ws.webapp.ServletContextListener";
   public static final String PROPERTY_WEBAPP_URL = "org.jboss.ws.webapp.url";

   private String webContext;
   private String urlPattern;

   private String servletClass;
   private Map<String,String> contextParameter = new HashMap<String,String>();

   public HttpSpec(String webContext, String urlPattern)
   {
      if(!webContext.startsWith("/"))
         throw new IllegalArgumentException("webContext needs to start with '/'");

      this.webContext = webContext;
      this.urlPattern = urlPattern;
   }

   public Protocol getProtocol()
   {
      return Protocol.HTTP;
   }

   public String getWebContext()
   {
      return webContext;
   }

   public String getUrlPattern()
   {
      return urlPattern;
   }

   public void setServletClass(String servletClass)
   {
      this.servletClass = servletClass;
   }

   public String getServletClass()
   {
      return servletClass;
   }

   public Map<String, String> getContextParameter()
   {
      return contextParameter;
   }
}
