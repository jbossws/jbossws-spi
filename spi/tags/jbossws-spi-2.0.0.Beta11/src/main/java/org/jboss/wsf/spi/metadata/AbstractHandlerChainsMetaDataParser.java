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
package org.jboss.wsf.spi.metadata;

import static org.jboss.wsf.spi.metadata.ParserConstants.CHAIN_PORT_PATTERN;
import static org.jboss.wsf.spi.metadata.ParserConstants.CHAIN_PROTOCOL_BINDING;
import static org.jboss.wsf.spi.metadata.ParserConstants.CHAIN_SERVICE_PATTERN;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_CHAIN;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_CLASS;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_PARAM;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_PARAM_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_PARAM_VALUE;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_SOAP_HEADER;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_SOAP_ROLE;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsQName;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.util.ResourceBundle;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.ws.api.util.BundleUtils;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedInitParamMetaData;

/**
 * Basic handler-chains parser
 * 
 * @author alessio.soldano@jboss.com
 * @since 30-Nov-2010
 */
public abstract class AbstractHandlerChainsMetaDataParser
{
   private static final ResourceBundle bundle = BundleUtils.getBundle(AbstractHandlerChainsMetaDataParser.class);
   protected UnifiedHandlerChainsMetaData parseHandlerChains(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      return this.parseHandlerChains(reader, nsUri, nsUri, HANDLER_CHAINS);
   }
   
   protected UnifiedHandlerChainsMetaData parseHandlerChains(XMLStreamReader reader, String nsUri,
         String handlerChainsElementNS, String handlerChainsElementName) throws XMLStreamException
   {
      UnifiedHandlerChainsMetaData handlerChains = new UnifiedHandlerChainsMetaData();
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, handlerChainsElementNS, handlerChainsElementName))
               {
                  return handlerChains;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, HANDLER_CHAIN)) {
                  handlerChains.addHandlerChain(parseHandlerChain(reader, nsUri, handlerChains));
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
   
   private UnifiedHandlerChainMetaData parseHandlerChain(XMLStreamReader reader, String nsUri, UnifiedHandlerChainsMetaData handlerChains) throws XMLStreamException
   {
      UnifiedHandlerChainMetaData handlerChain = new UnifiedHandlerChainMetaData(handlerChains);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, HANDLER_CHAIN))
               {
                  return handlerChain;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, CHAIN_PORT_PATTERN))
               {
                  handlerChain.setPortNamePattern(elementAsQName(reader));
               }
               else if (match(reader, nsUri, CHAIN_SERVICE_PATTERN))
               {
                  handlerChain.setServiceNamePattern(elementAsQName(reader));
               }
               else if(match(reader, nsUri, CHAIN_PROTOCOL_BINDING))
               {
                  handlerChain.setProtocolBindings(elementAsString(reader));
               }
               else if (match(reader, nsUri, HANDLER)) {
                  handlerChain.addHandler(parseHandler(reader, nsUri, handlerChain));
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
   
   protected UnifiedHandlerMetaData parseHandler(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      return parseHandler(reader, nsUri, null);
   }
   
   private UnifiedHandlerMetaData parseHandler(XMLStreamReader reader, String nsUri, UnifiedHandlerChainMetaData handlerChain) throws XMLStreamException
   {
      UnifiedHandlerMetaData handler = new UnifiedHandlerMetaData(handlerChain);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, HANDLER))
               {
                  return handler;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, HANDLER_NAME))
               {
                  handler.setHandlerName(elementAsString(reader));
               }
               else if (match(reader, nsUri, HANDLER_CLASS))
               {
                  handler.setHandlerClass(elementAsString(reader));
               }
               else if (match(reader, nsUri, HANDLER_PARAM)) {
                  handler.addInitParam(parseInitParam(reader, nsUri));
               }
               else if (match(reader, nsUri, HANDLER_SOAP_ROLE)) {
                  handler.addSoapRole(elementAsString(reader));
               }
               else if (match(reader, nsUri, HANDLER_SOAP_HEADER)) {
                  handler.addSoapHeader(elementAsQName(reader));
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

   private UnifiedInitParamMetaData parseInitParam(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      UnifiedInitParamMetaData initParam = new UnifiedInitParamMetaData();
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, HANDLER_PARAM))
               {
                  return initParam;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, HANDLER_PARAM_NAME))
               {
                  initParam.setParamName(elementAsString(reader));
               }
               else if (match(reader, nsUri, HANDLER_PARAM_VALUE))
               {
                  initParam.setParamValue(elementAsString(reader));
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
}
