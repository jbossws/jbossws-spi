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
package org.jboss.wsf.spi.invocation;

import java.lang.reflect.UndeclaredThrowableException;

import javax.management.MBeanException;

import org.jboss.wsf.spi.deployment.Endpoint;

/**
 * Handles invocations on endpoints.
 *
 * @author Thomas.Diesler@jboss.org
 * @author Heiko.Braun@jboss.com
 * @since 25-Apr-2007
 */
public abstract class InvocationHandler
{
   /**
    * Create a container specific invocation
    *
    * @return  invocation
    */
   public abstract Invocation createInvocation();

   /**
    * Invoke the the service endpoint
    *
    * @param ep  endpoint
    * @param inv  invocation
    * @throws Exception  exception
    */
   public abstract void invoke(Endpoint ep, Invocation inv) throws Exception;

   /**
    *  Initilize the invocation handler
    *
    * @param ep  endpoint
    */
   public abstract void init(Endpoint ep);
   
   protected void handleInvocationException(Throwable th) throws Exception
   {
      if (th instanceof MBeanException)
      {
         throw ((MBeanException)th).getTargetException();
      }

      if (th instanceof Exception)
      {
         throw (Exception)th;
      }

      if (th instanceof Error)
      {
         throw (Error)th;
      }

      throw new UndeclaredThrowableException(th);
   }
   
   // invocation handler lifecycle callback methods
   
   /**
    * Template method for notifying subclasses that endpoint instance have been instantiated.
    *
    * @param endpoint instantiated endpoint
    * @param invocation current invocation
    * @throws Exception subclasses have to throw exception on any failure
    */
   public abstract void onEndpointInstantiated(final Endpoint endpoint, final Invocation invocation) throws Exception;

   /**
    * Template method for notifying subclasses that endpoint method is going to be invoked.
    *
    * @param invocation current invocation
    * @throws Exception subclasses have to throw exception on any failure
    */
   public abstract void onBeforeInvocation(final Invocation invocation) throws Exception;

   /**
    * Template method for notifying subclasses that endpoint method invocation was completed.
    *
    * @param invocation current invocation
    * @throws Exception subclasses have to throw exception on any failure
    */
   public abstract void onAfterInvocation(final Invocation invocation) throws Exception;
   
}
