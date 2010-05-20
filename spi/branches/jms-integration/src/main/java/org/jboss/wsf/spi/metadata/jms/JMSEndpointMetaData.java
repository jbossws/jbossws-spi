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
package org.jboss.wsf.spi.metadata.jms;

/**
 * JMS Meta data class contains the implementor and address information
 * @author <a href="ema@redhat.com">Jim Ma</a>
 */
public class JMSEndpointMetaData
{
   //Endpoint name
   private String name;
   
   //port name
   private String endpointName = "";

   //implementor class
   private String implementor = "";

   //wsdl location
   private String wsdlLocation = "";
   

   //parent component 
   private JMSEndpointsMetaData endpointsMetaData = null;

   public JMSEndpointMetaData(JMSEndpointsMetaData endpoints)
   {
      endpointsMetaData = endpoints;
   }

   public JMSEndpointsMetaData getParentMetaData()
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

   public String getEndpointName()
   {
      return endpointName;
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

   public void setEndpointName(String endpointName)
   {
      this.endpointName = endpointName;
   }

   public void setWsdlLocation(String wsdlLocation)
   {
      this.wsdlLocation = wsdlLocation;
   }

}
