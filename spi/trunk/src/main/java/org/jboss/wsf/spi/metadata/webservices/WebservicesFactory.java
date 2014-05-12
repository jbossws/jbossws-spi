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
package org.jboss.wsf.spi.metadata.webservices;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.jboss.wsf.spi.Messages.MESSAGES;
import static org.jboss.wsf.spi.metadata.ParserConstants.ADDRESSING;
import static org.jboss.wsf.spi.metadata.ParserConstants.ADDRESSING_RESPONSES;
import static org.jboss.wsf.spi.metadata.ParserConstants.EJB_LINK;
import static org.jboss.wsf.spi.metadata.ParserConstants.ENABLED;
import static org.jboss.wsf.spi.metadata.ParserConstants.ENABLE_MTOM;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.J2EE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAVAEE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JCP_JAVAEENS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAXRPC_MAPPING_FILE;
import static org.jboss.wsf.spi.metadata.ParserConstants.MTOM_THRESHOLD;
import static org.jboss.wsf.spi.metadata.ParserConstants.PORT_COMPONENT;
import static org.jboss.wsf.spi.metadata.ParserConstants.PORT_COMPONENT_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROTOCOL_BINDING;
import static org.jboss.wsf.spi.metadata.ParserConstants.REQUIRED;
import static org.jboss.wsf.spi.metadata.ParserConstants.RESPECT_BINDING;
import static org.jboss.wsf.spi.metadata.ParserConstants.SERVICE_ENDPOINT_INTERFACE;
import static org.jboss.wsf.spi.metadata.ParserConstants.SERVICE_IMPL_BEAN;
import static org.jboss.wsf.spi.metadata.ParserConstants.SERVLET_LINK;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICES;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICE_DESCRIPTION;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICE_DESCRIPTION_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.WSDL_FILE;
import static org.jboss.wsf.spi.metadata.ParserConstants.WSDL_PORT;
import static org.jboss.wsf.spi.metadata.ParserConstants.WSDL_SERVICE;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsBoolean;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsInt;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsQName;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.ws.WebServiceException;

import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.metadata.AbstractHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;
import org.jboss.wsf.spi.util.StAXUtils;

/**
 * A JBossXB factory for {@link WebservicesMetaData}
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 16-Apr-2004
 */
public class WebservicesFactory extends AbstractHandlerChainsMetaDataParser
{
   // The URL to the webservices.xml descriptor
   private final URL descriptorURL;
   
   public WebservicesFactory(URL descriptorURL)
   {
      this.descriptorURL = descriptorURL;
   }

   /**
    * Load webservices.xml from <code>META-INF/webservices.xml</code>
    * or <code>WEB-INF/webservices.xml</code>.
    *
    * @param root virtual file root
    * @return WebservicesMetaData or <code>null</code> if it cannot be found
    */
   public WebservicesMetaData loadFromVFSRoot(UnifiedVirtualFile root)
   {
      WebservicesMetaData webservices = null;

      UnifiedVirtualFile wsdd = root.findChildFailSafe("META-INF/webservices.xml");

      // Maybe a web application deployment?
      if (null == wsdd)
      {
         wsdd = root.findChildFailSafe("WEB-INF/webservices.xml");
      }

      // the descriptor is optional
      if (wsdd != null)
      {
         return load(wsdd.toURL());
      }

      return webservices;
   }
   
   public WebservicesMetaData load(URL wsddUrl)
   {
      InputStream is = null;
      try
      {
         is = wsddUrl.openStream();
         XMLStreamReader xmlr = StAXUtils.createXMLStreamReader(is);
         return parse(xmlr, wsddUrl);
      }
      catch (Exception e)
      {
         throw MESSAGES.failedToUnmarshall(e, wsddUrl);
      }
      finally
      {
         try
         {
            if (is != null) is.close();
         }
         catch (IOException e) {} //ignore
      }
   }
   
   public WebservicesMetaData parse(InputStream is)
   {
      return parse(is, null);
   }
   
   public WebservicesMetaData parse(InputStream is, URL descriptorURL)
   {
      try
      {
         XMLStreamReader xmlr = StAXUtils.createXMLStreamReader(is);
         return parse(xmlr, descriptorURL);
      }
      catch (Exception e)
      {
         throw new WebServiceException(e);
      }
   }
   
