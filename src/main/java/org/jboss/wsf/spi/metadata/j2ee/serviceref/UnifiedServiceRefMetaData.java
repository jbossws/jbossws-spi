/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.Loggers;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.serviceref.ServiceRefType;

/**
 * The metadata from service-ref element in web.xml, ejb-jar.xml, and
 * application-client.xml.
 * 
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class UnifiedServiceRefMetaData implements Serializable
{
   private static final long serialVersionUID = 1L;
   private transient UnifiedVirtualFile vfsRoot;
   
   // Standard properties
   
   // Service reference type - either JAX-RPC or JAXWS
   private ServiceRefType type;
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
   private boolean isAddressingAnnotationSpecified;
   private boolean addressingEnabled;
   private boolean addressingRequired;
   private String addressingResponses = "ALL";
   // @MTOM annotation metadata
   private boolean isMtomAnnotationSpecified;
   private boolean mtomEnabled;
   private int mtomThreshold;
   // @RespectBinding annotation metadata
   private boolean isRespectBindingAnnotationSpecified;
   private boolean respectBindingEnabled;

   public UnifiedServiceRefMetaData(UnifiedVirtualFile vfRoot)
   {
      this.vfsRoot = vfRoot;
   }

   public UnifiedServiceRefMetaData()
   {
   }

   public void setAddressingAnnotationSpecified(final boolean isAddressingAnnotationSpecified) {
      this.isAddressingAnnotationSpecified = isAddressingAnnotationSpecified;
   }
   
   public boolean isAddressingAnnotationSpecified() {
      return this.isAddressingAnnotationSpecified;
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
         throw MESSAGES.unsupportedAddressingResponseType(responsesTypes);

      this.addressingResponses = responsesTypes;
   }
   
   public String getAddressingResponses() {
      return this.addressingResponses;
   }

   public void setMtomAnnotationSpecified(final boolean isMtomAnnotationSpecified) {
      this.isMtomAnnotationSpecified = isMtomAnnotationSpecified;
   }
   
   public boolean isMtomAnnotationSpecified() {
      return this.isMtomAnnotationSpecified;
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

   public void setRespectBindingAnnotationSpecified(final boolean isRespectBindingAnnotationSpecified) {
      this.isRespectBindingAnnotationSpecified = isRespectBindingAnnotationSpecified;
   }
   
   public boolean isRespectBindingAnnotationSpecified() {
      return this.isRespectBindingAnnotationSpecified;
   }

   public void setRespectBindingEnabled(final boolean respectBindingEnabled) {
      this.respectBindingEnabled = respectBindingEnabled;
   }
   
   public boolean isRespectBindingEnabled() {
      return this.respectBindingEnabled;
   }

   public UnifiedVirtualFile getVfsRoot()
   {
      return vfsRoot;
   }

   public void setVfsRoot(UnifiedVirtualFile vfsRoot)
   {
      this.vfsRoot = vfsRoot;
   }
   
   public ServiceRefType getType()
   {
      return type;
   }

   public void setType(ServiceRefType type)
   {
      this.type = type;
   }

   public String getServiceRefName()
   {
      return serviceRefName;
   }

   public void setServiceRefName(String serviceRefName)
   {
      this.serviceRefName = serviceRefName;
   }

   public String getMappingFile()
   {
      return mappingFile;
   }

   public void setMappingFile(String mappingFile)
   {
      this.mappingFile = mappingFile;
   }

   public URL getMappingLocation()
   {
      URL mappingURL = null;
      if (mappingFile != null) {
          if (vfsRoot != null) {
              try
              {
                  mappingURL = vfsRoot.findChild(mappingFile).toURL();
              }
              catch (Exception e)
              {
                  throw MESSAGES.cannotFindFile(e, mappingFile);
              }
          } else {
              mappingURL = Thread.currentThread().getContextClassLoader().getResource(mappingFile);
          }
      }
      return mappingURL;
   }

   public Collection<UnifiedPortComponentRefMetaData> getPortComponentRefs()
   {
      return portComponentRefs;
   }

   public UnifiedPortComponentRefMetaData getPortComponentRef(String seiName, QName portName)
   {
      UnifiedPortComponentRefMetaData matchingRef = null;
      for (UnifiedPortComponentRefMetaData ref : portComponentRefs)
      {
         if (ref.matches(seiName, portName))
         {
            if (matchingRef != null)
               Loggers.METADATA_LOGGER.multipleMatchingPortComponentRef(seiName, portName);

            matchingRef = ref;
         }
      }
      return matchingRef;
   }

   public void addPortComponentRef(UnifiedPortComponentRefMetaData pcRef)
   {
      portComponentRefs.add(pcRef);
   }

   public List<UnifiedHandlerMetaData> getHandlers()
   {
      return handlers;
   }

   public void addHandler(UnifiedHandlerMetaData handler)
   {
      handlers.add(handler);
   }

   public String getServiceInterface()
   {
      return serviceInterface;
   }

   public void setServiceInterface(String serviceInterface)
   {
      this.serviceInterface = serviceInterface;
   }

   public String getServiceImplClass()
   {
      return serviceImplClass;
   }

   public void setServiceImplClass(String serviceImplClass)
   {
      this.serviceImplClass = serviceImplClass;
   }

   public QName getServiceQName()
   {
      return serviceQName;
   }

   public void setServiceQName(QName serviceQName)
   {
      this.serviceQName = serviceQName;
   }

   public String getServiceRefType()
   {
      return serviceRefType;
   }

   public void setServiceRefType(String serviceResType)
   {
      this.serviceRefType = serviceResType;
   }

   public String getWsdlFile()
   {
      return wsdlFile;
   }

   public void setWsdlFile(String wsdlFile)
   {
      this.wsdlFile = wsdlFile;
   }

   public URL getWsdlLocation()
   {
      URL wsdlLocation = null;
      if (wsdlOverride != null)
      {
         try
         {
            wsdlLocation = new URL(wsdlOverride);
         }
         catch (MalformedURLException e1)
         {
            if (vfsRoot != null) {
                try
                {
                    wsdlLocation = vfsRoot.findChild(wsdlOverride).toURL();
                }
                catch (Exception e)
                {
                    throw MESSAGES.cannotFindFile(e, wsdlOverride);
                }
            } else {
                wsdlLocation = Thread.currentThread().getContextClassLoader().getResource(wsdlOverride);
            }
         }
      }

      if (wsdlLocation == null && wsdlFile != null)
      {
         try
         {
            wsdlLocation = new URL(wsdlFile);
         }
         catch (MalformedURLException e1)
         {
            if (vfsRoot != null) {
                try
                {
                    wsdlLocation = vfsRoot.findChild(wsdlFile).toURL();
                }
                catch (Exception e)
                {
                    throw MESSAGES.cannotFindFile(e, wsdlFile);
                }
            } else {
                wsdlLocation = Thread.currentThread().getContextClassLoader().getResource(wsdlFile);
            }
         }
      }
      return wsdlLocation;
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

   public String getWsdlOverride()
   {
      return wsdlOverride;
   }

   public void setWsdlOverride(String wsdlOverride)
   {
      this.wsdlOverride = wsdlOverride;
   }

   public UnifiedHandlerChainsMetaData getHandlerChains()
   {
      return handlerChains;
   }

   public void setHandlerChains(UnifiedHandlerChainsMetaData handlerChains)
   {
      this.handlerChains = handlerChains;
   }

   public String getHandlerChain()
   {
      return handlerChain;
   }

   public void setHandlerChain(String handlerChain)
   {
      this.handlerChain = handlerChain;
   }

   public String toString()
   {
      StringBuilder str = new StringBuilder();
      str.append("\nUnifiedServiceRef");
      str.append("\n serviceRefName=" + serviceRefName);
      str.append("\n type=" + type);
      str.append("\n serviceInterface=" + serviceInterface);
      str.append("\n serviceImplClass=" + serviceImplClass);
      str.append("\n serviceRefType=" + serviceRefType);
      str.append("\n serviceQName=" + serviceQName);
      str.append("\n wsdlFile=" + wsdlFile);
      str.append("\n wsdlOverride=" + wsdlOverride);
      str.append("\n mappingFile=" + mappingFile);
      str.append("\n configName=" + configName);
      str.append("\n configFile=" + configFile);
      str.append("\n addressingAnnotationSpecified=" + isAddressingAnnotationSpecified);
      str.append("\n addressingEnabled=" + addressingEnabled);
      str.append("\n addressingRequired=" + addressingRequired);
      str.append("\n addressingResponses=" + addressingResponses);
      str.append("\n mtomAnnotationSpecified=" + isMtomAnnotationSpecified);
      str.append("\n mtomEnabled=" + mtomEnabled);
      str.append("\n mtomThreshold=" + mtomThreshold);
      str.append("\n respectBindingAnnotationSpecified=" + isRespectBindingAnnotationSpecified);
      str.append("\n respectBindingEnabled=" + respectBindingEnabled);
      str.append("\n handlerChains=" + handlerChains);
      str.append("\n handlerChain=" + handlerChain);
      for (UnifiedHandlerMetaData uhmd : handlers)
         str.append(uhmd.toString());
      for (UnifiedPortComponentRefMetaData pcref : portComponentRefs)
         str.append(pcref.toString());
      return str.toString();
   }
}
