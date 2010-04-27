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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Stack independent top level meta data class from jbossws-endpoints.xml. </p>
 * <p>This class contains the following example xml content : </p>
 * <pre>
 * &lt;jaxws:endpoints xmlns:jaxws="http://jbossws.jboss.org/jaxws"
 *   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *   xmlns:jms="http://jbossws.jboss.org/jbossws/transport/jms"
 *   xsi:schemaLocation="http://jbossws.jboss.org/jaxws jaxws.xsd http://jbossws.jboss.org/jaxws/transport/jms jms.xsd">
 *   &lt;jaxws:endpoint implementor="" name="" portName=""
 *       serviceName="" wsdlLocation="">
 *          &lt;jms:Address>
 *             &lt;jms:requestDestination name="test">
 *                 &lt;jms:property name="" value="" />
 *                 &lt;jms:property name="" value="" />
 *              &lt;/jms:requestDestination>
          
 *              &lt;jms:replyDestination name="test">
 *                  &lt;jms:property name="" value="" />
 *                  &lt;jms:property name="" value="" />
 *              &lt;/jms:replyDestination>     
 *           &lt;/jms:Address>
 *   &lt;/jaxws:endpoint>
 * &lt;/jaxws:endpoints>
 * </pre>
 * 
 * @author &lt;a href="ema@redhat.com">Jim Ma&lt;/a>
 */

public class EndpointsMetaData
{
   //The endpoints list
   private List<EndpointMetaData> endpointsMetaData = new ArrayList<EndpointMetaData>();

   //The jbossws-endpoint.xml location
   private URL descriptorURL;

   public EndpointsMetaData()
   {
   }

   public EndpointsMetaData(URL descriptorURL)
   {
      this.descriptorURL = descriptorURL;
   }

   public URL getDescriptorURL()
   {
      return descriptorURL;
   }

   public void addEndpointMetaData(EndpointMetaData endpointMetaData)
   {
      endpointsMetaData.add(endpointMetaData);
   }
   
   public List<EndpointMetaData> getEndpointsMetaData() {
      return this.endpointsMetaData;
   }
}