   public  WebservicesMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      return parse(reader, null);
   }
   
   private WebservicesMetaData parse(XMLStreamReader reader, URL descriptorURL) throws XMLStreamException
   {
      int iterate;
      try
      {
         iterate = reader.nextTag();
      }
      catch (XMLStreamException e)
      {
         // skip non-tag elements
         iterate = reader.nextTag();
      }
      WebservicesMetaData metadata = null;
      switch (iterate)
      {
         case END_ELEMENT : {
            // we're done
            break;
         }
         case START_ELEMENT : {

            if (match(reader, JAVAEE_NS, WEBSERVICES) || match(reader, J2EE_NS, WEBSERVICES) || match(reader, JCP_JAVAEENS, WEBSERVICES))
            {
               String nsUri = reader.getNamespaceURI();
               metadata = parseWebservices(reader, nsUri, descriptorURL);
            }
            else
            {
               throw MESSAGES.unexpectedElement(descriptorURL != null ? descriptorURL.toString() : "webservices.xml", reader.getLocalName());
            }
         }
      }
      return metadata;
   }
   
   private WebservicesMetaData parseWebservices(XMLStreamReader reader, String nsUri, URL descriptorURL) throws XMLStreamException
   {
      List<WebserviceDescriptionMetaData> wsdmds = new ArrayList<WebserviceDescriptionMetaData>(2);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICES))
               {
                  return new WebservicesMetaData(descriptorURL, wsdmds);
               } 
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                  wsdmds.add(parseWebserviceDescription(reader, nsUri));                 
               } else if (match(reader, nsUri, "description") || match(reader, nsUri, "display-name")) {
            	   //skip to parse
                  getElementText(reader);
               } else if (match(reader, nsUri, "icon")) {
            	   //skip icon
            	   while (reader.hasNext() && !(reader.nextTag() == XMLStreamConstants.END_ELEMENT && match(reader, nsUri, "icon"))) {
            		   reader.next();
            	   }
               }
               
               else
               {
                  throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
               }
            }
         }
      }
      throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
   }
   
   WebserviceDescriptionMetaData parseWebserviceDescription(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      String wsdlFile = null;
      String descriptionName = null;
      String jaxrpcMappingFile = null;
      List<PortComponentMetaData> pcms = new ArrayList<PortComponentMetaData>();
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICE_DESCRIPTION))
               {
                  return new WebserviceDescriptionMetaData(descriptionName, wsdlFile, jaxrpcMappingFile, pcms);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICE_DESCRIPTION_NAME)) {
                  descriptionName = getElementText(reader);
               }
               else if (match(reader, nsUri, WSDL_FILE)) {
                  wsdlFile = getElementText(reader);
               }
               else if (match(reader, nsUri, JAXRPC_MAPPING_FILE)) {
                   jaxrpcMappingFile = getElementText(reader);
               }
               else if (match(reader, nsUri, PORT_COMPONENT)) {
                  pcms.add(parsePortComponent(reader, nsUri));
               }
               else
               {
                  throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
               }
            }
         }
      }
      throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
   }
   
   private PortComponentMetaData parsePortComponent(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      boolean respectBindingEnabled = false;
      PortComponentAddressing addressing = new PortComponentAddressing();
      PortComponentLinks links = new PortComponentLinks();
      String name = null;
      QName wsdlService = null;
      QName wsdlPort = null;
      boolean mtomEnabled = false;
      int mtomThreshold = 0;
      String protocolBinding = null;
      String serviceEndpointInterface = null;
      UnifiedHandlerChainsMetaData uhcs = null;
      List<UnifiedHandlerMetaData> handlers = new ArrayList<UnifiedHandlerMetaData>(2);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, PORT_COMPONENT))
               {
                  return new PortComponentMetaData(name, wsdlPort, serviceEndpointInterface, links.ejb, links.servlet, handlers, null, addressing.enabled,
                        addressing.required, addressing.responses, mtomEnabled, mtomThreshold, respectBindingEnabled, wsdlService, protocolBinding, uhcs);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {           	
               if (match(reader, nsUri, PORT_COMPONENT_NAME)) {
                  name = getElementText(reader);
               }
               else if (match(reader, nsUri, "description") || match(reader, nsUri, "display-name")) {
            	   //skip to parse
            	   getElementText(reader);
               } else if (match(reader, nsUri, "icon")) {
            	   //skip icon
            	   while (reader.hasNext() && !(reader.nextTag() == XMLStreamConstants.END_ELEMENT && match(reader, nsUri, "icon"))) {
            		   reader.next();
            	   }
               }
               else if (match(reader, nsUri, WSDL_SERVICE)) {
                  wsdlService = elementAsQName(reader);
               }
               else if (match(reader, nsUri, WSDL_PORT)) {
                  wsdlPort = elementAsQName(reader);
               }
               else if (match(reader, nsUri, ENABLE_MTOM)) {
                  mtomEnabled = elementAsBoolean(reader);
               }
               else if (match(reader, nsUri, MTOM_THRESHOLD)) {
                  mtomThreshold = elementAsInt(reader);
               }
               else if (match(reader, nsUri, ADDRESSING)) {
                  parseAddressing(reader, nsUri, addressing);
               }
               else if (match(reader, nsUri, RESPECT_BINDING)) {
                  respectBindingEnabled = parseRespectBinding(reader, nsUri);
               }
               else if (match(reader, nsUri, PROTOCOL_BINDING)) {
                  protocolBinding = getElementText(reader);
               }
               else if (match(reader, nsUri, SERVICE_ENDPOINT_INTERFACE)) {
                  serviceEndpointInterface = getElementText(reader);
               }
               else if (match(reader, nsUri, SERVICE_IMPL_BEAN)) {
                  parseServiceImplBean(reader, nsUri, links);
               }
               else if (match(reader, nsUri, HANDLER_CHAINS)) {
                  uhcs = parseHandlerChains(reader, nsUri);
               }
               else if (match(reader, nsUri, HANDLER)) {
                  handlers.add(parseHandler(reader, nsUri));
               }
               else
               {
                  throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
               }
            }
         }
      }
      throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
   }
   
   protected String getElementText(XMLStreamReader reader) throws XMLStreamException {
      return elementAsString(reader);
   }
   
   private void parseAddressing(XMLStreamReader reader, String nsUri, PortComponentAddressing pca) throws XMLStreamException
   {
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, ADDRESSING))
               {
                  return;
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, ENABLED)) {
                  pca.enabled = elementAsBoolean(reader);
               }
               else if (match(reader, nsUri, REQUIRED)) {
                  pca.required = elementAsBoolean(reader);
               }
               else if (match(reader, nsUri, ADDRESSING_RESPONSES)) {
                  pca.responses = getElementText(reader);
               }
               else
               {
                  throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
               }
            }
         }
      }
      throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
   }
   
   private class PortComponentAddressing {
      public boolean enabled = false;
      public boolean required = false;
      public String responses = "ALL";
   }
   
   private boolean parseRespectBinding(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      boolean respectBindingEnabled = false;
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, RESPECT_BINDING))
               {
                  return respectBindingEnabled;
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, ENABLED)) {
                  respectBindingEnabled = elementAsBoolean(reader);
               }
               else
               {
                  throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
               }
            }
         }
      }
      throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
   }
   
   private void parseServiceImplBean(XMLStreamReader reader, String nsUri, PortComponentLinks pcl) throws XMLStreamException
   {
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, SERVICE_IMPL_BEAN))
               {
                  return;
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, SERVLET_LINK)) {
                  pcl.servlet = getElementText(reader);
               }
               else if (match(reader, nsUri, EJB_LINK)) {
                  pcl.ejb = getElementText(reader);
               }
               else
               {
                  throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
               }
            }
         }
      }
      throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
   }
   
   private class PortComponentLinks {
      public String servlet;
      public String ejb;
   }
   
   @Override
   protected String getDescriptorForLogs() {
      return descriptorURL != null ? descriptorURL.toString() : "webservices.xml";
   }
   
   public URL getDescriptorURL()
   {
      return descriptorURL;
   }

}
