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
package org.jboss.wsf.spi.deployment;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ServiceLoader;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.wsf.spi.classloading.ClassLoaderProvider;

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
      ClassLoaderProvider clProvider = ClassLoaderProvider.getDefaultProvider();
      ClassLoader cl = clProvider.getWebServiceSubsystemClassLoader();
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
