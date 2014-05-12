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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.Messages;

/** The metadata data from service-ref/port-component-ref element in web.xml, ejb-jar.xml, and application-client.xml.
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 */
public class UnifiedPortComponentRefMetaData implements Serializable
{
   private static final long serialVersionUID = -152050329082506952L;

   // The required <service-endpoint-interface> element
   private final String serviceEndpointInterface;
   // The optional <port-component-link> element
   private final String portComponentLink;
   // The optional <port-qname> element
   private final QName portQName;
   // Arbitrary proxy properties given by <stub-property>
   private final List<UnifiedStubPropertyMetaData> stubProperties;
   // The optional JBossWS config-name
   private final String configName;
   // The optional JBossWS config-file
   private final String configFile;
   // The optional <adressing> element
   private final boolean addressingAnnotationSpecified;
   private final boolean addressingEnabled;
   private final boolean addressingRequired;
   private final String addressingResponses;
   // The optional <enable-mtom> element
   private final boolean mtomEnabled;
   // The optional <mtom-threshold> element
   private final int mtomThreshold;
   // @RespectBinding annotation metadata
   private final boolean respectBindingAnnotationSpecified;
   private final boolean respectBindingEnabled;

   public UnifiedPortComponentRefMetaData(boolean addressingAnnotationSpecified,
                                          boolean addressingEnabled,
                                          boolean addressingRequired,
                                          String addressingResponses,
                                          boolean mtomEnabled,
                                          int mtomThreshold,
                                          boolean respectBindingAnnotationSpecified,
                                          boolean respectBindingEnabled,
                                          String portComponentLink,
                                          String serviceEndpointInterface,
                                          QName portQName,
                                          List<UnifiedStubPropertyMetaData> stubProps,
                                          String configFile,
                                          String configName)
   {
      this.addressingAnnotationSpecified = addressingAnnotationSpecified;
      this.addressingEnabled = addressingEnabled;
      this.addressingRequired = addressingRequired;
      if (!"ANONYMOUS".equals(addressingResponses) && !"NON_ANONYMOUS".equals(addressingResponses) && !"ALL".equals(addressingResponses))
         throw Messages.MESSAGES.unsupportedAddressingResponseType(addressingResponses);
      this.addressingResponses = addressingResponses;
      this.mtomEnabled = mtomEnabled;
      this.mtomThreshold = mtomThreshold;
      this.respectBindingAnnotationSpecified = respectBindingAnnotationSpecified;
      this.respectBindingEnabled = respectBindingEnabled;
      this.portComponentLink = portComponentLink;
      this.serviceEndpointInterface = serviceEndpointInterface;
      this.portQName = portQName;
      if (stubProps != null && !stubProps.isEmpty()) {
         this.stubProperties = Collections.unmodifiableList(stubProps);
      } else {
         this.stubProperties = Collections.emptyList();
      }
      this.configFile = configFile;
      this.configName = configName;
   }

   public boolean isAddressingAnnotationSpecified()
   {
      return addressingAnnotationSpecified;
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

   public void setMtomEnabled(final boolean mtomEnabled) {
      
   }
   
   public boolean isMtomEnabled() {
      return this.mtomEnabled;
   }

   public int getMtomThreshold() {
      return this.mtomThreshold;
   }

   public boolean isRespectBindingAnnotationSpecified()
   {
      return respectBindingAnnotationSpecified;
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

   public String getServiceEndpointInterface()
   {
      return serviceEndpointInterface;
   }

   public QName getPortQName()
   {
      return portQName;
   }

   public List<UnifiedStubPropertyMetaData> getStubProperties()
   {
      return stubProperties;
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public String getConfigName()
   {
      return configName;
   }

   public boolean matches(String seiName, QName portName)
   {
      if (seiName == null && portName == null)
         throw Messages.MESSAGES.cannotMatchAgainstNull();

      boolean match = false;

      // match against portName first
      if (portName != null)
         match = portName.equals(getPortQName());

      // if it fails try seiName
      if (!match && seiName != null)
         match = seiName.equals(getServiceEndpointInterface());

      return match;
   }

   public String toString()
   {
      StringBuilder str = new StringBuilder();
      str.append("\nUnifiedPortComponentRef");
      str.append("\n serviceEndpointInterface=" + serviceEndpointInterface);
      str.append("\n portQName=" + portQName);
      str.append("\n addressingAnnotationSpecified=" + addressingAnnotationSpecified);
      str.append("\n addressingEnabled=" + addressingEnabled);
      str.append("\n addressingRequired=" + addressingRequired);
      str.append("\n addressingResponses=" + addressingResponses);
      str.append("\n mtomEnabled=" + mtomEnabled);
      str.append("\n mtomThreshold=" + mtomThreshold);
      str.append("\n respectBindingAnnotationSpecified=" + respectBindingAnnotationSpecified);
      str.append("\n respectBindingEnabled=" + respectBindingEnabled);
      str.append("\n portComponentLink=" + portComponentLink);
      str.append("\n stubProperties=" + stubProperties);
      str.append("\n configName=" + configName);
      str.append("\n configFile=" + configFile);
      return str.toString();
   }
}
