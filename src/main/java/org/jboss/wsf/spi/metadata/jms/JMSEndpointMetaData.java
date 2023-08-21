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

/**
 * JMS Meta data class contains the implementor and address information
 * @author <a href="ema@redhat.com">Jim Ma</a>
 * @author <a href="alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public class JMSEndpointMetaData
{
   //Endpoint name
   private final String name;
   //port name
   private final String endpointName;
   //implementor class
   private final String implementor;
   //wsdl location
   private final String wsdlLocation;
   private final String soapAddress;
   //parent component 
   private volatile JMSEndpointsMetaData endpointsMetaData;
   
   public JMSEndpointMetaData(String name, String endpointName, String implementor, String wsdlLocation,
         String soapAddress)
   {
      this.name = name;
      this.endpointName = endpointName;
      this.implementor = implementor;
      this.wsdlLocation = wsdlLocation;
      this.soapAddress = soapAddress;
   }

   public JMSEndpointsMetaData getParentMetaData()
   {
      return endpointsMetaData;
   }
   
   public void setParentMetaData(JMSEndpointsMetaData endpointsMetaData) {
      if (endpointsMetaData != null) {
         throw new IllegalStateException();
      }
      this.endpointsMetaData = endpointsMetaData;
   }

   public String getImplementor()
   {
      return implementor;
   }

   public String getName()
   {
      return name;
   }

   public String getEndpointName()
   {
      return endpointName;
   }

   public String getWsdlLocation()
   {
      return wsdlLocation;
   }

   public String getSoapAddress()
   {
      return soapAddress;
   }
}
