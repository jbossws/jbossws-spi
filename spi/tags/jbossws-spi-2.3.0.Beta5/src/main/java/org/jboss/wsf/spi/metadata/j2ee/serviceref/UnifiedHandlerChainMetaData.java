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
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

/** The unified metadata data for a handler chain element
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 */
public class UnifiedHandlerChainMetaData implements Serializable
{
   private static final long serialVersionUID = 4612021639718764949L;
   
   private final QName serviceNamePattern;
   private final QName portNamePattern;
   private final String protocolBindings;
   private final List<UnifiedHandlerMetaData> handlers;
   private final boolean excluded;
   private final String id;

   public UnifiedHandlerChainMetaData(QName serviceNamePattern,
                                      QName portNamePattern,
                                      String protocolBindings,
                                      List<UnifiedHandlerMetaData> handlers,
                                      boolean excluded,
                                      String id)
   {
      this.serviceNamePattern = serviceNamePattern;
      this.portNamePattern = portNamePattern;
      this.protocolBindings = protocolBindings;
      this.excluded = excluded;
      this.id = id;
      if (handlers != null && !handlers.isEmpty()) {
         this.handlers = Collections.unmodifiableList(handlers);
         for (UnifiedHandlerMetaData uhmd : handlers) {
            uhmd.setHandlerChain(this);
         }
      } else {
         this.handlers = Collections.emptyList();
      }
   }

   public String getId()
   {
      return id;
   }

   public QName getPortNamePattern()
   {
      return portNamePattern;
   }

   public QName getServiceNamePattern()
   {
      return serviceNamePattern;
   }

   public String getProtocolBindings()
   {
      return protocolBindings;
   }

   public List<UnifiedHandlerMetaData> getHandlers()
   {
      return handlers;
   }

   public boolean isExcluded() {
      return this.excluded;
   }
}
