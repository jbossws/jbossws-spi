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
package org.jboss.wsf.spi.util;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.wsf.spi.Messages;

/**
 * StAX utils
 * 
 * @author alessio.soldano@jboss.com
 * @since 27-Nov-2010
 */
public class StAXUtils
{
   private static final BlockingQueue<XMLInputFactory> INPUT_FACTORY_POOL;

   static
   {
      int i = 10;
      try
      {
         String s = System.getProperty("org.jboss.ws.staxutils.pool-size", "10");
         i = Integer.parseInt(s);
      }
      catch (Throwable t)
      {
         i = 10;
      }
      if (i <= 0)
      {
         i = 10;
      }
      INPUT_FACTORY_POOL = new LinkedBlockingQueue<XMLInputFactory>(i);
   }

   /**
    * Return a new factory so that the caller can set sticky parameters.
    * @param nsAware  true is nsAware
    * @return  XMLInputFactory
    */
   public static XMLInputFactory createXMLInputFactory(boolean nsAware)
   {
      XMLInputFactory factory = XMLInputFactory.newInstance();
      factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, nsAware);
      factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
      factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
      factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
      factory.setXMLResolver(new XMLResolver()
      {
         public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace)
               throws XMLStreamException
         {
            throw Messages.MESSAGES.readingExternalEntitiesDisabled();
         }
      });
      return factory;
   }

   private static XMLInputFactory getXMLInputFactory()
   {
      XMLInputFactory f = INPUT_FACTORY_POOL.poll();
      if (f == null)
      {
         f = createXMLInputFactory(true);
      }
      return f;
   }

   private static void returnXMLInputFactory(XMLInputFactory factory)
   {
      INPUT_FACTORY_POOL.offer(factory);
   }

   public static XMLStreamReader createXMLStreamReader(InputStream in)
   {
      XMLInputFactory factory = getXMLInputFactory();
      try
      {
         return factory.createXMLStreamReader(in);
      }
      catch (XMLStreamException e)
      {
         throw Messages.MESSAGES.couldNotParseStream(e);
      }
      finally
      {
         returnXMLInputFactory(factory);
      }
   }

   public static boolean match(XMLStreamReader reader, QName name)
   {
      return reader.getName().equals(name);
   }
   
   public static boolean match(XMLStreamReader reader, String namespace, String localName)
   {
      QName name = reader.getName();
      return localName.equals(name.getLocalPart()) && namespace.equals(name.getNamespaceURI());
   }

   public static boolean match(XMLStreamReader reader, String localName)
   {
      QName name = reader.getName();
      return localName.equals(name.getLocalPart());
   }

   public static String elementAsString(XMLStreamReader reader) throws XMLStreamException
   {
      String elementtext = reader.getElementText();
      return elementtext == null ? null : elementtext.trim();
   }

   public static QName elementAsQName(XMLStreamReader reader) throws XMLStreamException
   {
      String text = elementAsString(reader);
      return stringToQName(reader, text, reader.getNamespaceURI());
   }
   
   public static boolean elementAsBoolean(XMLStreamReader reader) throws XMLStreamException
   {
      String text = elementAsString(reader);
      return Boolean.parseBoolean(text);
   }
   
   public static int elementAsInt(XMLStreamReader reader) throws XMLStreamException
   {
      String text = elementAsString(reader);
      return Integer.parseInt(text);
   }
   
   public static QName attributeAsQName(XMLStreamReader reader, String namespace, String localName) throws XMLStreamException
   {
      String text = reader.getAttributeValue(namespace, localName);
      return stringToQName(reader, text, reader.getNamespaceURI());
   }
   
   public static QName attributeAsQName(XMLStreamReader reader, String namespace, String localName, String targetNS) throws XMLStreamException
   {
      String text = reader.getAttributeValue(namespace, localName);
      return stringToQName(reader, text, targetNS);
   }
   
   private static QName stringToQName(XMLStreamReader reader, String text, String defaultNS)
   {
      String localPart = text.substring(text.indexOf(':') + 1, text.length());
      int i = text.indexOf(':');
      String prefix = i < 0 ? null : text.substring(0, i);
      String namespaceURI = prefix == null ? defaultNS : reader.getNamespaceURI(prefix);
      return prefix == null ? new QName(namespaceURI, localPart) : new QName(namespaceURI, localPart, prefix);
   }
   
   public static int nextElement(XMLStreamReader reader)
   {
      try
      {
         int x = reader.next();
         while (x != XMLStreamReader.START_ELEMENT && x != XMLStreamReader.END_ELEMENT && reader.hasNext())
         {
            x = reader.next();
         }
         return x;
      }
      catch (XMLStreamException e)
      {
         throw Messages.MESSAGES.couldNotParseStream(e);
      }
   }
}
