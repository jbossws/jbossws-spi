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
package org.jboss.test.wsf.spi.metadata.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class ConfigMDParserTestCase
{
   @Test
   public void testParseJavaEE() throws Exception {
      parseFile("src/test/resources/metadata/config/test-jaxws-config.xml");
   }

   @Test
   public void testParseJakarta() throws Exception {
      parseFile("src/test/resources/metadata/config/test-jaxws-config-jakarta.xml");
   }
   public void parseFile(String filename) throws Exception
   {
      InputStream is = new FileInputStream(new File(filename));
      ConfigRoot metadata = ConfigMetaDataParser.parse(is);

      List<ClientConfig> clientConfigs = metadata.getClientConfig();
      assertEquals(1, clientConfigs.size());
      ClientConfig cc = clientConfigs.get(0);
      assertEquals("Standard Client", cc.getConfigName());
      assertTrue(cc.getProperties().isEmpty());
      assertTrue(cc.getPreHandlerChains() == null || cc.getPreHandlerChains().isEmpty());
      assertTrue(cc.getPostHandlerChains() == null || cc.getPostHandlerChains().isEmpty());

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
      assertTrue(ec.getPreHandlerChains() == null || ec.getPreHandlerChains().isEmpty());
      assertTrue(ec.getPostHandlerChains() == null || ec.getPostHandlerChains().isEmpty());
      assertTrue(ec.getProperties().isEmpty());
      assertTrue(ec.hasFeature("http://org.jboss.ws/binding/wsdl/dotnet"));
   }
}