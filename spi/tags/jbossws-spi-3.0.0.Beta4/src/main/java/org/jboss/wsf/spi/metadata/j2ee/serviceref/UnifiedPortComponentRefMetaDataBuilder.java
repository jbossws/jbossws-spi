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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.Messages;

/**
 * Utility class for building UnifiedPortComponentRefMetaData instances
 *
 * @author alessio.soldano@jboss.com
 */
public class UnifiedPortComponentRefMetaDataBuilder
{
   // The required <service-endpoint-interface> element
   private String serviceEndpointInterface;
   // The optional <port-component-link> element
   private String portComponentLink;
   // The optional <port-qname> element
   private QName portQName;
   // Arbitrary proxy properties given by <stub-property>
   private List<UnifiedStubPropertyMetaData> stubProperties = new ArrayList<UnifiedStubPropertyMetaData>(2);
   // The optional JBossWS config-name
   private String configName;
   // The optional JBossWS config-file
   private String configFile;
   // The optional <adressing> element
   private boolean addressingAnnotationSpecified;
   private boolean addressingEnabled;
   private boolean addressingRequired;
   private String addressingResponses = "ALL";
   // The optional <enable-mtom> element
   private boolean mtomEnabled;
   // The optional <mtom-threshold> element
   private int mtomThreshold;
   // @RespectBinding annotation metadata
   private boolean respectBindingAnnotationSpecified;
   private boolean respectBindingEnabled;

   public UnifiedPortComponentRefMetaDataBuilder()
   {
      
   }
   
   public UnifiedPortComponentRefMetaData build() {
      return new UnifiedPortComponentRefMetaData(addressingAnnotationSpecified,
                                                 addressingEnabled,
                                                 addressingRequired,
                                                 addressingResponses,
                                                 mtomEnabled,
                                                 mtomThreshold,
                                                 respectBindingAnnotationSpecified,
                                                 respectBindingEnabled,
                                                 portComponentLink,
                                                 serviceEndpointInterface,
                                                 portQName,
                                                 stubProperties,
                                                 configFile,
                                                 configName);
   }

   public void setAddressingAnnotationSpecified(final boolean addressingAnnotationSpecified)
   {
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
         throw Messages.MESSAGES.unsupportedAddressingResponseType(responsesTypes);

      this.addressingResponses = responsesTypes;
   }
   
   public void setMtomEnabled(final boolean mtomEnabled) {
      this.mtomEnabled = mtomEnabled;
   }
   
   public void setMtomThreshold(final int mtomThreshold)
   {
      this.mtomThreshold = mtomThreshold;
   }
   
   public void setRespectBindingAnnotationSpecified(final boolean respectBindingAnnotationSpecified)
   {
      this.respectBindingAnnotationSpecified = respectBindingAnnotationSpecified;
   }

   public void setRespectBindingEnabled(final boolean respectBindingEnabled) {
      this.respectBindingEnabled = respectBindingEnabled;
   }
   
   public void setPortComponentLink(String portComponentLink)
   {
      this.portComponentLink = portComponentLink;
   }

   public void setServiceEndpointInterface(String serviceEndpointInterface)
   {
      this.serviceEndpointInterface = serviceEndpointInterface;
   }

   public void setPortQName(QName portQName)
   {
      this.portQName = portQName;
   }

   public void setStubProperties(List<UnifiedStubPropertyMetaData> stubProps)
   {
      stubProperties = stubProps;
   }

   public void addStubProperty(UnifiedStubPropertyMetaData stubProp)
   {
      stubProperties.add(stubProp);
   }

   public void setConfigFile(String configFile)
   {
      this.configFile = configFile;
   }

   public void setConfigName(String configName)
   {
      this.configName = configName;
   }

}
