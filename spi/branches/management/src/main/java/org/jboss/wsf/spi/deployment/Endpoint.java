/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat Middleware LLC, and individual contributors
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
import java.util.Map;

import javax.management.ObjectName;

import org.jboss.ws.api.monitoring.Record;
import org.jboss.ws.api.monitoring.RecordProcessor;
import org.jboss.wsf.spi.invocation.InvocationHandler;
import org.jboss.wsf.spi.invocation.RequestHandler;
import org.jboss.wsf.spi.management.EndpointMetrics;
import org.jboss.wsf.spi.metadata.config.EndpointConfig;
import org.jboss.wsf.spi.security.SecurityDomainContext;

/**
 * A general JAXWS endpoint.
 * 
 * @author Thomas.Diesler@jboss.com
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 * @since 20-Apr-2007 
 */
public interface Endpoint extends Extensible
{

   static final String SEPID_DOMAIN = "jboss.ws";
   static final String SEPID_PROPERTY_CONTEXT = "context";
   static final String SEPID_PROPERTY_ENDPOINT = "endpoint";
   static final String SERVICE = "servce";
   static final String NAME = "name";
   static final String SHORTNAME="shortname";
   static final String STATE = "state";
   static final String TYPE = "type";
   static final String TARGETBEAN = "targetBean";
   static final String REQUESTHANDLER = "requestHandler";
   static final String INVOCATIONHANDLER = "invocationHandler";
   static final String LIFECYCLEHANDLER = "lifecyleHandler";
   static final String ADDRESS = "address";
   static final String PRE_HANDLERCHAIN = "preHandlerChain";
   static final String POST_HANDLERCHAIN = "postHandlerChain";
   static final String SECURITY_DOMAIN = "securityDomain";

   static final String SEPID_DOMAIN_ENDPOINT = SEPID_DOMAIN + "." + SEPID_PROPERTY_ENDPOINT;

   /** Get the service this endpoint belongs to */
   Service getService();

   /** Set the service this endpoint belongs to */
   void setService(Service service);

   /** Get the unique identifier for this endpoint */
   ObjectName getName();

   /** Set the unique identifier for this endpoint */
   void setName(ObjectName epName);

   /** Get the short name for this endpoint */
   String getShortName();
   
   /** Set the short name for this endpoint */
   void setShortName(String shortName);
   
   /** Get the current state for this endpoint */
   EndpointState getState();

   /** Set the current state for this endpoint */
   void setState(EndpointState state);

   /** Get endpoint type */
   EndpointType getType();

   /** Set endpoint type */
   void setType(EndpointType type);

   /** Get the endpoint implementation bean */
   String getTargetBeanName();

   /** Set the endpoint implementation bean */
   void setTargetBeanName(String epImpl);
   
   /** Use the deployment classloader to load the bean */
   Class<?> getTargetBeanClass();
      
   /** Set the request handler for this endpoint */
   void setRequestHandler(RequestHandler handler);

   /** Get the request handler for this endpoint */
   RequestHandler getRequestHandler();

   /** Get the lifecycle handler for this endpoint */
   LifecycleHandler getLifecycleHandler();

   /** Set the lifecycle handler for this endpoint */
   void setLifecycleHandler(LifecycleHandler handler);

   /** Get the endpoint bean invoker */
   InvocationHandler getInvocationHandler();

   /** Set the endpoint bean invoker */
   void setInvocationHandler(InvocationHandler invoker);

   /** Get the endpoint metrics for this endpoint */
   EndpointMetrics getEndpointMetrics();

   /** Set the endpoint metrics for this endpoint */
   void setEndpointMetrics(EndpointMetrics metrics);
   
   /** Get the record processors configured for this endpoint **/
   List<RecordProcessor> getRecordProcessors();
   
   /** Set the record processors for this endpoint **/
   void setRecordProcessors(List<RecordProcessor> recordProcessors);
   
   /** Ask configured processors for processing of the given record **/
   void processRecord(Record record);
   
   /** Get endpoint address */
   String getAddress();

   /** Set endpoint address */
   void setAddress(String address);
   
   /** Get security domain context */
   SecurityDomainContext getSecurityDomainContext();
   
   /** Set security domain context */
   void setSecurityDomainContext(SecurityDomainContext context);

   /** Get instance provider */
   InstanceProvider getInstanceProvider();

   /** Set instance provider */
   void setInstanceProvider(InstanceProvider provider);

   /** Get endpoint config */
   EndpointConfig getEndpointConfig();
   
   /** Set endpoint config */
   void setEndpointConfig(EndpointConfig config); 
   
   Map<String, String> getAllConfigsMap();
   
}
