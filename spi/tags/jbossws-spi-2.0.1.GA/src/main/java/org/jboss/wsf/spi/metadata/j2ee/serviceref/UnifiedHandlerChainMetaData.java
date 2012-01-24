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
package org.jboss.wsf.spi.metadata.j2ee.serviceref;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.serviceref.ServiceRefElement;

/** The unified metdata data for a handler chain element
 * 
 * @author Thomas.Diesler@jboss.org
 */
public class UnifiedHandlerChainMetaData extends ServiceRefElement
{
   private QName serviceNamePattern;
   private QName portNamePattern;
   private String protocolBindings;
   private List<UnifiedHandlerMetaData> handlers = new ArrayList<UnifiedHandlerMetaData>();
   private boolean excluded;
   private String id;

   @Deprecated
   public UnifiedHandlerChainMetaData(UnifiedHandlerChainsMetaData handlerChains)
   {
   }

   public UnifiedHandlerChainMetaData()
   {
   }

   public String getId()
   {
      return id;
   }

   public void setId(final String id)
   {
      this.id = id;
   }

   public QName getPortNamePattern()
   {
      return portNamePattern;
   }

   public void setPortNamePattern(QName portNamePattern)
   {
      this.portNamePattern = portNamePattern;
   }

   public QName getServiceNamePattern()
   {
      return serviceNamePattern;
   }

   public void setServiceNamePattern(QName serviceNamePattern)
   {
      this.serviceNamePattern = serviceNamePattern;
   }

   public String getProtocolBindings()
   {
      return protocolBindings;
   }

   public void setProtocolBindings(String protocolBindings)
   {
      this.protocolBindings = protocolBindings;
   }

   public List<UnifiedHandlerMetaData> getHandlers()
   {
      return handlers;
   }

   public void addHandler(UnifiedHandlerMetaData handler)
   {
      handlers.add(handler);
   }
   
   public boolean isExcluded() {
      return this.excluded;
   }
   
   public void setExcluded(boolean excluded) {
      this.excluded = excluded;
   }
}
