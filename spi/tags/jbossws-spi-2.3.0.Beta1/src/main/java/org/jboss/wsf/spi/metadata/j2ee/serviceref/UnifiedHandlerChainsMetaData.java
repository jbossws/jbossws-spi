/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata.j2ee.serviceref;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** The unified metadata data for a handler chains element
 * 
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 */
public class UnifiedHandlerChainsMetaData implements Serializable
{
   private static final long serialVersionUID = -4983482217732535558L;
   
   private final List<UnifiedHandlerChainMetaData> handlerChains;
   
   public UnifiedHandlerChainsMetaData(UnifiedHandlerChainMetaData... handlerChains)
   {
      this(handlerChains != null ? Arrays.asList(handlerChains) : null);
   }

   public UnifiedHandlerChainsMetaData(List<UnifiedHandlerChainMetaData> handlerChains)
   {
      if (handlerChains != null && !handlerChains.isEmpty()) {
         this.handlerChains = Collections.unmodifiableList(handlerChains);
      } else {
         this.handlerChains = Collections.emptyList();
      }
   }

   public List<UnifiedHandlerChainMetaData> getHandlerChains()
   {
      return handlerChains;
   }
}