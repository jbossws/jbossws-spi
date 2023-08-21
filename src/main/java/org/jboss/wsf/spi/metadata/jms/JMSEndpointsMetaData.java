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
package org.jboss.wsf.spi.metadata.jms;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="ema@redhat.com">Jim Ma</a>
 * @author <a href="alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public final class JMSEndpointsMetaData
{
   private final List<JMSEndpointMetaData> jmsEndpointsMD;
   
   public JMSEndpointsMetaData(List<JMSEndpointMetaData> jmsEndpointsMD)
   {
      if (jmsEndpointsMD != null && !jmsEndpointsMD.isEmpty()) {
         this.jmsEndpointsMD = Collections.unmodifiableList(jmsEndpointsMD);
      } else {
         this.jmsEndpointsMD = Collections.emptyList();
      }
   }

   public List<JMSEndpointMetaData> getEndpointsMetaData() {
      return jmsEndpointsMD;
   }

}
