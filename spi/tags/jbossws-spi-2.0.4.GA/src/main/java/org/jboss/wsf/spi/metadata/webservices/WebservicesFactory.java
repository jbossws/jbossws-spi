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
package org.jboss.wsf.spi.metadata.webservices;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.jboss.wsf.spi.metadata.ParserConstants.ADDRESSING;
import static org.jboss.wsf.spi.metadata.ParserConstants.ADDRESSING_RESPONSES;
import static org.jboss.wsf.spi.metadata.ParserConstants.EJB_LINK;
import static org.jboss.wsf.spi.metadata.ParserConstants.ENABLED;
import static org.jboss.wsf.spi.metadata.ParserConstants.ENABLE_MTOM;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.J2EE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAVAEE_NS;
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
import java.util.ResourceBundle;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.ws.WebServiceException;

import org.jboss.ws.api.util.BundleUtils;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.metadata.AbstractHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.util.StAXUtils;

/**
 * A JBossXB factory for {@link WebservicesMetaData}
 *
 * @author Thomas.Diesler@jboss.org
 * @since 16-Apr-2004
 */
public class WebservicesFactory extends AbstractHandlerChainsMetaDataParser
{
   private static final ResourceBundle bundle = BundleUtils.getBundle(WebservicesFactory.class);
   // The URL to the webservices.xml descriptor
   private URL descriptorURL;
   
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
   public static WebservicesMetaData loadFromVFSRoot(UnifiedVirtualFile root)
   {
      WebservicesMetaData webservices = null;

      UnifiedVirtualFile wsdd = null;
      try
      {
         wsdd = root.findChild("META-INF/webservices.xml");
      }
      catch (IOException e)
      {
         //
      }

      // Maybe a web application deployment?
      if (null == wsdd)
      {
         try
         {
            wsdd = root.findChild("WEB-INF/webservices.xml");
         }
         catch (IOException e)
         {
            //
         }
      }

      // the descriptor is optional
      if (wsdd != null)
      {
         return load(wsdd.toURL());
      }

      return webservices;
   }
   
   public static WebservicesMetaData load(URL wsddUrl)
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
         throw new WebServiceException(BundleUtils.getMessage(bundle, "FAILED_TO_UNMARSHALL", wsddUrl),  e);
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
   
   public static WebservicesMetaData parse(InputStream is)
   {
      return parse(is, null);
   }
   
   public static WebservicesMetaData parse(InputStream is, URL descriptorURL)
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
   
