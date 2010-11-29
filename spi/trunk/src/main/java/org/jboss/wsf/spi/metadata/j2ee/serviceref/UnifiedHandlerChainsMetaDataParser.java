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

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.jboss.wsf.spi.util.StAXUtils.createXMLStreamReader;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsQName;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/** The interface of the parser for the unified metadata handler chains element
 * 
 * @author alessio.soldano@jboss.com
 * @since 26-Nov-2010
 */
public class UnifiedHandlerChainsMetaDataParser
{
   private static final String JAVAEE_NS = "http://java.sun.com/xml/ns/javaee";
   private static final QName QNAME_CHAIN_PORT_PATTERN = new QName(JAVAEE_NS, "port-name-pattern");
   private static final QName QNAME_CHAIN_PROTOCOL_BINDING = new QName(JAVAEE_NS, "protocol-bindings");
   private static final QName QNAME_CHAIN_SERVICE_PATTERN = new QName(JAVAEE_NS, "service-name-pattern");
   private static final QName QNAME_HANDLER_CHAIN = new QName(JAVAEE_NS, "handler-chain");
   private static final QName QNAME_HANDLER_CHAINS = new QName(JAVAEE_NS, "handler-chains");
   private static final QName QNAME_HANDLER = new QName(JAVAEE_NS, "handler");
   private static final QName QNAME_HANDLER_NAME = new QName(JAVAEE_NS, "handler-name");
   private static final QName QNAME_HANDLER_CLASS = new QName(JAVAEE_NS, "handler-class");
   private static final QName QNAME_HANDLER_SOAP_ROLE = new QName(JAVAEE_NS, "soap-role");
   private static final QName QNAME_HANDLER_SOAP_HEADER = new QName(JAVAEE_NS, "soap-header");
   private static final QName QNAME_HANDLER_PARAM = new QName(JAVAEE_NS, "init-param");
   private static final QName QNAME_HANDLER_PARAM_NAME = new QName(JAVAEE_NS, "param-name");
   private static final QName QNAME_HANDLER_PARAM_VALUE = new QName(JAVAEE_NS, "param-value");

   public static UnifiedHandlerChainsMetaData parse(InputStream is) throws IOException
   {
      // http://java.sun.com/xml/ns/javaee/javaee_web_services_1_2.xsd
      try
      {
         XMLStreamReader xmlr = createXMLStreamReader(is);
         return parse(xmlr);
      }
      catch (XMLStreamException e)
      {
         throw new IOException(e);
      }
   }
   
   public static UnifiedHandlerChainsMetaData parse(XMLStreamReader reader) throws XMLStreamException
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
      UnifiedHandlerChainsMetaData handlerChains = null;
      switch (iterate)
      {
         case END_ELEMENT : {
            // we're done
            break;
         }
         case START_ELEMENT : {

            if (match(reader, QNAME_HANDLER_CHAINS))
            {
               handlerChains = parseHandlerChains(reader);
            }
            else
            {
               throw new IllegalStateException("Unexpected element: " + reader.getLocalName());
            }
         }
      }
      return handlerChains;
   }
   
   private static UnifiedHandlerChainsMetaData parseHandlerChains(XMLStreamReader reader) throws XMLStreamException
   {
      UnifiedHandlerChainsMetaData handlerChains = new UnifiedHandlerChainsMetaData();
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, QNAME_HANDLER_CHAINS))
               {
                  return handlerChains;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, QNAME_HANDLER_CHAIN)) {
                  handlerChains.addHandlerChain(parseHandlerChain(reader, handlerChains));
               }
               else
               {
                  throw new IllegalStateException("Unexpected element: " + reader.getLocalName());
               }
            }
         }
      }
      throw new IllegalStateException("Reached end of xml document unexpectedly");
   }
   
   private static UnifiedHandlerChainMetaData parseHandlerChain(XMLStreamReader reader, UnifiedHandlerChainsMetaData handlerChains) throws XMLStreamException
   {
      UnifiedHandlerChainMetaData handlerChain = new UnifiedHandlerChainMetaData(handlerChains);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, QNAME_HANDLER_CHAIN))
               {
                  return handlerChain;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, QNAME_CHAIN_PORT_PATTERN))
               {
                  handlerChain.setPortNamePattern(elementAsQName(reader));
               }
               else if (match(reader, QNAME_CHAIN_SERVICE_PATTERN))
               {
                  handlerChain.setServiceNamePattern(elementAsQName(reader));
               }
               else if(match(reader, QNAME_CHAIN_PROTOCOL_BINDING))
               {
                  handlerChain.setProtocolBindings(elementAsString(reader));
               }
               else if (match(reader, QNAME_HANDLER)) {
                  handlerChain.addHandler(parseHandler(reader, handlerChain));
               }
               else
               {
                  throw new IllegalStateException("Unexpected element: " + reader.getLocalName());
               }
            }
         }
      }
      throw new IllegalStateException("Reached end of xml document unexpectedly");
   }
   
   private static UnifiedHandlerMetaData parseHandler(XMLStreamReader reader, UnifiedHandlerChainMetaData handlerChain) throws XMLStreamException
   {
      UnifiedHandlerMetaData handler = new UnifiedHandlerMetaData(handlerChain);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, QNAME_HANDLER))
               {
                  return handler;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, QNAME_HANDLER_NAME))
               {
                  handler.setHandlerName(elementAsString(reader));
               }
               else if (match(reader, QNAME_HANDLER_CLASS))
               {
                  handler.setHandlerClass(elementAsString(reader));
               }
               else if (match(reader, QNAME_HANDLER_PARAM)) {
                  handler.addInitParam(parseInitParam(reader));
               }
               else if (match(reader, QNAME_HANDLER_SOAP_ROLE)) {
                  handler.addSoapRole(elementAsString(reader));
               }
               else if (match(reader, QNAME_HANDLER_SOAP_HEADER)) {
                  handler.addSoapHeader(elementAsQName(reader));
               }
               else
               {
                  throw new IllegalStateException("Unexpected element: " + reader.getLocalName());
               }
            }
         }
      }
      throw new IllegalStateException("Reached end of xml document unexpectedly");
   }

   private static UnifiedInitParamMetaData parseInitParam(XMLStreamReader reader) throws XMLStreamException
   {
      UnifiedInitParamMetaData initParam = new UnifiedInitParamMetaData();
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, QNAME_HANDLER_PARAM))
               {
                  return initParam;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, QNAME_HANDLER_PARAM_NAME))
               {
                  initParam.setParamName(elementAsString(reader));
               }
               else if (match(reader, QNAME_HANDLER_PARAM_VALUE))
               {
                  initParam.setParamValue(elementAsString(reader));
               }
               else
               {
                  throw new IllegalStateException("Unexpected element: " + reader.getLocalName());
               }
            }
         }
      }
      throw new IllegalStateException("Reached end of xml document unexpectedly");
   }
}
