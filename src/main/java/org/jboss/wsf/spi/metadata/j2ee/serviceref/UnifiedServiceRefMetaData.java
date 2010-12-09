/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.deployment.WritableUnifiedVirtualFile;
import org.jboss.wsf.spi.serviceref.ServiceRefElement;
import org.jboss.wsf.spi.util.URLLoaderAdapter;

/**
 * The metadata from service-ref element in web.xml, ejb-jar.xml, and
 * application-client.xml.
 * 
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class UnifiedServiceRefMetaData extends ServiceRefElement
{
   private static final long serialVersionUID = -926464174132493955L;

   // provide logging
   private static Logger log = Logger.getLogger(UnifiedServiceRefMetaData.class);

   private transient UnifiedVirtualFile vfsRoot;

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
   private List<UnifiedPortComponentRefMetaData> portComponentRefs = new ArrayList<UnifiedPortComponentRefMetaData>();
   // The optional <handler> elements. JAX-RPC handlers declared in the standard J2EE1.4 descriptor
   private List<UnifiedHandlerMetaData> handlers = new ArrayList<UnifiedHandlerMetaData>();
   // The optional <handler-chains> elements. JAX-WS handlers declared in the standard JavaEE5 descriptor
   private UnifiedHandlerChainsMetaData handlerChains;

   // JBoss properties 

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
   // Arbitrary proxy properties given by <call-property> 
   private List<UnifiedCallPropertyMetaData> callProperties = new ArrayList<UnifiedCallPropertyMetaData>();
   // @Addressing annotation metadata
   private boolean addressingEnabled;
   private boolean addressingRequired;
   private String addressingResponses = "ALL";
   // @MTOM annotation metadata
   private boolean mtomEnabled;
   private int mtomThreshold;
   // @RespectBinding annotation metadata
   private boolean respectBindingEnabled;

   public UnifiedServiceRefMetaData(UnifiedVirtualFile vfRoot)
   {
      this.vfsRoot = vfRoot;
   }

   public UnifiedServiceRefMetaData()
   {
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
      if (mappingFile != null)
      {
         try
         {
            mappingURL = vfsRoot.findChild(mappingFile).toURL();
         }
         catch (Exception e)
         {
            throw new WebServiceException("Cannot find jaxrcp-mapping-file: " + mappingFile, e);
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
               log.warn("Multiple matching port component ref: [sei=" + seiName + ",port=" + portName + "]");

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
            try
            {
               wsdlLocation = vfsRoot.findChild(wsdlOverride).toURL();
            }
            catch (Exception e)
            {
               throw new WebServiceException("Cannot find wsdl-override: " + wsdlOverride, e);
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
            try
            {
               wsdlLocation = vfsRoot.findChild(wsdlFile).toURL();
            }
            catch (Exception e)
            {
               throw new WebServiceException("Cannot find wsdl-file: " + wsdlFile, e);
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

   private void writeObject(ObjectOutputStream out) throws IOException
   {
      out.defaultWriteObject();
      out.writeObject(vfsRoot);
      if (vfsRoot instanceof WritableUnifiedVirtualFile)
      {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ((WritableUnifiedVirtualFile)vfsRoot).writeContent(bos, new WritableUnifiedVirtualFile.NameFilter() {
            public boolean accept(String fileName)
            {
               boolean result = fileName.contains("META-INF");
               result = result || fileName.endsWith(".wsdl");
               result = result ||  fileName.endsWith(".xsd");
               result = result || fileName.endsWith(".xml");
               return result;
            }
         });
         out.writeObject(bos.toByteArray());
         out.writeObject(vfsRoot.getName());
      }
   }
   
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
   {
      in.defaultReadObject();
      UnifiedVirtualFile obj = (UnifiedVirtualFile)in.readObject();
      if (obj.toURL() == null && (obj instanceof WritableUnifiedVirtualFile))
      {
         //the virtual file has been created in a different VM (or is even pointing to a different filesystem), try getting the serialized contents
         byte[] bytes = (byte[])in.readObject();
         String vfName = (String)in.readObject();
         ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
         File tempFile = File.createTempFile("jbossws-vf-", "-" + vfName);
         tempFile.deleteOnExit();
         FileOutputStream fos = new FileOutputStream(tempFile);
         copyStreamAndClose(fos, bis);
         this.vfsRoot = new URLLoaderAdapter(tempFile.toURI().toURL());
      }
      else
      {
         this.vfsRoot = (UnifiedVirtualFile)obj;
      }
   }

   private static void copyStreamAndClose(OutputStream outs, InputStream ins) throws IOException
   {
      try
      {
         byte[] bytes = new byte[1024];
         int r = ins.read(bytes);
         while (r > 0)
         {
            outs.write(bytes, 0, r);
            r = ins.read(bytes);
         }
      }
      catch (IOException e)
      {
         throw e;
      }
      finally{
         try {
            ins.close();
         } catch (Exception e) {}
         try {
            outs.close();
         } catch (Exception e) {}
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
      str.append("\n callProperties=" + callProperties);
      str.append("\n addressingEnabled=" + addressingEnabled);
      str.append("\n addressingRequired=" + addressingRequired);
      str.append("\n addressingResponses=" + addressingResponses);
      str.append("\n mtomEnabled=" + mtomEnabled);
      str.append("\n mtomThreshold=" + mtomThreshold);
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
