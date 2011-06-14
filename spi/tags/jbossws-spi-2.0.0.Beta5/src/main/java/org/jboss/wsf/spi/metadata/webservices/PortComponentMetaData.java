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
package org.jboss.wsf.spi.metadata.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;

/**
 * XML Binding and ws4ee meta-data element for
 * <code>webservices/webservice-description/port-component</code>
 * <p/>
 * A port component is the equivalent of an ordinary Axis service (and
 * as such it constitutes the building blocks for jaxrpc services).
 * <p/>
 *
 * @author Thomas.Diesler@jboss.org
 * @since 15-April-2004
 */
public class PortComponentMetaData
{
   /**
    * The index of the webservice-description in webservices.xml
    */
   public static final String PARAMETER_WEBSERVICE_ID = "webserviceID";

   // provide logging
   private static final Logger log = Logger.getLogger(PortComponentMetaData.class);

   // The parent <webservice-description> element
   private WebserviceDescriptionMetaData webserviceDescription;

   /** The required <port-component-name> element
    * This name bears no relationship to the WSDL port name.
    * This name must be unique amongst all port component names in a module.
    */
   private String portComponentName;
   // The required <wsdl-port> element
   private QName wsdlPort;
   // The required <service-endpoint-interface> element
   private String serviceEndpointInterface;
   // The required <ejb-link> or <servlet-link> in the <service-impl-bean> element
   private String ejbLink;
   private String servletLink;
   // The optional <handler> elements
   private List<UnifiedHandlerMetaData> handlers = new ArrayList<UnifiedHandlerMetaData>();

   // The HTTP context root
   private String contextRoot;
   // The optional secure wsdl access
   private Boolean secureWSDLAccess;

   // -----------------------------------------
   // JAX-WS additions

   // The optional <adressing> element
   private boolean addressingEnabled;
   private boolean addressingRequired;
   private String addressingResponses = "ALL";
   // The optional <enable-mtom> element
   private boolean mtomEnabled;
   // The optional <mtom-threshold> element
   private int mtomThreshold;
   // @RespectBinding annotation metadata
   private boolean respectBindingEnabled;
   private QName wsdlService;
   private String protocolBinding;
   private UnifiedHandlerChainsMetaData handlerChains;


   /** Construct a new PortComponentMetaData for a given WebserviceDescriptionMetaData
    */
   public PortComponentMetaData(WebserviceDescriptionMetaData webserviceDescription)
   {
      this.webserviceDescription = webserviceDescription;
   }

   public WebserviceDescriptionMetaData getWebserviceDescription()
   {
      return webserviceDescription;
   }

   public String getPortComponentName()
   {
      return portComponentName;
   }

   public void setPortComponentName(String portComponentName)
   {
      this.portComponentName = portComponentName;
   }

   public QName getWsdlPort()
   {
      return wsdlPort;
   }

   public void setWsdlPort(QName wsdlPort)
   {
      if (wsdlPort.getNamespaceURI().length() == 0)
         log.warn("<wsdl-port> element in webservices.xml not namespace qualified: " + wsdlPort);

      this.wsdlPort = wsdlPort;
   }

   public String getEjbLink()
   {
      return ejbLink;
   }

   public void setEjbLink(String ejbLink)
   {
      this.ejbLink = ejbLink;
   }

   public String getServletLink()
   {
      return servletLink;
   }

   public void setServletLink(String servletLink)
   {
      this.servletLink = servletLink;
   }

   public String getServiceEndpointInterface()
   {
      return serviceEndpointInterface;
   }

   public void setServiceEndpointInterface(String serviceEndpointInterface)
   {
      this.serviceEndpointInterface = serviceEndpointInterface;
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

   public void setContextRoot(String contextRoot)
   {
      this.contextRoot = contextRoot;
   }

   public Boolean getSecureWSDLAccess()
   {
      return secureWSDLAccess;
   }

   public void setSecureWSDLAccess(Boolean secureWSDLAccess)
   {
      this.secureWSDLAccess = secureWSDLAccess;
   }

   public void setAddressingEnabled(final boolean addressingEnabled) {
      this.addressingEnabled = addressingEnabled;
   }
   
   public boolean isAddressingEnabled() {
      return this.addressingEnabled;
   }

   public void setAddressingRequired(final boolean addressingRequired) {
      this.addressingRequired = addressingRequired;
   }
   
   public boolean isAddressingRequired() {
      return this.addressingRequired;
   }
   
   public void setAddressingResponses(final String responsesTypes)
   {
      if (!"ANONYMOUS".equals(responsesTypes) && !"NON_ANONYMOUS".equals(responsesTypes) && !"ALL".equals(responsesTypes))
         throw new IllegalArgumentException("Only ALL, ANONYMOUS or NON_ANONYMOUS strings are allowed");

      this.addressingResponses = responsesTypes;
   }
   
   public String getAddressingResponses() {
      return this.addressingResponses;
   }

   public void setMtomEnabled(final boolean mtomEnabled) {
      this.mtomEnabled = mtomEnabled;
   }
   
   public boolean isMtomEnabled() {
      return this.mtomEnabled;
   }

   public void setMtomThreshold(final int mtomThreshold)
   {
      this.mtomThreshold = mtomThreshold;
   }
   
   public int getMtomThreshold() {
      return this.mtomThreshold;
   }

   public void setRespectBindingEnabled(final boolean respectBindingEnabled) {
      this.respectBindingEnabled = respectBindingEnabled;
   }
   
   public boolean isRespectBindingEnabled() {
      return this.respectBindingEnabled;
   }

   public QName getWsdlService()
   {
      return wsdlService;
   }

   public void setWsdlService(QName wsdlService)
   {
      this.wsdlService = wsdlService;
   }

   public String getProtocolBinding()
   {
      return protocolBinding;
   }

   public void setProtocolBinding(String protocolBinding)
   {
      this.protocolBinding = protocolBinding;
   }

   public UnifiedHandlerChainsMetaData getHandlerChains()
   {
      return handlerChains;
   }

   public void setHandlerChains(UnifiedHandlerChainsMetaData handlerChains)
   {
      this.handlerChains = handlerChains;
   }

   public String serialize()
   {
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
