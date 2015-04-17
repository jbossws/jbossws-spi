/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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

import java.util.Collections;
import java.util.Map;

import org.jboss.wsf.spi.management.ServerConfig;
import org.jboss.wsf.spi.metadata.webservices.JBossWebservicesMetaData;


/** 
 * 
 * @author alessio.soldano@jboss.com
 * @since 19-Sep-2014
 */
public class SOAPAddressRewriteMetadata
{
   public static final String WEBSERVICE_HOST = "wsdl.soapAddress.rewrite.wsdl-host";
   public static final String WEBSERVICE_PORT = "wsdl.soapAddress.rewrite.wsdl-port";
   public static final String WEBSERVICE_SECURE_PORT = "wsdl.soapAddress.rewrite.wsdl-secure-port";
   public static final String WEBSERVICE_PATH_REWRITE_RULE = "wsdl.soapAddress.rewrite.wsdl-path-rewrite-rule";
   public static final String WEBSERVICE_URI_SCHEME = "wsdl.soapAddress.rewrite.wsdl-uri-scheme";
   public static final String MODIFY_SOAP_ADDRESS = "wsdl.soapAddress.rewrite.modify-wsdl-address";
   
   private final ServerConfig serverConfig;
   private final Map<String, String> props;
   
   public SOAPAddressRewriteMetadata(ServerConfig serverConfig, JBossWebservicesMetaData jbwsmd) {
      this.serverConfig = serverConfig;
      Map<String, String> p = jbwsmd != null ? jbwsmd.getProperties() : null;
      if (p != null) {
         this.props = p;
      } else {
         this.props = Collections.emptyMap();
      }
   }
   
   
   public String getWebServiceHost() {
      String res = props.get(WEBSERVICE_HOST);
      return res != null ? res : serverConfig.getWebServiceHost();
   }
   
   public int getWebServicePort() {
      String res = props.get(WEBSERVICE_PORT);
      return res != null ? Integer.parseInt(res) : serverConfig.getWebServicePort();
   }
   
   public int getWebServiceSecurePort() {
      String res = props.get(WEBSERVICE_SECURE_PORT);
      return res != null ? Integer.parseInt(res) : serverConfig.getWebServiceSecurePort();
   }

   public String getWebServicePathRewriteRule() {
      String res = props.get(WEBSERVICE_PATH_REWRITE_RULE);
      return res != null ? res : serverConfig.getWebServicePathRewriteRule();
   }
   
   public String getWebServiceUriScheme() {
      String res = props.get(WEBSERVICE_URI_SCHEME);
      return res != null ? res : serverConfig.getWebServiceUriScheme();
   }
   
   public boolean isModifySOAPAddress() {
      String res = props.get(MODIFY_SOAP_ADDRESS);
      return res != null ? Boolean.parseBoolean(res) : serverConfig.isModifySOAPAddress();
   }
   
}
