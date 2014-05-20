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
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.Loggers;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;

/**
 * The metadata from service-ref element in web.xml, ejb-jar.xml,
 * application-client.xml and @WebServiceRef annotations.
 * The class is threadsafe; the information coming from descriptors
 * only is immutable, while the information that can be overridden
 * by annotations are stored using volatile references. 
 * 
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class UnifiedServiceRefMetaData implements Serializable
{
   private static final long serialVersionUID = -3140013087984532678L;

   private volatile transient UnifiedVirtualFile vfsRoot;
   
   // Standard properties
   
   // The required <service-ref-name> element
   private final String serviceRefName;
   // The <service-interface> element
   private volatile String serviceInterface;
   // service-res-type
   private volatile String serviceRefType;
   // The optional <wsdl-file> element
   private volatile String wsdlFile;
   
   // The optional <jaxrpc-mapping-file> element
   private final String mappingFile;
   // The optional <service-qname> element
   private final QName serviceQName;
   // The list <port-component-ref> elements
   private final List<UnifiedPortComponentRefMetaData> portComponentRefs;
   // The optional <handler> elements.
   private final List<UnifiedHandlerMetaData> handlers;
   // The optional <handler-chains> elements. JAX-WS handlers declared in the standard JavaEE5 descriptor
   private final UnifiedHandlerChainsMetaData handlerChains;
   // The optional <service-impl-class> element
   private final String serviceImplClass;
   // The optional JBossWS config-name
   private final String configName;
   // The optional JBossWS config-file
   private final String configFile;
   // The optional URL of the actual WSDL to use, <wsdl-override> 
   private final String wsdlOverride;
   
   // The optional <handler-chain> element. JAX-WS handler chain declared in the JBoss JavaEE5 descriptor
   private volatile String handlerChain;
   // @Addressing annotation metadata
   private volatile AddressingMetadata addressingMetadata = new AddressingMetadata(false, false, false, "ALL");
   // @MTOM annotation metadata
   private volatile MTOMMetadata mtomMetadata = new MTOMMetadata(false,  false,  0);
   // @RespectBinding annotation metadata
   private volatile RespectBindingMetadata respectBindingMetadata = new RespectBindingMetadata(false, false);
   
   private final Map<QName, String> deployedServiceAddresses = Collections.synchronizedMap(new HashMap<QName, String>());
   
   public UnifiedServiceRefMetaData(UnifiedVirtualFile vfsRoot, String serviceRefName)
   {
      this(vfsRoot, serviceRefName, null, null, null, null, null, null, null, null, null);
   }

   public UnifiedServiceRefMetaData(UnifiedVirtualFile vfsRoot,
                                    String serviceRefName,
                                    String mappingFile,
                                    QName serviceQName,
                                    List<UnifiedPortComponentRefMetaData> portComponentRefs,
                                    List<UnifiedHandlerMetaData> handlers,
                                    UnifiedHandlerChainsMetaData handlerChains,
                                    String serviceImplClass,
                                    String configName,
                                    String configFile,
                                    String wsdlOverride)
   {
      this.vfsRoot = vfsRoot;
      this.serviceRefName = serviceRefName;
      this.mappingFile = mappingFile;
      this.serviceQName = serviceQName;
      if (portComponentRefs != null && !portComponentRefs.isEmpty()) {
         this.portComponentRefs = Collections.unmodifiableList(portComponentRefs);
      } else {
         this.portComponentRefs = Collections.emptyList();
      }
      if (handlers != null && !handlers.isEmpty()) {
         this.handlers = Collections.unmodifiableList(handlers);
      } else {
         this.handlers = Collections.emptyList();
      }
      this.handlerChains = handlerChains;
      this.serviceImplClass = serviceImplClass;
      this.configName = configName;
      this.configFile = configFile;
      this.wsdlOverride = wsdlOverride;
   }
   
   

   public UnifiedServiceRefMetaData(UnifiedVirtualFile vfsRoot, String serviceRefName,
         String serviceInterface, String serviceRefType, String wsdlFile, String mappingFile, QName serviceQName,
         List<UnifiedPortComponentRefMetaData> portComponentRefs, List<UnifiedHandlerMetaData> handlers,
         UnifiedHandlerChainsMetaData handlerChains, String serviceImplClass, String configName, String configFile,
         String wsdlOverride, String handlerChain, AddressingMetadata addressingMetadata, MTOMMetadata mtomMetadata,
         RespectBindingMetadata respectBindingMetadata)
   {
      this.vfsRoot = vfsRoot;
      this.serviceRefName = serviceRefName;
      this.serviceInterface = serviceInterface;
      this.serviceRefType = serviceRefType;
      this.wsdlFile = wsdlFile;
      this.mappingFile = mappingFile;
      this.serviceQName = serviceQName;
      if (portComponentRefs != null && !portComponentRefs.isEmpty()) {
         this.portComponentRefs = Collections.unmodifiableList(portComponentRefs);
      } else {
         this.portComponentRefs = Collections.emptyList();
      }
      if (handlers != null && !handlers.isEmpty()) {
         this.handlers = Collections.unmodifiableList(handlers);
      } else {
         this.handlers = Collections.emptyList();
      }
      this.handlerChains = handlerChains;
      this.serviceImplClass = serviceImplClass;
      this.configName = configName;
      this.configFile = configFile;
      this.wsdlOverride = wsdlOverride;
      this.handlerChain = handlerChain;
      this.addressingMetadata = addressingMetadata;
      this.mtomMetadata = mtomMetadata;
      this.respectBindingMetadata = respectBindingMetadata;
   }

   public boolean isAddressingAnnotationSpecified() {
      return this.addressingMetadata.isAnnotationSpecified();
   }

   public boolean isAddressingEnabled() {
      return this.addressingMetadata.isEnabled();
   }

   public boolean isAddressingRequired() {
      return this.addressingMetadata.isRequired();
   }
   
   public void setAddressingMedadata(AddressingMetadata addressingMetadata) {
      this.addressingMetadata = addressingMetadata;
   }
   
   public String getAddressingResponses() {
      return this.addressingMetadata.getResponses();
   }

   public boolean isMtomAnnotationSpecified() {
      return this.mtomMetadata.isAnnotationSpecified();
   }

   public boolean isMtomEnabled() {
      return this.mtomMetadata.isEnabled();
   }

   public int getMtomThreshold() {
      return this.mtomMetadata.getThreshold();
   }
   
   public void setMTOMMetadata(MTOMMetadata mtomMetadata) {
      this.mtomMetadata = mtomMetadata;
   }

   public boolean isRespectBindingAnnotationSpecified() {
      return this.respectBindingMetadata.isAnnotationSpecified();
   }

   public boolean isRespectBindingEnabled() {
      return this.respectBindingMetadata.isEnabled();
   }
   
   public void setRespectBindingMetadata(RespectBindingMetadata respectBindingMetadata) {
      this.respectBindingMetadata = respectBindingMetadata;
   }

   public UnifiedVirtualFile getVfsRoot()
   {
      return vfsRoot;
   }

   public void setVfsRoot(UnifiedVirtualFile vfsRoot)
   {
      this.vfsRoot = vfsRoot;
   }
   
   public String getServiceRefName()
   {
      return serviceRefName;
   }

   public String getMappingFile()
   {
      return mappingFile;
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
              mappingURL = getContextClassLoader().getResource(mappingFile);
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

   public List<UnifiedHandlerMetaData> getHandlers()
   {
      return handlers;
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

   public QName getServiceQName()
   {
      return serviceQName;
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
      return this.getWsdlLocation(this.wsdlOverride);
   }

   public URL getWsdlLocation(final String wsdlOverride)
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
                   wsdlLocation = AccessController.doPrivileged(new PrivilegedExceptionAction<URL>() {
                      public URL run() throws Exception {
                         return vfsRoot.findChild(wsdlOverride).toURL();
                      }
                   });
                }
                catch (Exception e)
                {
                    throw MESSAGES.cannotFindFile(e, wsdlOverride);
                }
            } else {
                wsdlLocation = getContextClassLoader().getResource(wsdlOverride);
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
                   wsdlLocation = AccessController.doPrivileged(new PrivilegedExceptionAction<URL>() {
                      public URL run() throws Exception {
                         return vfsRoot.findChild(wsdlFile).toURL();
                      }
                   });
                }
                catch (Exception e)
                {
                    throw MESSAGES.cannotFindFile(e, wsdlFile);
                }
            } else {
                wsdlLocation = getContextClassLoader().getResource(wsdlFile);
            }
         }
      }
      return wsdlLocation;
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public String getConfigName()
   {
      return configName;
   }

   public String getWsdlOverride()
   {
      return wsdlOverride;
   }

   public UnifiedHandlerChainsMetaData getHandlerChains()
   {
      return handlerChains;
   }

   public String getHandlerChain()
   {
      return handlerChain;
   }

   public void setHandlerChain(String handlerChain)
   {
      this.handlerChain = handlerChain;
   }
   
   public String getDeployedServiceAddress(QName serviceQName)
   {
      return deployedServiceAddresses.get(serviceQName);
   }

   public void addDeployedServiceAddresses(Map<QName, String> addressMap)
   {
      deployedServiceAddresses.putAll(addressMap);
   }

   private static ClassLoader getContextClassLoader()
   {
      if (System.getSecurityManager() == null)
      {
         return Thread.currentThread().getContextClassLoader();
      }
      else
      {
         return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>()
         {
            public ClassLoader run()
            {
               return Thread.currentThread().getContextClassLoader();
            }
         });
      }
   }

   public String toString()
   {
      StringBuilder str = new StringBuilder();
      str.append("\nUnifiedServiceRef");
      str.append("\n serviceRefName=" + serviceRefName);
      str.append("\n serviceInterface=" + serviceInterface);
      str.append("\n serviceImplClass=" + serviceImplClass);
      str.append("\n serviceRefType=" + serviceRefType);
      str.append("\n serviceQName=" + serviceQName);
      str.append("\n wsdlFile=" + wsdlFile);
      str.append("\n wsdlOverride=" + wsdlOverride);
      str.append("\n mappingFile=" + mappingFile);
      str.append("\n configName=" + configName);
      str.append("\n configFile=" + configFile);
      str.append("\n addressingAnnotationSpecified=" + addressingMetadata.isAnnotationSpecified());
      str.append("\n addressingEnabled=" + addressingMetadata.isEnabled());
      str.append("\n addressingRequired=" + addressingMetadata.isRequired());
      str.append("\n addressingResponses=" + addressingMetadata.getResponses());
      str.append("\n mtomAnnotationSpecified=" + mtomMetadata.isAnnotationSpecified());
      str.append("\n mtomEnabled=" + mtomMetadata.isEnabled());
      str.append("\n mtomThreshold=" + mtomMetadata.getThreshold());
      str.append("\n respectBindingAnnotationSpecified=" + respectBindingMetadata.isAnnotationSpecified());
      str.append("\n respectBindingEnabled=" + respectBindingMetadata.isEnabled());
      str.append("\n handlerChains=" + handlerChains);
      str.append("\n handlerChain=" + handlerChain);
      for (UnifiedHandlerMetaData uhmd : handlers)
         str.append(uhmd.toString());
      for (UnifiedPortComponentRefMetaData pcref : portComponentRefs)
         str.append(pcref.toString());
      return str.toString();
   }
}
