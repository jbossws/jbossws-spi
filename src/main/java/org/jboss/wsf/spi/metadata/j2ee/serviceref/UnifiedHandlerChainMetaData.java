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
