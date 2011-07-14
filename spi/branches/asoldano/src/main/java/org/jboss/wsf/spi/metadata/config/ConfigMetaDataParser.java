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
import static org.jboss.wsf.spi.metadata.ParserConstants.CLIENT_CONFIG;
import static org.jboss.wsf.spi.metadata.ParserConstants.CONFIG_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.ENDPOINT_CONFIG;
import static org.jboss.wsf.spi.metadata.ParserConstants.FEATURE;
import static org.jboss.wsf.spi.metadata.ParserConstants.FEATURE_DATA;
import static org.jboss.wsf.spi.metadata.ParserConstants.FEATURE_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAVAEE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.JAXWS_CONFIG;
import static org.jboss.wsf.spi.metadata.ParserConstants.JBOSSWS_JAXWS_CONFIG_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.POST_HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.PRE_HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY_VALUE;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.logging.Logger;
import org.jboss.ws.api.util.BundleUtils;
import org.jboss.wsf.spi.metadata.AbstractHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.util.StAXUtils;

/**
 * The parser for the configuration metadata
 * 
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public class ConfigMetaDataParser extends AbstractHandlerChainsMetaDataParser
{
   private static final ResourceBundle bundle = BundleUtils.getBundle(ConfigMetaDataParser.class);
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
               throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
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
                     throw new IllegalStateException(BundleUtils.getMessage(bundle, "COULD_NOT_GET_PROPERTY_NAME"));
                  }
                  config.setProperty(name, value);
                  return;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
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
                     throw new IllegalStateException(BundleUtils.getMessage(bundle, "COULD_NOT_READ_FEATURE_NAME"));
                  }
                  return feature;
               }
               else
               {
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_END_TAG",  reader.getLocalName()));
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE_NAME)) {
                  feature = new Feature(elementAsString(reader));
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS, FEATURE_DATA)) {
                  //not implemented yet
                  Logger.getLogger(this.getClass()).warn(BundleUtils.getMessage(bundle, "FEATURE_DATA_NOT_SUPPORTED_YET"));
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
                  throw new IllegalStateException(BundleUtils.getMessage(bundle, "UNEXPECTED_ELEMENT",  reader.getLocalName()));
               }
            }
         }
      }
      throw new IllegalStateException(BundleUtils.getMessage(bundle, "REACHED_END_OF_XML_DOCUMENT_UNEXPECTEDLY"));
   }
}
