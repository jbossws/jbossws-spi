/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata.config;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.jboss.wsf.spi.metadata.ParserConstants.*;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.metadata.AbstractHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.util.StAXUtils;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * The parser for the configuration metadata
 * 
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public class ConfigMetaDataParser extends AbstractHandlerChainsMetaDataParser
{
   private ConfigMetaDataParser()
   {
      super();
   }
   
   public static ConfigRoot parse(URL url) throws IOException
   {
      InputStream is = null;
      try
      {
         is = url.openStream();
         return parse(is);
      }
      finally
      {
         if (is != null) {
            try {
               is.close();
            } catch (Exception e) {} //ignore
         }
      }
   }
   
   public static ConfigRoot parse(InputStream is) throws IOException
   {
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
   
   public static ConfigRoot parse(XMLStreamReader reader) throws XMLStreamException
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
      ConfigRoot configRoot = null;
      switch (iterate)
      {
         case END_ELEMENT : {
            // we're done
            break;
         }
         case START_ELEMENT : {

            if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, JAXWS_CONFIG))
            {
               ConfigMetaDataParser parser = new ConfigMetaDataParser();
               configRoot = parser.parseConfigRoot(reader);
            }
            else
            {
               throw new IllegalStateException("Unexpected element: " + reader.getLocalName());
            }
         }
      }
      return configRoot;
   }
   
   private ConfigRoot parseConfigRoot(XMLStreamReader reader) throws XMLStreamException
   {
      ConfigRoot configRoot = new ConfigRoot();
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, JAXWS_CONFIG))
               {
                  return configRoot;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, ENDPOINT_CONFIG)) {
                  EndpointConfig epConfig = new EndpointConfig();
                  parseConfig(reader, epConfig, ENDPOINT_CONFIG);
                  configRoot.addEndpointConfig(epConfig);
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, CLIENT_CONFIG)) {
                  ClientConfig clConfig = new ClientConfig();
                  parseConfig(reader, clConfig, CLIENT_CONFIG);
                  configRoot.addClientConfig(clConfig);
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
   
   private void parseConfig(XMLStreamReader reader, CommonConfig config, String configElement) throws XMLStreamException
   {
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, configElement))
               {
                  return;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, CONFIG_NAME)) {
                  config.setConfigName(elementAsString(reader));
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, PRE_HANDLER_CHAINS)) {
                  UnifiedHandlerChainsMetaData uhcmd = parseHandlerChains(reader, JAVAEE_NS, JBOSSWS_JAXWS_CONFIG_NS, PRE_HANDLER_CHAINS);
                  config.setPreHandlerChains(uhcmd.getHandlerChains());
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, POST_HANDLER_CHAINS)) {
                  UnifiedHandlerChainsMetaData uhcmd = parseHandlerChains(reader, JAVAEE_NS, JBOSSWS_JAXWS_CONFIG_NS, POST_HANDLER_CHAINS);
                  config.setPostHandlerChains(uhcmd.getHandlerChains());
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE)) {
                  config.setFeature(parseFeature(reader), true);
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, PROPERTY)) {
                  parseProperty(reader, config);
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
   
   private void parseProperty(XMLStreamReader reader, CommonConfig config) throws XMLStreamException
   {
      String name = null;
      String value = null;
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, PROPERTY))
               {
                  if (name == null)
                  {
                     throw new IllegalStateException("Could not get property name!");
                  }
                  config.setProperty(name, value);
                  return;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, PROPERTY_NAME)) {
                  name = elementAsString(reader);
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, PROPERTY_VALUE)) {
                  value = elementAsString(reader);
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
   
   private Feature parseFeature(XMLStreamReader reader) throws XMLStreamException
   {
      Feature feature = null;
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE))
               {
                  if (feature == null)
                  {
                     throw new IllegalStateException("Could not read feature name!");
                  }
                  return feature;
               }
               else
               {
                  throw new IllegalStateException("Unexpected end tag: " + reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE_NAME)) {
                  feature = new Feature(elementAsString(reader));
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE_DATA)) {
                  //not implemented yet
                  Logger.getLogger(this.getClass()).warn("Feature data not supported yet!");
                  while (reader.hasNext()) {
                     reader.next();
                     if (reader.isEndElement() && match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE_DATA))
                     {
                        break;
                     }
                  }
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
