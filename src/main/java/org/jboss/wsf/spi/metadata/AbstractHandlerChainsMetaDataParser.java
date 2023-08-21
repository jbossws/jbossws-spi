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
package org.jboss.wsf.spi.metadata;

import static org.jboss.wsf.spi.Messages.MESSAGES;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

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
   protected UnifiedHandlerChainsMetaData parseHandlerChains(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      return this.parseHandlerChains(reader, nsUri, nsUri, HANDLER_CHAINS);
   }
   
   protected UnifiedHandlerChainsMetaData parseHandlerChains(XMLStreamReader reader, String nsUri,
         String handlerChainsElementNS, String handlerChainsElementName) throws XMLStreamException
   {
      List<UnifiedHandlerChainMetaData> handlerChains = new ArrayList<UnifiedHandlerChainMetaData>(1);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, handlerChainsElementNS, handlerChainsElementName))
               {
                  return new UnifiedHandlerChainsMetaData(handlerChains);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, ParserConstants.JAVAEE_NS, HANDLER_CHAIN)) {
                  handlerChains.add(parseHandlerChain(reader, nsUri));
               } else if (match(reader, ParserConstants.JAKARTAEE_NS, HANDLER_CHAIN)) {
                  handlerChains.add(parseHandlerChain(reader, ParserConstants.JAKARTAEE_NS));
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
   
   private UnifiedHandlerChainMetaData parseHandlerChain(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      QName portNamePattern = null;
      QName serviceNamePattern = null;
      String protocolBindings = null;
      List<UnifiedHandlerMetaData> handlers = new ArrayList<UnifiedHandlerMetaData>(4);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, HANDLER_CHAIN))
               {
                  return new UnifiedHandlerChainMetaData(serviceNamePattern, portNamePattern, protocolBindings, handlers, false, null);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, CHAIN_PORT_PATTERN))
               {
                  portNamePattern = elementAsQName(reader);
               }
               else if (match(reader, nsUri, CHAIN_SERVICE_PATTERN))
               {
                  serviceNamePattern = elementAsQName(reader);
               }
               else if(match(reader, nsUri, CHAIN_PROTOCOL_BINDING))
               {
                  protocolBindings = elementAsString(reader);
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
   
   protected UnifiedHandlerMetaData parseHandler(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      String handlerName = null;
      String handlerClass = null;
      List<UnifiedInitParamMetaData> initParams = new LinkedList<UnifiedInitParamMetaData>();
      Set<QName> soapHeaders = new HashSet<QName>(2);
      Set<String> soapRoles = new HashSet<String>(2);
      Set<String> portNames = new HashSet<String>(4);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, HANDLER))
               {
                  return new UnifiedHandlerMetaData(handlerClass, handlerName, initParams, soapHeaders, soapRoles, portNames);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, HANDLER_NAME))
               {
                  handlerName = elementAsString(reader);
               }
               else if (match(reader, nsUri, HANDLER_CLASS))
               {
                  handlerClass = elementAsString(reader);
               }
               else if (match(reader, nsUri, HANDLER_PARAM)) {
                  initParams.add(parseInitParam(reader, nsUri));
               }
               else if (match(reader, nsUri, HANDLER_SOAP_ROLE)) {
                  soapRoles.add(elementAsString(reader));
               }
               else if (match(reader, nsUri, HANDLER_SOAP_HEADER)) {
                  soapHeaders.add(elementAsQName(reader));
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

   private UnifiedInitParamMetaData parseInitParam(XMLStreamReader reader, String nsUri) throws XMLStreamException
   {
      String paramName = null;
      String paramValue = null;
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, nsUri, HANDLER_PARAM))
               {
                  return new UnifiedInitParamMetaData(paramName, paramValue);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, nsUri, HANDLER_PARAM_NAME))
               {
                  paramName = elementAsString(reader);
               }
               else if (match(reader, nsUri, HANDLER_PARAM_VALUE))
               {
                  paramValue = elementAsString(reader);
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
   
   protected abstract String getDescriptorForLogs();
}
