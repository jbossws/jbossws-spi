/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata.endpoints;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.ws.WebServiceException;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.metadata.endpoints.jms.JMSAddressMetaData;
import org.jboss.wsf.spi.metadata.endpoints.jms.JMSDestinationMetaData;
import org.jboss.xb.binding.ObjectModelFactory;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.xml.sax.Attributes;

/**
 * Endpoints OjbectModelFactory
 * 
 * @author <a href="ema@redhat.com">Jim Ma</a>
 */
public class EndpointsFactory implements ObjectModelFactory
{
   public static final String DESCRIPTOR_FILE = "jbossws-endpoints.xml";

   private static final Logger log = Logger.getLogger(EndpointsFactory.class);

   // The URL to the jbossws-endpoints.xml descriptor
   private URL descriptorURL;

   public EndpointsFactory(URL descriptorURL)
   {
      this.descriptorURL = descriptorURL;
   }

   /**
    * Load jbossws-endpoints.xml under META-INF/
    * , WEB-INF or / 
    *
    * @param root virtual file root
    * @return EndpointMetaData or <code>null</code> if it cannot be found
    */
   public static EndpointsMetaData loadFromVFSRoot(UnifiedVirtualFile root)
   {
      EndpointsMetaData endpoints = null;

      UnifiedVirtualFile endpointsDD = null;
      try
      {
         endpointsDD = root.findChild("META-INF/" + DESCRIPTOR_FILE);
      }
      catch (IOException e)
      {
         //
      }

      if (null == endpointsDD)
      {
         try
         {
            endpointsDD = root.findChild("WEB-INF/" + DESCRIPTOR_FILE);
         }
         catch (IOException e)
         {
            //
         }
      }

      if (null == endpointsDD)
      {
         try
         {
            endpointsDD = root.findChild(DESCRIPTOR_FILE);
         }
         catch (IOException e)
         {
            //
         }
      }

      if (endpointsDD != null)
      {

         URL endpointsUrl = endpointsDD.toURL();
         try
         {
            InputStream is = endpointsUrl.openStream();
            Unmarshaller unmarshaller = UnmarshallerFactory.newInstance().newUnmarshaller();
            ObjectModelFactory factory = new EndpointsFactory(endpointsUrl);
            endpoints = (EndpointsMetaData) unmarshaller.unmarshal(is, factory, null);
            is.close();
         }
         catch (Exception e)
         {
            log.error("Failed to unmarshall " + DESCRIPTOR_FILE);
            throw new WebServiceException("Failed to unmarshall " + DESCRIPTOR_FILE + "," + e.getMessage());
         }
      }

      return endpoints;
   }

   /**
    * This method is called on the factory by the object model builder when the parsing starts.
    *
    * @return the root of the object model.
    */
   public Object newRoot(Object root, UnmarshallingContext navigator, String namespaceURI, String localName,
         Attributes attrs)
   {
      EndpointsMetaData endpointsMetaData = new EndpointsMetaData(descriptorURL);
      return endpointsMetaData;
   }

   public Object completeRoot(Object root, UnmarshallingContext ctx, String uri, String name)
   {
      return root;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EndpointsMetaData endpoints, UnmarshallingContext navigator, String namespaceURI,
         String localName, Attributes attrs)
   {
      if ("endpoint".equals(localName))
      {
         EndpointMetaData endpoint = new EndpointMetaData(endpoints);
         endpoint.setName(attrs.getValue("name"));
         endpoint.setImplementor(attrs.getValue("implementor"));
         endpoint.setPortName(attrs.getValue("portName"));
         endpoint.setServiceName(attrs.getValue("serviceName"));
         endpoint.setWsdlLocation(attrs.getValue("wsdlLocation"));
         return endpoint;

      }
      else
         return null;
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EndpointsMetaData endpoints, EndpointMetaData endpoint, UnmarshallingContext navigator,
         String namespaceURI, String localName)
   {
      endpoints.addEndpointMetaData(endpoint);
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EndpointMetaData endpoint, UnmarshallingContext navigator, String namespaceURI,
         String localName, Attributes attrs)
   {
      if (JMSAddressMetaData.NAMESPACE_URI.equals(namespaceURI)
            && JMSAddressMetaData.ADDRESS_LOCAL_NAME.equals(localName))
      {
         JMSAddressMetaData jmsMetaData =  new JMSAddressMetaData(endpoint);
         jmsMetaData.setPortName(attrs.getValue("portName"));
         return jmsMetaData;
      }

      else
         return null;
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EndpointMetaData endpoint, JMSAddressMetaData jmsAddress, UnmarshallingContext navigator,
         String namespaceURI, String localName)
   {
      endpoint.addAddressMetaData(jmsAddress);
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(JMSAddressMetaData jmsAddress, UnmarshallingContext navigator, String namespaceURI,
         String localName, Attributes attrs)
   {
      if ("requestDestination".equals(localName))
      {
         JMSDestinationMetaData requestDest = new JMSDestinationMetaData();
         jmsAddress.setRequestDestination(requestDest);
         return requestDest;
      }
      else if ("replyDestination".equals(localName))
      {
         JMSDestinationMetaData replyDest = new JMSDestinationMetaData();
         jmsAddress.setReplyDestination(replyDest);
         return replyDest;
      }
      else
         return null;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(JMSDestinationMetaData jmsDestination, UnmarshallingContext navigator, String namespaceURI,
         String localName, Attributes attrs)
   {
      if ("property".equals(localName))
         jmsDestination.setProperty(attrs.getValue("name"), attrs.getValue("value"));
      return null;
   }

}
