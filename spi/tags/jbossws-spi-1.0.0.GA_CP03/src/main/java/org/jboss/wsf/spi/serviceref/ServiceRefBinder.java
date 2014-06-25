/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.wsf.spi.serviceref;

import java.lang.reflect.AnnotatedElement;

import javax.naming.Context;
import javax.naming.NamingException;

import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedServiceRefMetaData;

/**
 * Creates a ServiceReferenceable and binds it to JNDI.
 *
 * @author Heiko.Braun@jboss.com
 *         Created: Jul 11, 2007
 */
public interface ServiceRefBinder
{
   final static String BEAN_NAME_JAXRPC = "WSServiceRefBinderJAXRPC";

   final static String BEAN_NAME_JAXWS = "WSServiceRefBinderJAXWS";

   void setupServiceRef(Context encCtx, String encName, AnnotatedElement anElement, UnifiedServiceRefMetaData serviceRef, ClassLoader loader) throws NamingException;
}