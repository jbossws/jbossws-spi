/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
