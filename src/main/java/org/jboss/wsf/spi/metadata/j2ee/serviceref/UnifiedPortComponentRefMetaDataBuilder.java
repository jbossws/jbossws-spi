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
