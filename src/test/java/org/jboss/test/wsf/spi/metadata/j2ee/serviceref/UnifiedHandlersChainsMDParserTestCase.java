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
package org.jboss.test.wsf.spi.metadata.j2ee.serviceref;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaDataParser;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedInitParamMetaData;

/**
 * Test the UnifiedHandlersChainsMetaDataParser
 *
 * @author alessio.soldano@jboss.com
 * @since 27-Nov-2010
 */
public class UnifiedHandlersChainsMDParserTestCase
{
   @Test
   public void testParseJavaEE() throws Exception {
      parseFile("src/test/resources/metadata/j2ee/serviceref/test-handlers.xml");
   }

   @Test
   public void testParseJakartaEE() throws Exception {
      parseFile("src/test/resources/metadata/j2ee/serviceref/test-handlers-jakarta.xml");
   }

   public void parseFile(String filename) throws Exception
   {
      InputStream is = new FileInputStream(new File(filename));
      UnifiedHandlerChainsMetaData metadata = UnifiedHandlerChainsMetaDataParser.parse(is);
      List<UnifiedHandlerChainMetaData> chains = metadata.getHandlerChains();
      assertEquals(2, chains.size());
      testHandlerChain1(chains.get(0));
      testHandlerChain2(chains.get(1));
   }

   private void testHandlerChain1(UnifiedHandlerChainMetaData chain)
   {
      assertEquals(new QName("http://ws.jboss.org/jbws2949", "EndpointPort", "foo"), chain.getPortNamePattern());
      assertEquals("foo", chain.getPortNamePattern().getPrefix());
      assertEquals(new QName("http://ws.jboss.org/jbws2949", "EndpointService", "foo"), chain.getServiceNamePattern());
      assertEquals("##SOAP11_HTTP", chain.getProtocolBindings());
      List<UnifiedHandlerMetaData> handlers = chain.getHandlers();
      assertEquals(1, handlers.size());
      UnifiedHandlerMetaData handler = handlers.get(0);
      assertEquals(chain, handler.getHandlerChain());
      assertEquals("SOAPServerHandler1", handler.getHandlerName());
      assertEquals("org.jboss.test.ws.jaxws.jbws2949.SOAPHandler1", handler.getHandlerClass());
      List<UnifiedInitParamMetaData> params = handler.getInitParams();
      assertEquals(2, params.size());
      assertEquals("foo", params.get(0).getParamName());
      assertEquals("1", params.get(0).getParamValue());
      assertEquals("bar", params.get(1).getParamName());
      assertEquals("2", params.get(1).getParamValue());
      assertEquals(0, handler.getPortNames().size());
      assertEquals(2, handler.getSoapHeaders().size());
      assertTrue(handler.getSoapHeaders().contains(new QName("http://org.jboss.ws/jaxws/samples/logicalhandler", "firstHeader")));
      assertTrue(handler.getSoapHeaders().contains(new QName("https://jakarta.ee/xml/ns/jakartaee", "secondHeader")) ||
              handler.getSoapHeaders().contains(new QName("http://java.sun.com/xml/ns/javaee", "secondHeader")));
      assertEquals(1, handler.getSoapRoles().size());
      assertEquals("MyRole", handler.getSoapRoles().iterator().next());
   }
   
   private void testHandlerChain2(UnifiedHandlerChainMetaData chain)
   {
      assertEquals(new QName("http://ws.jboss.org/jbws2949", "EndpointPort2", "ns1"), chain.getPortNamePattern());
      assertEquals("ns1", chain.getPortNamePattern().getPrefix());
      assertEquals(new QName("http://org.jboss.ws/jaxws/samples/logicalhandler", "EndpointService2", "ns1"), chain.getServiceNamePattern());
      assertEquals("ns1", chain.getServiceNamePattern().getPrefix());
      assertEquals(null, chain.getProtocolBindings());
      List<UnifiedHandlerMetaData> handlers = chain.getHandlers();
      assertEquals(2, handlers.size());
      UnifiedHandlerMetaData handler1 = handlers.get(0);
      assertEquals(chain, handler1.getHandlerChain());
      assertEquals("SOAPServerHandler2", handler1.getHandlerName());
      assertEquals("org.jboss.test.ws.jaxws.jbws2949.SOAPHandler2", handler1.getHandlerClass());
      assertEquals(0, handler1.getInitParams().size());
      assertEquals(0, handler1.getPortNames().size());
      assertEquals(0, handler1.getSoapHeaders().size());
      assertEquals(0, handler1.getSoapRoles().size());

      UnifiedHandlerMetaData handler2 = handlers.get(1);
      assertEquals(chain, handler2.getHandlerChain());
      assertEquals("SOAPServerHandler3", handler2.getHandlerName());
      assertEquals("org.jboss.test.ws.jaxws.jbws2949.SOAPHandler3", handler2.getHandlerClass());
      assertEquals(0, handler2.getInitParams().size());
      assertEquals(0, handler2.getPortNames().size());
      assertEquals(0, handler2.getSoapHeaders().size());
      assertEquals(0, handler2.getSoapRoles().size());

   }
}