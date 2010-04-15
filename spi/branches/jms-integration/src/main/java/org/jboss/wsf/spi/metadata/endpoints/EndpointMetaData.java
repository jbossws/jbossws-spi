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
package org.jboss.wsf.spi.metadata.endpoints;

/**
 * Meta data class contains the implementor and address information
 * @author <a href="ema@redhat.com">Jim Ma</a>
 */
public class EndpointMetaData
{
   //Endpoint name
   private String name;

   //Service name
   private String serviceName = "";

   //port name
   private String portName = "";

   //implementor class
   private String implementor = "";

   //wsdl location
   private String wsdlLocation = "";

   //endpoint address information
   private AddressMetaData addressMetaData = null;

   //parent component 
   private EndpointsMetaData endpointsMetaData = null;

   public EndpointMetaData(EndpointsMetaData endpoints)
   {
      endpointsMetaData = endpoints;
   }

   public void addAddressMetaData(AddressMetaData address)
   {
      this.addressMetaData = address;
   }

   public AddressMetaData getAddressMetaData()
   {
      return addressMetaData;
   }

   public EndpointsMetaData getEndpointsMetaData()
   {
      return endpointsMetaData;
   }

   public String getImplementor()
   {
      return implementor;
   }

   public String getName()
   {
      return name;
   }

   public String getPortName()
   {
      return portName;
   }

   public String getServiceName()
   {
      return serviceName;
   }

   public String getWsdlLocation()
   {
      return wsdlLocation;
   }

   public void setImplementor(String implementor)
   {
      this.implementor = implementor;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setPortName(String portName)
   {
      this.portName = portName;
   }

   public void setServiceName(String serviceName)
   {
      this.serviceName = serviceName;
   }

   public void setWsdlLocation(String wsdlLocation)
   {
      this.wsdlLocation = wsdlLocation;
   }

}
