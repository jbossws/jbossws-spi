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

import static org.jboss.wsf.spi.metadata.ParserConstants.*;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsQName;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedInitParamMetaData;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Basic handler-chains parser
 * 
 * @author alessio.soldano@jboss.com
 * @since 30-Nov-2010
 */
public abstract class AbstractHandlerChainsMetaDataParser
{
   protected UnifiedHandlerChainsMetaData parseHandlerChains(XMLStreamReader reader) throws XMLStreamException
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
   
   private UnifiedHandlerChainMetaData parseHandlerChain(XMLStreamReader reader, UnifiedHandlerChainsMetaData handlerChains) throws XMLStreamException
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
   
   private UnifiedHandlerMetaData parseHandler(XMLStreamReader reader, UnifiedHandlerChainMetaData handlerChain) throws XMLStreamException
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

   private UnifiedInitParamMetaData parseInitParam(XMLStreamReader reader) throws XMLStreamException
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
