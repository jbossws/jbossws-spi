/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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

import static org.jboss.wsf.spi.Messages.MESSAGES;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;

/**
 * Utility class for building UnifiedServiceRefMetaData instances
 * 
 * @author alessio.soldano@jboss.com
 */
public final class UnifiedServiceRefMetaDataBuilder
{
   private UnifiedVirtualFile vfsRoot;
   
   // Standard properties
   
   // The required <service-ref-name> element
   private String serviceRefName;
   // The JAXRPC required <service-interface> element
   private String serviceInterface;
   // service-res-type
   private String serviceRefType;
   // The optional <wsdl-file> element
   private String wsdlFile;
   // The optional <jaxrpc-mapping-file> element
   private String mappingFile;
   // The optional <service-qname> element
   private QName serviceQName;
   // The list <port-component-ref> elements
   private List<UnifiedPortComponentRefMetaData> portComponentRefs = new ArrayList<UnifiedPortComponentRefMetaData>(4);
   // The optional <handler> elements. JAX-RPC handlers declared in the standard J2EE1.4 descriptor
   private List<UnifiedHandlerMetaData> handlers = new ArrayList<UnifiedHandlerMetaData>(4);
   // The optional <handler-chains> elements. JAX-WS handlers declared in the standard JavaEE5 descriptor
   private UnifiedHandlerChainsMetaData handlerChains;

   // The optional <service-impl-class> element
   private String serviceImplClass;
   // The optional JBossWS config-name
   private String configName;
   // The optional JBossWS config-file
   private String configFile;
   // The optional URL of the actual WSDL to use, <wsdl-override> 
   private String wsdlOverride;
   // The optional <handler-chain> element. JAX-WS handler chain declared in the JBoss JavaEE5 descriptor
   private String handlerChain;
   // @Addressing annotation metadata
   private boolean addressingAnnotationSpecified;
   private boolean addressingEnabled;
   private boolean addressingRequired;
   private String addressingResponses = "ALL";
   // @MTOM annotation metadata
   private boolean mtomAnnotationSpecified;
   private boolean mtomEnabled;
   private int mtomThreshold;
   // @RespectBinding annotation metadata
   private boolean respectBindingAnnotationSpecified;
   private boolean respectBindingEnabled;

   public UnifiedServiceRefMetaDataBuilder()
   {
   }
   
   public UnifiedServiceRefMetaData build() {
      return new UnifiedServiceRefMetaData(vfsRoot,
                                           serviceRefName,
                                           serviceInterface,
                                           serviceRefType,
                                           wsdlFile,
                                           mappingFile,
                                           serviceQName,
                                           portComponentRefs,
                                           handlers,
                                           handlerChains,
                                           serviceImplClass,
                                           configName,
                                           configFile,
                                           wsdlOverride,
                                           handlerChain,
                                           new AddressingMetadata(addressingAnnotationSpecified, addressingEnabled, addressingRequired, addressingResponses),
                                           new MTOMMetadata(mtomAnnotationSpecified, mtomEnabled, mtomThreshold),
                                           new RespectBindingMetadata(respectBindingAnnotationSpecified, respectBindingEnabled));
   }

   public void setAddressingAnnotationSpecified(final boolean addressingAnnotationSpecified) {
      this.addressingAnnotationSpecified = addressingAnnotationSpecified;
   }
   
   public void setAddressingEnabled(final boolean addressingEnabled) {
      this.addressingEnabled = addressingEnabled;
   }
   
   public void setAddressingRequired(final boolean addressingRequired) {
      this.addressingRequired = addressingRequired;
   }
   
   public void setAddressingResponses(final String responsesTypes)
   {
      if (!"ANONYMOUS".equals(responsesTypes) && !"NON_ANONYMOUS".equals(responsesTypes) && !"ALL".equals(responsesTypes))
         throw MESSAGES.unsupportedAddressingResponseType(responsesTypes);

      this.addressingResponses = responsesTypes;
   }
   
   public void setMtomAnnotationSpecified(final boolean mtomAnnotationSpecified) {
      this.mtomAnnotationSpecified = mtomAnnotationSpecified;
   }
   
   public void setMtomEnabled(final boolean mtomEnabled) {
      this.mtomEnabled = mtomEnabled;
   }
   
   public void setMtomThreshold(final int mtomThreshold)
   {
      this.mtomThreshold = mtomThreshold;
   }
   
   public void setRespectBindingAnnotationSpecified(final boolean respectBindingAnnotationSpecified) {
      this.respectBindingAnnotationSpecified = respectBindingAnnotationSpecified;
   }
   
   public void setRespectBindingEnabled(final boolean respectBindingEnabled) {
      this.respectBindingEnabled = respectBindingEnabled;
   }
   
   public void setVfsRoot(UnifiedVirtualFile vfsRoot)
   {
      this.vfsRoot = vfsRoot;
   }
   
   public String getServiceRefName()
   {
      return serviceRefName;
   }

   public void setServiceRefName(String serviceRefName)
   {
      this.serviceRefName = serviceRefName;
   }

   public void setMappingFile(String mappingFile)
   {
      this.mappingFile = mappingFile;
   }

   public void addPortComponentRef(UnifiedPortComponentRefMetaData pcRef)
   {
      portComponentRefs.add(pcRef);
   }

   public void addHandler(UnifiedHandlerMetaData handler)
   {
      handlers.add(handler);
   }

   public void setServiceInterface(String serviceInterface)
   {
      this.serviceInterface = serviceInterface;
   }

   public void setServiceImplClass(String serviceImplClass)
   {
      this.serviceImplClass = serviceImplClass;
   }

   public void setServiceQName(QName serviceQName)
   {
      this.serviceQName = serviceQName;
   }

   public void setServiceRefType(String serviceResType)
   {
      this.serviceRefType = serviceResType;
   }

   public void setWsdlFile(String wsdlFile)
   {
      this.wsdlFile = wsdlFile;
   }

   public void setConfigFile(String configFile)
   {
      this.configFile = configFile;
   }

   public void setConfigName(String configName)
   {
      this.configName = configName;
   }

   public void setWsdlOverride(String wsdlOverride)
   {
      this.wsdlOverride = wsdlOverride;
   }

   public void setHandlerChains(UnifiedHandlerChainsMetaData handlerChains)
   {
      this.handlerChains = handlerChains;
   }

   public void setHandlerChain(String handlerChain)
   {
      this.handlerChain = handlerChain;
   }
}
