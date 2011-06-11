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
package org.jboss.test.wsf.spi.metadata.j2ee.serviceref;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

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
public class UnifiedHandlersChainsMDParserTestCase extends TestCase
{
   public void testParse() throws Exception
   {
      InputStream is = new FileInputStream(new File("src/test/resources/metadata/j2ee/serviceref/test-handlers.xml"));
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
      assertTrue(handler.getSoapHeaders().contains(new QName("http://java.sun.com/xml/ns/javaee", "secondHeader")));
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