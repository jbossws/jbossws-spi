/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.deployment;

import java.util.List;

/**
 * A service collecting endpoints belonging to the same deployment. 
 * 
 * @author Thomas.Diesler@jboss.com
 * @author alessio.soldano@jboss.com
 * @since 20-Apr-2007 
 */
public interface Service extends Extensible
{

   /**
    * Get the deployment this service belongs to
    * 
    * @return the deployment this service belongs to
    */
   Deployment getDeployment();
   
   /**
    * Add an endpoint to the service
    * 
    * @param endpoint   the endpoint to be added
    */
   void addEndpoint(Endpoint endpoint);
   
   /**
    * Remove an endpoint from the service, returns true if successfull
    * 
    * @param endpoint   the endpoint to remove
    * @return           true if the endpoint was actually removed, false otherwise
    */
   boolean removeEndpoint(Endpoint endpoint);
   
   /**
    * Get the list of endpoints
    * 
    * @return   a copy of the list of endpoints
    */
   List<Endpoint> getEndpoints();
   
   /**
    * Get the list of endpoints
    * 
    * @param filter a filter for selecting endpoints
    * @return       a list of selected endpoints
    */
   List<Endpoint> getEndpoints(EndpointTypeFilter filter);
   
   /**
    * Get an endpoint by name
    * 
    * @param simpleName the name of the endpoint to get
    * @return           the selected endpoint
    */
   Endpoint getEndpointByName(String simpleName);
   
   /**
    * Get the context root for this service
    * 
    * @return   the context root for this service
    */
   String getContextRoot();
   
   /**
    * Set the context root for this service
    * 
    * @param contextRoot    the context root for this service
    */
   void setContextRoot(String contextRoot);
   
   /**
    * Get the virtual host for this service
    * 
    * @return   the virtual host for this service
    */
   String getVirtualHost();
   
   /**
    * Set the virtual host for this service
    * 
    * @param virtualHost    the virtual host for this service
    */
   void setVirtualHost(String virtualHost);
   
}