   public static WebservicesMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      return parse(reader, null);
   }
   
   private static WebservicesMetaData parse(XMLStreamReader reader, URL descriptorURL) throws XMLStreamException
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

            if (match(reader, JAVAEE_NS, WEBSERVICES) || match(reader, J2EE_NS, WEBSERVICES))
            {
               String nsUri = reader.getNamespaceURI();
               WebservicesFactory factory = new WebservicesFactory(descriptorURL);
               metadata = factory.parseWebservices(reader, nsUri, descriptorURL);
            }
            else
            {
               throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
            }
         }
      }
      return metadata;
   }
   
   private WebservicesMetaData parseWebservices(XMLStreamReader reader, String nsUri, URL descriptorURL) throws XMLStreamException
   {
      WebservicesMetaData metadata = new WebservicesMetaData(descriptorURL);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICES))
               {
                  return metadata;
               } 
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                  metadata.addWebserviceDescription(parseWebserviceDescription(reader, nsUri, metadata));                 
               } else if (match(reader, nsUri, "description") || match(reader, nsUri, "display-name")) {
            	   //skip to parse
            	   elementAsString(reader);
               } else if (match(reader, nsUri, "icon")) {
            	   //skip icon
            	   while (reader.hasNext() && !(reader.nextTag() == XMLStreamConstants.END_ELEMENT && match(reader, nsUri, "icon"))) {
            		   reader.next();
            	   }
               }
               
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
   
   WebserviceDescriptionMetaData parseWebserviceDescription(XMLStreamReader reader, String nsUri, WebservicesMetaData wsMetaData) throws XMLStreamException
   {
      WebserviceDescriptionMetaData description = new WebserviceDescriptionMetaData(wsMetaData);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICE_DESCRIPTION))
               {
                  return description;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, WEBSERVICE_DESCRIPTION_NAME)) {
                  description.setWebserviceDescriptionName(elementAsString(reader));
               }
               else if (match(reader, nsUri, WSDL_FILE)) {
                  description.setWsdlFile(elementAsString(reader));
               }
               else if (match(reader, nsUri, JAXRPC_MAPPING_FILE)) {
                   description.setJaxrpcMappingFile(elementAsString(reader));
               }
               else if (match(reader, nsUri, PORT_COMPONENT)) {
                  description.addPortComponent(parsePortComponent(reader, nsUri, description));
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
   
   private PortComponentMetaData parsePortComponent(XMLStreamReader reader, String nsUri, WebserviceDescriptionMetaData desc) throws XMLStreamException
   {
      PortComponentMetaData pc = new PortComponentMetaData(desc);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, PORT_COMPONENT))
               {
                  return pc;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {           	
               if (match(reader, nsUri, PORT_COMPONENT_NAME)) {
                  pc.setPortComponentName(elementAsString(reader));
               }
               else if (match(reader, nsUri, "description") || match(reader, nsUri, "display-name")) {
            	   //skip to parse
            	   elementAsString(reader);
               } else if (match(reader, nsUri, "icon")) {
            	   //skip icon
            	   while (reader.hasNext() && !(reader.nextTag() == XMLStreamConstants.END_ELEMENT && match(reader, nsUri, "icon"))) {
            		   reader.next();
            	   }
               }
               else if (match(reader, nsUri, WSDL_SERVICE)) {
                  pc.setWsdlService(elementAsQName(reader));
               }
               else if (match(reader, nsUri, WSDL_PORT)) {
                  pc.setWsdlPort(elementAsQName(reader));
               }
               else if (match(reader, nsUri, ENABLE_MTOM)) {
                  pc.setMtomEnabled(elementAsBoolean(reader));
               }
               else if (match(reader, nsUri, MTOM_THRESHOLD)) {
                  pc.setMtomThreshold(elementAsInt(reader));
               }
               else if (match(reader, nsUri, ADDRESSING)) {
                  parseAddressing(reader, nsUri, pc);
               }
               else if (match(reader, nsUri, RESPECT_BINDING)) {
                  parseRespectBinding(reader, nsUri, pc);
               }
               else if (match(reader, nsUri, PROTOCOL_BINDING)) {
                  pc.setProtocolBinding(elementAsString(reader));
               }
               else if (match(reader, nsUri, SERVICE_ENDPOINT_INTERFACE)) {
                  pc.setServiceEndpointInterface(elementAsString(reader));
               }
               else if (match(reader, nsUri, SERVICE_IMPL_BEAN)) {
                  parseServiceImplBean(reader, nsUri, pc);
               }
               else if (match(reader, nsUri, HANDLER_CHAINS)) {
                  pc.setHandlerChains(parseHandlerChains(reader, nsUri));
               }
               else if (match(reader, nsUri, HANDLER)) {
                  pc.addHandler(parseHandler(reader, nsUri));
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
   
   private void parseAddressing(XMLStreamReader reader, String nsUri, PortComponentMetaData pc) throws XMLStreamException
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, ENABLED)) {
                  pc.setAddressingEnabled(elementAsBoolean(reader));
               }
               else if (match(reader, nsUri, REQUIRED)) {
                  pc.setAddressingRequired(elementAsBoolean(reader));
               }
               else if (match(reader, nsUri, ADDRESSING_RESPONSES)) {
                  pc.setAddressingResponses(elementAsString(reader));
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
   
   private void parseRespectBinding(XMLStreamReader reader, String nsUri, PortComponentMetaData pc) throws XMLStreamException
   {
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, RESPECT_BINDING))
               {
                  return;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, ENABLED)) {
                  pc.setRespectBindingEnabled(elementAsBoolean(reader));
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
   
   private void parseServiceImplBean(XMLStreamReader reader, String nsUri, PortComponentMetaData pc) throws XMLStreamException
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, SERVLET_LINK)) {
                  pc.setServletLink(elementAsString(reader));
               }
               else if (match(reader, nsUri, EJB_LINK)) {
                  pc.setEjbLink(elementAsString(reader));
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
   
   public URL getDescriptorURL()
   {
      return descriptorURL;
   }

}
