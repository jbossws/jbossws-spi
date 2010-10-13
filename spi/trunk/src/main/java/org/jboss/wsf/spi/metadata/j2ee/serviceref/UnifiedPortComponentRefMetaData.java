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

/** The metdata data from service-ref/port-component-ref element in web.xml, ejb-jar.xml, and application-client.xml.
 *
 * @author Thomas.Diesler@jboss.org
 */
public class UnifiedPortComponentRefMetaData extends ServiceRefElement
{
   private static final long serialVersionUID = 8622309745808960649L;

   // The parent service-ref
   private UnifiedServiceRefMetaData serviceRefMetaData;

   // The required <service-endpoint-interface> element
   private String serviceEndpointInterface;
   // The optional <port-component-link> element
   private String portComponentLink;
   // The optional <port-qname> element
   private QName portQName;
   // Arbitrary proxy properties given by <call-property>
   private List<UnifiedCallPropertyMetaData> callProperties = new ArrayList<UnifiedCallPropertyMetaData>();
   // Arbitrary proxy properties given by <stub-property>
   private List<UnifiedStubPropertyMetaData> stubProperties = new ArrayList<UnifiedStubPropertyMetaData>();
   // The optional JBossWS config-name
   private String configName;
   // The optional JBossWS config-file
   private String configFile;
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

   public UnifiedPortComponentRefMetaData(UnifiedServiceRefMetaData serviceRefMetaData)
   {
      this.serviceRefMetaData = serviceRefMetaData;
   }

   public void merge(UnifiedPortComponentRefMetaData pcref)
   {
      portQName = pcref.portQName;
      configName = pcref.configName;
      configFile = pcref.configFile;
      callProperties = pcref.callProperties;
      stubProperties = pcref.stubProperties;
      addressingEnabled = pcref.addressingEnabled;
      addressingRequired = pcref.addressingRequired;
      addressingResponses = pcref.addressingResponses;
      mtomEnabled = pcref.mtomEnabled;
      mtomThreshold = pcref.mtomThreshold;
      respectBindingEnabled = pcref.respectBindingEnabled;
   }

   public UnifiedServiceRefMetaData getServiceRefMetaData()
   {
      return serviceRefMetaData;
   }

   /**
    * @deprecated Use {@link #isMtomEnabled()} instead.
    */
   @Deprecated
   public Boolean getEnableMTOM()
   {
      return mtomEnabled;
   }

   /**
    * @deprecated Use {@link #setMtomEnabled(boolean)} instead.
    */
   @Deprecated
   public void setEnableMTOM(Boolean enableMTOM)
   {
      this.mtomEnabled = enableMTOM;
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

   /** 
    * The port-component-link element links a port-component-ref
    * to a specific port-component required to be made available
    * by a service reference.
    * 
    * The value of a port-component-link must be the
    * port-component-name of a port-component in the same module
    * or another module in the same application unit. The syntax
    * for specification follows the syntax defined for ejb-link
    * in the EJB 2.0 specification.
    */
   public String getPortComponentLink()
   {
      return portComponentLink;
   }

   public void setPortComponentLink(String portComponentLink)
   {
      this.portComponentLink = portComponentLink;
   }

   public String getServiceEndpointInterface()
   {
      return serviceEndpointInterface;
   }

   public void setServiceEndpointInterface(String serviceEndpointInterface)
   {
      this.serviceEndpointInterface = serviceEndpointInterface;
   }

   public QName getPortQName()
   {
      return portQName;
   }

   public void setPortQName(QName portQName)
   {
      this.portQName = portQName;
   }

   public List<UnifiedCallPropertyMetaData> getCallProperties()
   {
      return callProperties;
   }

   public void setCallProperties(List<UnifiedCallPropertyMetaData> callProps)
   {
      callProperties = callProps;
   }

   public void addCallProperty(UnifiedCallPropertyMetaData callProp)
   {
      callProperties.add(callProp);
   }

   public List<UnifiedStubPropertyMetaData> getStubProperties()
   {
      return stubProperties;
   }

   public void setStubProperties(List<UnifiedStubPropertyMetaData> stubProps)
   {
      stubProperties = stubProps;
   }

   public void addStubProperty(UnifiedStubPropertyMetaData stubProp)
   {
      stubProperties.add(stubProp);
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public void setConfigFile(String configFile)
   {
      this.configFile = configFile;
   }

   public String getConfigName()
   {
      return configName;
   }

   public void setConfigName(String configName)
   {
      this.configName = configName;
   }

   public boolean matches(String seiName, QName portName)
   {
      if (seiName == null && portName == null)
         throw new IllegalArgumentException("Cannot match against seiName=null && portName=null.");

      boolean match = false;

      // match against portName first
      if (portName != null)
         match = portName.equals(getPortQName());

      // if it fails try seiName
      if (match == false)
         match = seiName.equals(getServiceEndpointInterface());

      return match;
   }

   public String toString()
   {
      StringBuilder str = new StringBuilder();
      str.append("\nUnifiedPortComponentRef");
      str.append("\n serviceEndpointInterface=" + serviceEndpointInterface);
      str.append("\n portQName=" + portQName);
      str.append("\n addressingEnabled=" + addressingEnabled);
      str.append("\n addressingRequired=" + addressingRequired);
      str.append("\n addressingResponses=" + addressingResponses);
      str.append("\n mtomEnabled=" + mtomEnabled);
      str.append("\n mtomThreshold=" + mtomThreshold);
      str.append("\n respectBindingEnabled=" + respectBindingEnabled);
      str.append("\n portComponentLink=" + portComponentLink);
      str.append("\n callProperties=" + callProperties);
      str.append("\n stubProperties=" + stubProperties);
      str.append("\n configName=" + configName);
      str.append("\n configFile=" + configFile);
      return str.toString();
   }
}
