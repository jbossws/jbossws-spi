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

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author alessio.soldano@jboss.com
 * @since 06-Apr-2011
 *
 */
public interface ServletDelegate
{
   public void init(ServletConfig servletConfig) throws ServletException;
   
   public void destroy();

   public void doHead(HttpServletRequest request, HttpServletResponse response, ServletContext context)
         throws ServletException, IOException;

   public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext context)
         throws ServletException, IOException;

   public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext context)
         throws ServletException, IOException;

   public void doPut(HttpServletRequest request, HttpServletResponse response, ServletContext context)
         throws ServletException, IOException;

   public void doDelete(HttpServletRequest request, HttpServletResponse response, ServletContext context)
         throws ServletException, IOException;

   public void service(HttpServletRequest request, HttpServletResponse response, ServletContext context)
         throws ServletException, IOException;
}
