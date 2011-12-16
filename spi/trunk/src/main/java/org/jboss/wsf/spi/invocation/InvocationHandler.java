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
   /** Create a container specific invocation */
   public abstract Invocation createInvocation();

   /** Invoke the the service endpoint */
   public abstract void invoke(Endpoint ep, Invocation inv) throws Exception;

   /** Initilize the invocation handler */
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
