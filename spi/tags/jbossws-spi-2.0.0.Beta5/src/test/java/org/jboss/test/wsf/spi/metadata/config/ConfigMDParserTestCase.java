/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.wsf.spi.metadata.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.jboss.wsf.spi.metadata.config.ClientConfig;
import org.jboss.wsf.spi.metadata.config.ConfigMetaDataParser;
import org.jboss.wsf.spi.metadata.config.ConfigRoot;
import org.jboss.wsf.spi.metadata.config.EndpointConfig;

/**
 * Test the UnifiedHandlersChainsMetaDataParser
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public class ConfigMDParserTestCase extends TestCase
{
   public void testParse() throws Exception
   {
      InputStream is = new FileInputStream(new File("src/test/resources/metadata/config/test-jaxws-config.xml"));
      ConfigRoot metadata = ConfigMetaDataParser.parse(is);

      List<ClientConfig> clientConfigs = metadata.getClientConfig();
      assertEquals(1, clientConfigs.size());
      ClientConfig cc = clientConfigs.get(0);
      assertEquals("Standard Client", cc.getConfigName());
      assertTrue(cc.getProperties().isEmpty());
      assertNull(cc.getPreHandlerChains());
      assertNull(cc.getPostHandlerChains());

      List<EndpointConfig> endpointConfigs = metadata.getEndpointConfig();
      assertEquals(2, endpointConfigs.size());

      EndpointConfig ec = metadata.getEndpointConfigByName("Standard Endpoint");
      assertEquals(1, ec.getPreHandlerChains().size());
      assertEquals(1, ec.getPreHandlerChains().get(0).getHandlers().size());
      assertEquals(1, ec.getPostHandlerChains().size());
      assertEquals(2, ec.getPostHandlerChains().get(0).getHandlers().size());
      assertEquals(2, ec.getProperties().size());
      assertEquals("value1", ec.getProperty("name1"));
      assertEquals("value2", ec.getProperty("name2"));

      ec = metadata.getEndpointConfigByName(".NET friendly Endpoint");
      assertNull(ec.getPreHandlerChains());
      assertNull(ec.getPostHandlerChains());
      assertTrue(ec.getProperties().isEmpty());
      assertTrue(ec.hasFeature("http://org.jboss.ws/binding/wsdl/dotnet"));
   }
}