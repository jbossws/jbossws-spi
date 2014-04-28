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
package org.jboss.wsf.spi.invocation.integration;

import java.lang.reflect.Method;

/**
 * Host's web service implementation and allows invocations on them.
 *  
 * @author Heiko.Braun <heiko.braun@jboss.com>
 */
public interface ServiceEndpointContainer
{
   /**
    * Identifies a service endpoint container
    * @return
    */
   String getContainerName();

   /**
    * The actual web service implementation hosted by this container.
    * @return
    */
   Class<?> getServiceImplementationClass();

   /**
    * Invokes a particular endpoint opertation.
    * 
    * @param method  business method
    * @param args  parameters
    * @param callback gives access to invocation context properties. I.e. for injecting the WebServiceContext
    * @return null for operations without return parameter
    * @throws Exception
    */
   Object invokeEndpoint(Method method, Object[] args, InvocationContextCallback callback) throws Throwable;
}
