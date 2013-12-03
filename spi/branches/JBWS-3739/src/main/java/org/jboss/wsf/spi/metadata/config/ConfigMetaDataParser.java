/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
import static org.jboss.wsf.spi.metadata.ParserConstants.JBOSSWS_JAXWS_CONFIG_NS_4_0;
import static org.jboss.wsf.spi.metadata.ParserConstants.POST_HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.PRE_HANDLER_CHAINS;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY_VALUE;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;
import static org.jboss.wsf.spi.Messages.MESSAGES;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.wsf.spi.Loggers;
import org.jboss.wsf.spi.metadata.AbstractHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
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

            if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, JAXWS_CONFIG))
            {
               ConfigMetaDataParser parser = new ConfigMetaDataParser();
               configRoot = parser.parseConfigRoot(reader);
            }
            else
            {
               throw MESSAGES.unexpectedElement(org.jboss.wsf.spi.metadata.ParserConstants.JBOSSWS_JAXWS_CONFIG_NS, reader.getLocalName());
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
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, JAXWS_CONFIG))
               {
                  return configRoot;
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, ENDPOINT_CONFIG)) {
                  configRoot.addEndpointConfig(parseEndpointConfig(reader));
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, CLIENT_CONFIG)) {
                  configRoot.addClientConfig(parseClientConfig(reader));
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
   
   private EndpointConfig parseEndpointConfig(final XMLStreamReader reader) throws XMLStreamException
   {
      return (EndpointConfig)parseConfig(reader, ENDPOINT_CONFIG);
   }
   
   private ClientConfig parseClientConfig(final XMLStreamReader reader) throws XMLStreamException
   {
      return (ClientConfig)parseConfig(reader, CLIENT_CONFIG);
   }
   
   private CommonConfig parseConfig(final XMLStreamReader reader, final String configElement) throws XMLStreamException
   {
      String configName = null;
      List<UnifiedHandlerChainMetaData> pre = null;
      List<UnifiedHandlerChainMetaData> post = null;
      List<Feature> features = new ArrayList<Feature>(1);
      List<Prop> properties = new ArrayList<Prop>(1);
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, configElement))
               {
                  Map<String, String> props = null;
                  if (!properties.isEmpty()) {
                     props = new HashMap<String, String>(properties.size(), 1);
                     for (Prop ps : properties)
                     {
                        props.put(ps.getName(), ps.getValue());
                     }
                  }
                  Map<String, Feature> featuresMap = null;
                  if (!features.isEmpty()) {
                     featuresMap = new HashMap<String, Feature>(features.size(), 1);
                     for (Feature f : features)
                     {
                        featuresMap.put(f.getName(), f);
                     }
                  }
                  return CLIENT_CONFIG.equals(configElement) ? new ClientConfig(configName, pre, post, props,
                        featuresMap) : new EndpointConfig(configName, pre, post, props, featuresMap);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, CONFIG_NAME)) {
                  configName = elementAsString(reader);
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, PRE_HANDLER_CHAINS)) {
                  UnifiedHandlerChainsMetaData uhcmd = parseHandlerChains(reader, JAVAEE_NS, JBOSSWS_JAXWS_CONFIG_NS_4_0, PRE_HANDLER_CHAINS);
                  pre = uhcmd.getHandlerChains();
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, POST_HANDLER_CHAINS)) {
                  UnifiedHandlerChainsMetaData uhcmd = parseHandlerChains(reader, JAVAEE_NS, JBOSSWS_JAXWS_CONFIG_NS_4_0, POST_HANDLER_CHAINS);
                  post = uhcmd.getHandlerChains();
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, FEATURE)) {
                  features.add(parseFeature(reader));
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, PROPERTY)) {
                  properties.add(parseProperty(reader));
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
   
   private Prop parseProperty(XMLStreamReader reader) throws XMLStreamException
   {
      String name = null;
      String value = null;
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, PROPERTY))
               {
                  if (name == null)
                  {
                     throw MESSAGES.couldNotGetPropertyName(getDescriptorForLogs());
                  }
                  return new Prop(name, value);
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, PROPERTY_NAME)) {
                  name = elementAsString(reader);
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, PROPERTY_VALUE)) {
                  value = elementAsString(reader);
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
   
   private class Prop {
      private final String name;
      private final String value;
      
      public Prop(String name, String value)
      {
         super();
         this.name = name;
         this.value = value;
      }
      
      public String getName()
      {
         return name;
      }

      public String getValue()
      {
         return value;
      }
   }
   
   private Feature parseFeature(XMLStreamReader reader) throws XMLStreamException
   {
      Feature feature = null;
      while (reader.hasNext())
      {
         switch (reader.nextTag())
         {
            case XMLStreamConstants.END_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, FEATURE))
               {
                  if (feature == null)
                  {
                     throw MESSAGES.couldNotGetFeatureName(getDescriptorForLogs());
                  }
                  return feature;
               }
               else
               {
                  throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
               }
            }
            case XMLStreamConstants.START_ELEMENT : {
               if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, FEATURE_NAME)) {
                  feature = new Feature(elementAsString(reader));
               }
               else if (match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, FEATURE_DATA)) {
                  //not implemented yet
                  Loggers.METADATA_LOGGER.elementNotSupported(new QName(JBOSSWS_JAXWS_CONFIG_NS_4_0, FEATURE_DATA).toString());
                  while (reader.hasNext()) {
                     reader.next();
                     if (reader.isEndElement() && match(reader, JBOSSWS_JAXWS_CONFIG_NS_4_0, FEATURE_DATA))
                     {
                        break;
                     }
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

   @Override
   protected String getDescriptorForLogs()
   {
      return org.jboss.wsf.spi.metadata.ParserConstants.JBOSSWS_JAXWS_CONFIG_NS;
   }
}
