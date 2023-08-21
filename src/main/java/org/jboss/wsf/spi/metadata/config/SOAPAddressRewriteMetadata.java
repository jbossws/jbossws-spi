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
