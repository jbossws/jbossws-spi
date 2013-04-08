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
package org.jboss.test.wsf.spi.metadata.webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

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
public class WebServiceFactoryTestCase extends TestCase
{
   public void testParse() throws Exception
   {
      File file = new File("src/test/resources/metadata/webservices/test-webservices.xml");
      InputStream is = new FileInputStream(file);
      URL url = file.toURI().toURL();
      WebservicesMetaData metadata = WebservicesFactory.parse(is, url);
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