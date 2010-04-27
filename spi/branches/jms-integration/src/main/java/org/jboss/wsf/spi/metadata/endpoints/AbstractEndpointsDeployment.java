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

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.deployment.Deployment;

/**
 * The pojo bean to deploy the stack specific endpoints descriptor url
 * 
 * @author <a href="ema@redhat.com">Jim Ma</a>
 */
public abstract class AbstractEndpointsDeployment
{
   protected final Logger log = Logger.getLogger(getClass());

   protected URL url;

   protected EndpointsMetaData endpointsMetaData;
   
   protected Deployment deployment;

   public Deployment getDeployment()
   {
      return deployment;
   }

   public void setDeployment(Deployment deployment)
   {
      this.deployment = deployment;
   }

   public EndpointsMetaData getEndpointsMetaData()
   {
      return endpointsMetaData;
   }

   public void setEndpointsMetaData(EndpointsMetaData endpointsMetaData)
   {
      this.endpointsMetaData = endpointsMetaData;
   }

   public void setURL(URL url)
   {
      this.url = url;
   }

   public URL getURL()
   {
      return this.url;
   }

   public abstract void start() throws Exception;

   public abstract void stop() throws Exception;
}
