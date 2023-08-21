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
package org.jboss.test.wsf.spi.metadata.webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedInitParamMetaData;
import org.jboss.wsf.spi.metadata.webservices.PortComponentMetaData;
import org.jboss.wsf.spi.metadata.webservices.WebserviceDescriptionMetaData;
import org.jboss.wsf.spi.metadata.webservices.WebservicesFactory;
import org.jboss.wsf.spi.metadata.webservices.WebservicesMetaData;

/**
 * Test the WebServiceFactory
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Nov-2010
 */
public class WebServiceFactoryTestCase
{
   @Test
   public void testParse() throws Exception
   {
      parseFile("src/test/resources/metadata/webservices/test-webservices.xml");
   }

   @Test
   public void testParseJakarta() throws Exception
   {
      parseFile("src/test/resources/metadata/webservices/test-webservices-jakarta.xml");
   }

   public void parseFile(String filename) throws Exception
   {
      File file = new File(filename);
      InputStream is = new FileInputStream(file);
      URL url = file.toURI().toURL();
      WebservicesMetaData metadata = new WebservicesFactory(url).parse(is, url);
      assertEquals(url, metadata.getDescriptorURL());

      assertEquals(2, metadata.getWebserviceDescriptions().length);
      testDescription1(metadata.getWebserviceDescriptions()[0], metadata);
      testDescription2(metadata.getWebserviceDescriptions()[1], metadata);
   }
   
   private void testDescription1(WebserviceDescriptionMetaData description, WebservicesMetaData webservicesMetaData) throws Exception
   {
      assertEquals(null, description.getJaxrpcMappingFile());
      assertEquals("HelloService", description.getWebserviceDescriptionName());
      assertEquals("META-INF/wsdl/HelloService.wsdl", description.getWsdlFile());
      assertEquals(webservicesMetaData, description.getWebservices());
      PortComponentMetaData[] pcmds = description.getPortComponents();
      assertEquals(1, pcmds.length);
      PortComponentMetaData pcmd = pcmds[0];
      assertEquals(description, pcmd.getWebserviceDescription());
      assertEquals("HelloBean", pcmd.getPortComponentName());
      assertEquals("ANONYMOUS", pcmd.getAddressingResponses());
      assertEquals("jbws2999Test", pcmd.getEjbLink());
      assertEquals(null, pcmd.getServletLink());
      assertEquals(500, pcmd.getMtomThreshold());
      assertEquals(new QName("http://Hello.org", "HelloService"), pcmd.getWsdlService());
      assertEquals(new QName("http://Hello.org", "Hello"), pcmd.getWsdlPort());
      assertEquals(true, pcmd.isMtomEnabled());
      assertEquals(true, pcmd.isAddressingEnabled());
      assertEquals(true, pcmd.isRespectBindingEnabled());
      assertEquals(true, pcmd.isAddressingRequired());
      assertEquals("##SOAP11_HTTP", pcmd.getProtocolBinding());
      assertEquals("org.jboss.test.ws.jaxws.jbws2999.Hello", pcmd.getServiceEndpointInterface());
      assertEquals(null, pcmd.getContextRoot());
      
      UnifiedHandlerChainsMetaData chains = pcmd.getHandlerChains();
      assertEquals(1, chains.getHandlerChains().size());
      UnifiedHandlerChainMetaData chain = chains.getHandlerChains().get(0);
      assertEquals(null, chain.getPortNamePattern());
      assertEquals(null, chain.getServiceNamePattern());
      assertEquals(null, chain.getProtocolBindings());
      List<UnifiedHandlerMetaData> handlers = chain.getHandlers();
      assertEquals(1, handlers.size());
      UnifiedHandlerMetaData handler = handlers.get(0);
//      assertEquals(chain, handler.getHandlerChain());
      assertEquals("CustomHandler", handler.getHandlerName());
      assertEquals("org.jboss.test.ws.jaxws.jbws2999.CustomHandler", handler.getHandlerClass());
      List<UnifiedInitParamMetaData> params = handler.getInitParams();
      assertEquals(0, params.size());
      assertEquals(0, handler.getPortNames().size());
      assertEquals(0, handler.getSoapHeaders().size());
      assertEquals(0, handler.getSoapRoles().size());
   }
   
   private void testDescription2(WebserviceDescriptionMetaData description, WebservicesMetaData webservicesMetaData) throws Exception
   {
      assertEquals("WEB-INF/jaxrpc-mapping.xml", description.getJaxrpcMappingFile());
      assertEquals("SampleService", description.getWebserviceDescriptionName());
      assertEquals("WEB-INF/wsdl/SampleService.wsdl", description.getWsdlFile());
      assertEquals(webservicesMetaData, description.getWebservices());
      PortComponentMetaData[] pcmds = description.getPortComponents();
      assertEquals(1, pcmds.length);
      PortComponentMetaData pcmd = pcmds[0];
      assertEquals(description, pcmd.getWebserviceDescription());
      assertEquals("TrivialServicePort", pcmd.getPortComponentName());
      assertEquals("ALL", pcmd.getAddressingResponses());
      assertEquals(null, pcmd.getEjbLink());
      assertEquals("TrivialEndpoint", pcmd.getServletLink());
      assertEquals(0, pcmd.getMtomThreshold());
      assertEquals(null, pcmd.getWsdlService());
      assertEquals(new QName("http://org.jboss.ws/samples/docstyle/bare", "TrivialServicePort"), pcmd.getWsdlPort());
      assertEquals(false, pcmd.isMtomEnabled());
      assertEquals(false, pcmd.isAddressingEnabled());
      assertEquals(false, pcmd.isRespectBindingEnabled());
      assertEquals(false, pcmd.isAddressingRequired());
      assertEquals(null, pcmd.getProtocolBinding());
      assertEquals("org.jboss.test.ws.jaxrpc.samples.docstyle.bare.TrivialService", pcmd.getServiceEndpointInterface());
      assertEquals(null, pcmd.getContextRoot());
      
      UnifiedHandlerChainsMetaData chains = pcmd.getHandlerChains();
      assertEquals(null, chains);
   }
   
}