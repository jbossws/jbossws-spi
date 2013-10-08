/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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

import java.util.Map;

/**
 * Wraps AS7 NamespaceContextSelector logic to allow carrying its
 * contents for invocation served using thread pools.
 *
 * @author alessio.soldano@jboss.org
 * @since 03-Feb-2012
 *
 */
public interface NamespaceContextSelectorWrapper
{
   public void storeCurrentThreadSelector(Map<String, Object> map);
   
   public void setCurrentThreadSelector(Map<String, Object> map);
   
   public void clearCurrentThreadSelector(Map<String, Object> map);
}
