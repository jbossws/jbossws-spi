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
package org.jboss.wsf.spi.metadata.webservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jboss.wsf.spi.Loggers;

/**
 * XML Binding element for <code>webservices/webservice-description</code>
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 15-April-2004
 */
public class WebserviceDescriptionMetaData
{
   // The parent <webservices> element
   private volatile WebservicesMetaData webservices;

   // The required <webservice-description-name> element
   private final String webserviceDescriptionName;
   // The required <wsdl-file> element
   private final String wsdlFile;
   // The required <jaxrpc-mapping-file> element
   private final String jaxrpcMappingFile;
   // The required <port-component> elements
   private final List<PortComponentMetaData> portComponents;
   
   public WebserviceDescriptionMetaData(String webserviceDescriptionName,
         String wsdlFile, String jaxrpcMappingFile, PortComponentMetaData... portComponents)
   {
      this(webserviceDescriptionName, wsdlFile, jaxrpcMappingFile, portComponents != null ? Arrays.asList(portComponents) : null);
   }
   
   public WebserviceDescriptionMetaData(String webserviceDescriptionName,
         String wsdlFile, String jaxrpcMappingFile, List<PortComponentMetaData> portComponents)
   {
      this.webserviceDescriptionName = webserviceDescriptionName;
      this.wsdlFile = wsdlFile;
      this.jaxrpcMappingFile = jaxrpcMappingFile;
      if (portComponents != null && !portComponents.isEmpty()) {
         this.portComponents = Collections.unmodifiableList(portComponents);
         for (PortComponentMetaData pcm : portComponents) {
            pcm.setWebserviceDescription(this);
         }
      } else {
         this.portComponents = Collections.emptyList();
      }
   }

   public WebservicesMetaData getWebservices()
   {
      return webservices;
   }
   
   protected void setWebservices(WebservicesMetaData webservices)
   {
      this.webservices = webservices;
   }

   public PortComponentMetaData[] getPortComponents()
   {
      PortComponentMetaData[] array = new PortComponentMetaData[portComponents.size()];
      portComponents.toArray(array);
      return array;
   }

   /**
    * Get the QNames of the port components to be declared
    * in the namespaces
    * 
    * @return  collection of QNames
    */
   public Collection<QName> getPortComponentQNames()
   {
      //TODO:Check if there is just one QName that drives all portcomponents
      //or each port component can have a distinct QName (namespace/prefix)
      //Maintain uniqueness of the QName
      Map<String, QName> map = new HashMap<String, QName>();
      for (PortComponentMetaData pcm : portComponents)
      {
         QName qname = pcm.getWsdlPort();
         map.put(qname.getPrefix(), qname);
      }
      return map.values();
   }

   /**
    * Lookup a PortComponentMetaData by wsdl-port local part
    *
    * @param name - the wsdl-port local part
    * @return PortComponentMetaData if found, null otherwise
    */
   public PortComponentMetaData getPortComponentByWsdlPort(String name)
   {
      ArrayList<String> pcNames = new ArrayList<String>();
      for (PortComponentMetaData pc : portComponents)
      {
         String wsdlPortName = pc.getWsdlPort().getLocalPart();
         if (wsdlPortName.equals(name))
            return pc;

         pcNames.add(wsdlPortName);
      }

      Loggers.METADATA_LOGGER.cannotGetPortComponentName(name, pcNames);
      return null;
   }

   public PortComponentMetaData getPortComponentByEjbLinkName(String ejbName)
   {
      for (PortComponentMetaData pc : portComponents)
      {
         if (ejbName.equals(pc.getEjbLink())) return pc;
      }

      return null;
   }

   public String getWebserviceDescriptionName()
   {
      return webserviceDescriptionName;
   }

   public String getWsdlFile()
   {
      return wsdlFile;
   }

   public String getJaxrpcMappingFile()
   {
      return jaxrpcMappingFile;
   }

   /**
    * Serialize as a String
    * 
    * @return  string
    */
   public String serialize()
   {
      StringBuilder buffer = new StringBuilder("<webservice-description>");
      buffer.append("<webservice-description-name>").append(webserviceDescriptionName).append("</webservice-description-name>");
      buffer.append("<wsdl-file>").append(wsdlFile).append("</wsdl-file>");
      buffer.append("<jaxrpc-mapping-file>").append(jaxrpcMappingFile).append("</jaxrpc-mapping-file>");
      for (PortComponentMetaData pm : portComponents)
         buffer.append(pm.serialize());
      buffer.append("</webservice-description>");
      return buffer.toString();
   }
}
