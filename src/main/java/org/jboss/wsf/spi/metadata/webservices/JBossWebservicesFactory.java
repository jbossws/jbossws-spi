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
import static org.jboss.wsf.spi.metadata.ParserConstants.CONFIG_FILE;
import static org.jboss.wsf.spi.metadata.ParserConstants.CONFIG_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.CONTEXT_ROOT;
import static org.jboss.wsf.spi.metadata.ParserConstants.JBOSSEE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICES;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICE_DESCRIPTION;
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
import org.jboss.wsf.spi.util.StAXUtils;

/**
 * A JBossXB factory for {@link JBossWebservicesMetaData}
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class JBossWebservicesFactory extends WebservicesFactory
{
   private static final ResourceBundle bundle = BundleUtils.getBundle(JBossWebservicesFactory.class);
   
   public JBossWebservicesFactory(URL descriptorURL)
   {
      super(descriptorURL);
   }

   /**
    * Load jboss-webservices.xml from <code>META-INF/jboss-webservices.xml</code>
    * or <code>WEB-INF/jboss-webservices.xml</code>.
    *
    * @param root virtual file root
    * @return JBossWebservicesMetaData or <code>null</code> if it cannot be found
    */
   public static WebservicesMetaData loadFromVFSRoot(UnifiedVirtualFile root)
   {
      WebservicesMetaData webservices = null;

      UnifiedVirtualFile wsdd = null;
      try
      {
         wsdd = root.findChild("META-INF/jboss-webservices.xml");
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
            wsdd = root.findChild("WEB-INF/jboss-webservices.xml");
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
   
   public static JBossWebservicesMetaData load(URL wsddUrl)
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
   
   public static JBossWebservicesMetaData parse(InputStream is)
   {
      return parse(is, null);
   }
   
   public static JBossWebservicesMetaData parse(InputStream is, URL descriptorURL)
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
   
   public static JBossWebservicesMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      return parse(reader, null);
   }
   
   private static JBossWebservicesMetaData parse(XMLStreamReader reader, URL descriptorURL) throws XMLStreamException
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
      JBossWebservicesMetaData metadata = null;
      switch (iterate)
      {
         case END_ELEMENT : {
            // we're done
            break;
         }
         case START_ELEMENT : {

            if (match(reader, JBOSSEE_NS, WEBSERVICES))
            {
               String nsUri = reader.getNamespaceURI();
               JBossWebservicesFactory factory = new JBossWebservicesFactory(descriptorURL);
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
   
   private JBossWebservicesMetaData parseWebservices(XMLStreamReader reader, String nsUri, URL descriptorURL) throws XMLStreamException
   {
      JBossWebservicesMetaData metadata = new JBossWebservicesMetaData(descriptorURL);
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
               if (match(reader, nsUri, CONTEXT_ROOT)) {
                  metadata.setContextRoot(elementAsString(reader));
               }
               else if (match(reader, nsUri, CONFIG_NAME)) {
                  metadata.setConfigName(elementAsString(reader));
               }
               else if (match(reader, nsUri, CONFIG_FILE)) {
                  metadata.setConfigFile(elementAsString(reader));
               }
               else if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                  metadata.addWebserviceDescription(super.parseWebserviceDescription(reader, nsUri, metadata));
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
