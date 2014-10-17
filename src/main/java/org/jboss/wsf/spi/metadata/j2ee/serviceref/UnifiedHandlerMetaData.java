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
import java.util.Set;

import javax.xml.namespace.QName;

/** 
 * The unified metadata data for a handler element
 * 
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 */
public class UnifiedHandlerMetaData implements Serializable
{
   private static final long serialVersionUID = 1042008569778083467L;

   public enum HandlerType
   {
      PRE, ENDPOINT, POST, ALL
   }

   private volatile UnifiedHandlerChainMetaData handlerChain;

   // The required <handler-name> element
   private final String handlerName;
   // The required <handler-class> element
   private final String handlerClass;
   // The optional <init-param> elements
   private final List<UnifiedInitParamMetaData> initParams;
   // The optional <soap-header> elements
   private final Set<QName> soapHeaders;
   // The optional <soap-role> elements
   private final Set<String> soapRoles;
   // The optional <port-name> elements, these only apply to webservice clients
   private final Set<String> portNames;
   // An optional id
   private final String id;

   public UnifiedHandlerMetaData(String handlerClass,
                                 String handlerName,
                                 List<UnifiedInitParamMetaData> initParams,
                                 Set<QName> soapHeaders,
                                 Set<String> soapRoles,
                                 Set<String> portNames,
                                 String id) {
      this.handlerClass = handlerClass;
      this.handlerName = handlerName;
      this.initParams = initParams != null ? Collections.unmodifiableList(initParams) : null;
      if (soapHeaders != null && !soapHeaders.isEmpty()) {
         this.soapHeaders = Collections.unmodifiableSet(soapHeaders);
      } else {
         this.soapHeaders = Collections.emptySet();
      }
      if (soapRoles != null && !soapRoles.isEmpty()) {
         this.soapRoles = Collections.unmodifiableSet(soapRoles);
      } else {
         this.soapRoles = Collections.emptySet();
      }
      if (portNames != null && !portNames.isEmpty()) {
         this.portNames = Collections.unmodifiableSet(portNames);
      } else {
         this.portNames = Collections.emptySet();
      }
      this.id = id;
   }

   public UnifiedHandlerMetaData(String handlerClass,
           String handlerName,
           List<UnifiedInitParamMetaData> initParams,
           Set<QName> soapHeaders,
           Set<String> soapRoles,
           Set<String> portNames) {
	   this(handlerClass, handlerName, initParams, soapHeaders, soapRoles, portNames, handlerName);
   }
   
   public UnifiedHandlerChainMetaData getHandlerChain()
   {
      return handlerChain;
   }
   
   protected void setHandlerChain(UnifiedHandlerChainMetaData handlerChain)
   {
      this.handlerChain = handlerChain;
   }

   public String getHandlerName()
   {
      return handlerName;
   }

   public String getHandlerClass()
   {
      return handlerClass;
   }

   public String getId()
   {
      return id;
   }

   public List<UnifiedInitParamMetaData> getInitParams()
   {
      return initParams;
   }

   public Set<QName> getSoapHeaders()
   {
      return soapHeaders;
   }

   public Set<String> getSoapRoles()
   {
      return soapRoles;
   }

   public Set<String> getPortNames()
   {
      return portNames;
   }

   public String toString()
   {
      StringBuilder str = new StringBuilder();
      str.append("\nUnifiedHandlerMetaData");
      str.append("\n handlerName=" + handlerName);
      str.append("\n handlerClass=" + handlerClass);
      str.append("\n soapHeaders=" + soapHeaders);
      str.append("\n soapRoles=" + soapRoles);
      str.append("\n portNames=" + portNames);
      str.append("\n initParams=" + initParams);
      str.append("\n id=" + id);
      return str.toString();
   }
}
