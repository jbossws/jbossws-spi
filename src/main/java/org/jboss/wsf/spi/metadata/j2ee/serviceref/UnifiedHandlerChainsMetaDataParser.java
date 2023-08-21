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
package org.jboss.wsf.spi.metadata.j2ee.serviceref;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.jboss.wsf.spi.metadata.ParserConstants.HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.J2EE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAKARTAEE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAVAEE_NS;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.wsf.spi.Messages;
import org.jboss.wsf.spi.metadata.AbstractHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.util.StAXUtils;

/**
 * The parser for the unified metadata handler chains element
 * 
 * @author alessio.soldano@jboss.com
 * @since 26-Nov-2010
 */
public class UnifiedHandlerChainsMetaDataParser extends AbstractHandlerChainsMetaDataParser
{
   private UnifiedHandlerChainsMetaDataParser()
   {
      super();
   }
   
   public static UnifiedHandlerChainsMetaData parse(InputStream is) throws IOException
   {
      // http://java.sun.com/xml/ns/javaee/javaee_web_services_1_2.xsd
      try
      {
         XMLStreamReader xmlr = StAXUtils.createXMLStreamReader(is);
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

            if (match(reader, JAKARTAEE_NS, HANDLER_CHAINS) || match(reader, JAVAEE_NS, HANDLER_CHAINS) || match(reader, J2EE_NS, HANDLER_CHAINS))
            {
               UnifiedHandlerChainsMetaDataParser parser = new UnifiedHandlerChainsMetaDataParser();
               handlerChains = parser.parseHandlerChains(reader, reader.getNamespaceURI());
            }
            else
            {
               throw Messages.MESSAGES.unexpectedElement("handlers", reader.getLocalName());
            }
         }
      }
      return handlerChains;
   }

   @Override
   protected String getDescriptorForLogs()
   {
      return "handlers";
   }
}
