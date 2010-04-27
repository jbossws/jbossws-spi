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
package org.jboss.wsf.spi.metadata.endpoints.jms;

import org.jboss.wsf.spi.metadata.endpoints.AddressMetaData;
import org.jboss.wsf.spi.metadata.endpoints.EndpointMetaData;

/**
 * JMSAddressMetaData.
 * 
 * @author <a href="ema@redhat.com">Jim Ma</a>
 */
public class JMSAddressMetaData extends AddressMetaData
{
   public static final String NAMESPACE_URI = "http://jbossws.jboss.org/jbossws/transport/jms";

   public static final String ADDRESS_LOCAL_NAME = "Address";

   private EndpointMetaData endpoint;

   private JMSDestinationMetaData requestDestination;

   private JMSDestinationMetaData replyDestination;
   
   private String portName= "";

   public JMSAddressMetaData(EndpointMetaData endpoint)
   {
      this.endpoint = endpoint;
   }

   public EndpointMetaData getEndpoint()
   {
      return endpoint;
   }

   public JMSDestinationMetaData getReplyDestination()
   {
      return replyDestination;
   }

   public JMSDestinationMetaData getRequestDestination()
   {
      return requestDestination;
   }

   public void setReplyDestination(JMSDestinationMetaData replyDestination)
   {
      this.replyDestination = replyDestination;
   }

   public void setRequestDestination(JMSDestinationMetaData requestDestination)
   {
      this.requestDestination = requestDestination;
   }
   
   public String getPortName()
   {
      return portName;
   }

   public void setPortName(String portName)
   {
      this.portName = portName;
   }
}
