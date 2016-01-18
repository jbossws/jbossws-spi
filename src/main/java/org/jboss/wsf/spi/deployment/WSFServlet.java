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
package org.jboss.wsf.spi.deployment;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ServiceLoader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.wsf.spi.classloading.ClassLoaderProvider;
import org.jboss.wsf.spi.classloading.JAXRSClassLoaderProvider;

/**
 * 
 * @author alessio.soldano@jboss.com
 * @since 06-Apr-2011
 *
 */
public class WSFServlet extends HttpServlet
{
   private static final long serialVersionUID = -1958443536378468262L;

   public static final String STACK_SERVLET_DELEGATE_CLASS = "org.jboss.wsf.spi.deployment.stackServletDelegateClass";
   public static final String JAXRS_SERVLET_MODE = "org.jboss.wsf.spi.deployment.jaxrsServletMode";

   private volatile ServletDelegate delegate = null;

   @Override
   public void init(ServletConfig servletConfig) throws ServletException
   {
      super.init(servletConfig);
      delegate = getDelegate(servletConfig);
      if (delegate != null)
      {
         delegate.init(servletConfig);
      }
   }
   
   /**
    * Creates a ServletDelegate instance according to the STACK_SERVLET_DELEGATE_CLASS init parameter.
    * The class is loaded through a ServletDelegateFactory that's retrieved as follows:
    * - if a default ClassLoaderProvider is available, the webservice subsystem classloader from it
    *   is used to lookup the factory
    * - otherwise the current thread context classloader is used to lookup the factory. 
    * 
    * @param servletConfig   servlet config
    * @return the servlet delegate
    */
   protected ServletDelegate getDelegate(ServletConfig servletConfig)
   {
      final ClassLoader cl;
      if (Boolean.parseBoolean(servletConfig.getInitParameter(JAXRS_SERVLET_MODE))) {
         JAXRSClassLoaderProvider clProvider = JAXRSClassLoaderProvider.getDefaultProvider();
         cl = clProvider.getJAXRSSubsystemClassLoader();
      } else {
         ClassLoaderProvider clProvider = ClassLoaderProvider.getDefaultProvider();
         cl = clProvider.getWebServiceSubsystemClassLoader();
      }
      ServiceLoader<ServletDelegateFactory> sl = ServiceLoader.load(ServletDelegateFactory.class, cl);
      ServletDelegateFactory factory = sl.iterator().next();
      return factory.newServletDelegate(servletConfig.getInitParameter(STACK_SERVLET_DELEGATE_CLASS));
   }
   
   @Override
   public void destroy()
   {
      if (delegate != null)
      {
         delegate.destroy();
      }
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if (delegate != null)
      {
         delegate.doPost(request, response, getServletContext());
      }
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if (delegate != null)
      {
         delegate.doGet(request, response, getServletContext());
      }
   }

   @Override
   protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if (delegate != null)
      {
         delegate.doPut(request, response, getServletContext());
      }
   }

   @Override
   protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if (delegate != null)
      {
         delegate.doDelete(request, response, getServletContext());
      }
   }

   @Override
   protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if (delegate != null)
      {
         delegate.doHead(request, response, getServletContext());
      }
   }
   
   @Override
   public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if (delegate != null)
      {
         delegate.service(request, response, getServletContext());
      }
   }
   
   static ClassLoader getContextClassLoader() {
      if (System.getSecurityManager() == null) {
          return Thread.currentThread().getContextClassLoader();
      } else {
          return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
              public ClassLoader run() {
                  return Thread.currentThread().getContextClassLoader();
              }
          });
      }
  }
}
