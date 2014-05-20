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
package org.jboss.wsf.spi.metadata.webservices;

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.Loggers;
import org.jboss.wsf.spi.Messages;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;

/**
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 15-April-2004
 */
public class PortComponentMetaData
{
   /**
    * The index of the webservice-description in webservices.xml
    */
   public static final String PARAMETER_WEBSERVICE_ID = "webserviceID";

   // The parent <webservice-description> element
   private volatile WebserviceDescriptionMetaData webserviceDescription;

   /** The required <port-component-name> element
    * This name bears no relationship to the WSDL port name.
    * This name must be unique amongst all port component names in a module.
    */
   private final String portComponentName;
   // The required <wsdl-port> element
   private volatile QName wsdlPort;
   // The required <service-endpoint-interface> element
   private final String serviceEndpointInterface;
   // The required <ejb-link> or <servlet-link> in the <service-impl-bean> element
   private final String ejbLink;
   private final String servletLink;
   // The optional <handler> elements
   private final List<UnifiedHandlerMetaData> handlers;

   // The HTTP context root
   private final String contextRoot;

   // -----------------------------------------
   // JAX-WS additions

   // The optional <adressing> element
   private final boolean addressingEnabled;
   private final boolean addressingRequired;
   private final String addressingResponses;
   // The optional <enable-mtom> element
   private final boolean mtomEnabled;
   // The optional <mtom-threshold> element
   private final int mtomThreshold;
   // @RespectBinding annotation metadata
   private final boolean respectBindingEnabled;
   private final QName wsdlService;
   private final String protocolBinding;
   private final UnifiedHandlerChainsMetaData handlerChains;

   public PortComponentMetaData(String portComponentName,
                                QName wsdlPort,
                                String serviceEndpointInterface,
                                String ejbLink,
                                String servletLink,
                                List<UnifiedHandlerMetaData> handlers,
                                String contextRoot,
                                boolean addressingEnabled,
                                boolean addressingRequired,
                                String addressingResponses,
                                boolean mtomEnabled,
                                int mtomThreshold,
                                boolean respectBindingEnabled,
                                QName wsdlService,
                                String protocolBinding,
                                UnifiedHandlerChainsMetaData handlerChains)
   {
      this.portComponentName = portComponentName;
      if (wsdlPort.getNamespaceURI().length() == 0)
         Loggers.METADATA_LOGGER.webservicesXmlElementNotNamespaceQualified(wsdlPort);
      this.wsdlPort = wsdlPort;
      this.serviceEndpointInterface = serviceEndpointInterface;
      this.ejbLink = ejbLink;
      this.servletLink = servletLink;
      if (handlers != null && !handlers.isEmpty()) {
         this.handlers = Collections.unmodifiableList(handlers);
      } else {
         this.handlers = Collections.emptyList();
      }
      this.contextRoot = contextRoot;
      this.addressingEnabled = addressingEnabled;
      this.addressingRequired = addressingRequired;
      if (!"ANONYMOUS".equals(addressingResponses) && !"NON_ANONYMOUS".equals(addressingResponses) && !"ALL".equals(addressingResponses))
         throw Messages.MESSAGES.unsupportedAddressingResponseType(addressingResponses);
      this.addressingResponses = addressingResponses;
      this.mtomEnabled = mtomEnabled;
      this.mtomThreshold = mtomThreshold;
      this.respectBindingEnabled = respectBindingEnabled;
      this.wsdlService = wsdlService;
      this.protocolBinding = protocolBinding;
      this.handlerChains = handlerChains;
   }

   public PortComponentMetaData(String portComponentName, QName wsdlPort, String serviceEndpointInterface,
         String ejbLink, String servletLink, List<UnifiedHandlerMetaData> handlers, String contextRoot,
         QName wsdlService, String protocolBinding, UnifiedHandlerChainsMetaData handlerChains)
   {
      this(portComponentName, wsdlPort, serviceEndpointInterface, ejbLink, servletLink, handlers, contextRoot,
            false, false, "ALL", false, 0, false, wsdlService, protocolBinding, handlerChains);
   }
   
   public WebserviceDescriptionMetaData getWebserviceDescription()
   {
      return webserviceDescription;
   }
   
   protected void setWebserviceDescription(WebserviceDescriptionMetaData webserviceDescription)
   {
      this.webserviceDescription = webserviceDescription;
   }

   public String getPortComponentName()
   {
      return portComponentName;
   }

   public QName getWsdlPort()
   {
      return wsdlPort;
   }

   /**
    * set the wsdlPort for this PortComponentMetaData. This is deprecated, the proper wsdlPort
    * should be provided when creating a new instance.
    * 
    * @return
    */
   @Deprecated
   public void setWsdlPort(QName wsdlPort)
   {
      this.wsdlPort = wsdlPort;
   }

   public String getEjbLink()
   {
      return ejbLink;
   }

   public String getServletLink()
   {
      return servletLink;
   }

   public String getServiceEndpointInterface()
   {
      return serviceEndpointInterface;
   }

   public void addHandler(UnifiedHandlerMetaData handler)
   {
      handlers.add(handler);
   }

   public UnifiedHandlerMetaData[] getHandlers()
   {
      UnifiedHandlerMetaData[] array = new UnifiedHandlerMetaData[handlers.size()];
      handlers.toArray(array);
      return array;
   }

   public String getContextRoot()
   {
      return contextRoot;
   }

   public boolean isAddressingEnabled() {
      return this.addressingEnabled;
   }

   public boolean isAddressingRequired() {
      return this.addressingRequired;
   }
   
   public String getAddressingResponses() {
      return this.addressingResponses;
   }

   public boolean isMtomEnabled() {
      return this.mtomEnabled;
   }

   public int getMtomThreshold() {
      return this.mtomThreshold;
   }

   public boolean isRespectBindingEnabled() {
      return this.respectBindingEnabled;
   }

   public QName getWsdlService()
   {
      return wsdlService;
   }

   public String getProtocolBinding()
   {
      return protocolBinding;
   }

   public UnifiedHandlerChainsMetaData getHandlerChains()
   {
      return handlerChains;
   }

   public String serialize()
   {
      final QName wsdlPort = getWsdlPort();
      StringBuilder builder = new StringBuilder("<port-component>");
      builder.append("<port-component-name>").append(portComponentName).append("</port-component-name>");
      builder.append("<wsdl-port xmlns:").append(wsdlPort.getPrefix()).append("='").append(wsdlPort.getNamespaceURI()).append("'>");
      builder.append(wsdlPort.getPrefix()).append(':').append(wsdlPort.getLocalPart()).append("</wsdl-port>");
      builder.append("<service-endpoint-interface>").append(serviceEndpointInterface).append("</service-endpoint-interface>");
      builder.append("<service-impl-bean>");
      if (ejbLink != null)
         builder.append("<ejb-link>" + ejbLink + "</ejb-link>");
      else
         builder.append("<servlet-link>"+ servletLink + "</servlet-link>");
      builder.append("</service-impl-bean>");
      builder.append("</port-component>");
      return builder.toString();
   }
}
