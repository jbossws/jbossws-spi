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
package org.jboss.wsf.spi.metadata.webservices;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * XML Binding root element for <code>webservices.xml</code>
 *
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 15-April-2004
 */
public class WebservicesMetaData
{
   // The required <webservice-description> elements
   private final List<WebserviceDescriptionMetaData> webserviceDescriptions;

   // The URL to the webservices.xml descriptor
   private final URL descriptorURL;
   
   public WebservicesMetaData(URL descriptorURL, WebserviceDescriptionMetaData... webserviceDescriptions)
   {
      this(descriptorURL, webserviceDescriptions != null ? Arrays.asList(webserviceDescriptions) : null);
   }
   
   public WebservicesMetaData(URL descriptorURL, List<WebserviceDescriptionMetaData> webserviceDescriptions)
   {
      if (webserviceDescriptions != null && !webserviceDescriptions.isEmpty()) {
         this.webserviceDescriptions = Collections.unmodifiableList(webserviceDescriptions);
         for (WebserviceDescriptionMetaData wsdmd : webserviceDescriptions) {
            wsdmd.setWebservices(this);
         }
      } else {
         this.webserviceDescriptions = Collections.emptyList();
      }
      this.descriptorURL = descriptorURL;
   }

   public URL getDescriptorURL()
   {
      return descriptorURL;
   }

   public WebserviceDescriptionMetaData[] getWebserviceDescriptions()
   {
      WebserviceDescriptionMetaData[] array = new WebserviceDescriptionMetaData[webserviceDescriptions.size()];
      webserviceDescriptions.toArray(array);
      return array;
   }

   //Serialize as a String
   public String serialize()
   {
      //Construct the webservices.xml definitions
      StringBuilder buffer = new StringBuilder();

      // header: opening webservices tag
      createHeader(buffer);

      // webservice-description subelements
      for (WebserviceDescriptionMetaData wm : webserviceDescriptions)
         buffer.append(wm.serialize());

      // closing webservices tag
      buffer.append("</webservices>");
      return buffer.toString();
   }

   private void createHeader(StringBuilder buf)
   {
      buf.append("<webservices xmlns='http://java.sun.com/xml/ns/javaee'");
      buf.append(" xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'");
      buf.append(" xsi:schemaLocation='http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/javaee_web_services_1_2.xsd'");
      buf.append(" version='1.2'>");
   }
}
