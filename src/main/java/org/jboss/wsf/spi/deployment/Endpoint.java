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

   /**
    * Get the service this endpoint belongs to
    *
    * @return  endpoint service
    */
   Service getService();

   /**
    * Set the service this endpoint belongs to
    *
    * @param service   endpoint service
    */
   void setService(Service service);

   /**
    * Get the unique identifier for this endpoint
    *
    * @return  endpoint identifier
    */
   ObjectName getName();

   /**
    * Set the unique identifier for this endpoint
    *
    * @param epName  endpoint identifier
    */
   void setName(ObjectName epName);

   /**
    * Get the short name for this endpoint
    *
    * @return  endpoint short name
    */
   String getShortName();
   
   /**
    * Set the short name for this endpoint
    *
    * @param shortName   endpoint short name
    */
   void setShortName(String shortName);
   
   /**
    * Get the current state for this endpoint
    *
    * @return  endpoint state
    */
   EndpointState getState();

   /**
    * Set the current state for this endpoint
    *
    * @param state  endpoint state
    */
   void setState(EndpointState state);

   /**
    * Get endpoint type
    *
    * @return   endpoint type
    */
   EndpointType getType();

   /**
    * Set endpoint type
    *
    * @param type  endpoint type
    */
   void setType(EndpointType type);

   /**
    * Get the endpoint implementation bean
    *
    * @return  name of endpoint bean
    */
   String getTargetBeanName();

   /**
    * Set the endpoint implementation bean
    *
    * @param epImpl  name of endpoint bean
    */
   void setTargetBeanName(String epImpl);
   
   /**
    * Use the deployment classloader to load the bean
    *
    * @return   bean loaded
    */
   Class<?> getTargetBeanClass();
      
   /**
    * Set the request handler for this endpoint
    *
    * @param handler  endpoint request handler
    */
   void setRequestHandler(RequestHandler handler);

   /**
    * Get the request handler for this endpoint
    *
    * @return  endpoint request handler
    */
   RequestHandler getRequestHandler();

   /**
    * Get the lifecycle handler for this endpoint
    *
    * @return  endpoint lifecycle handler
    */
   LifecycleHandler getLifecycleHandler();

   /**
    * Set the lifecycle handler for this endpoint
    *
    * @param handler  endpoint lifecycle handler
    */
   void setLifecycleHandler(LifecycleHandler handler);

   /**
    * Get the endpoint bean invoker
    *
    * @return   endpoint invoker
    */
   InvocationHandler getInvocationHandler();

   /**
    * Set the endpoint bean invoker
    *
    * @param invoker  endpoint invoker
    */
   void setInvocationHandler(InvocationHandler invoker);

   /**
    * Get the endpoint metrics for this endpoint
    *
    * @return  endpoint metrics
    */
   EndpointMetrics getEndpointMetrics();

   /**
    * Set the endpoint metrics for this endpoint
    *
    * @param metrics  endpoint metrics
    */
   void setEndpointMetrics(EndpointMetrics metrics);
   
   /**
    * Get the record processors configured for this endpoint
    *
    * @return   set of endpoint record processors
    */
   List<RecordProcessor> getRecordProcessors();
   
   /**
    * Set the record processors for this endpoint
    *
    * @param recordProcessors   set of endpoint record processors
    */
   void setRecordProcessors(List<RecordProcessor> recordProcessors);
   
   /**
    * Ask configured processors for processing of the given record
    *
    * @param record   record to process
    */
   void processRecord(Record record);
   
   /**
    * Get endpoint address
    *
    * @return   endpoint address
    */
   String getAddress();

   /**
    * Set endpoint address
    *
    * @param address   endpoint address
    */
   void setAddress(String address);
   
   /**
    * Get security domain context
    *
    * @return   security domain context
    */
   SecurityDomainContext getSecurityDomainContext();
   
   /**
    * Set security domain context
    *
    * @param context  security domain context
    */
   void setSecurityDomainContext(SecurityDomainContext context);

   /**
    * Get instance provider
    *
    * @return  instance provider
    */
   InstanceProvider getInstanceProvider();

   /**
    * Set instance provider
    *
    * @param provider  instance provider
    */
   void setInstanceProvider(InstanceProvider provider);

   /**
    * Get endpoint config
    *
    * @return  endpoint config
    */
   EndpointConfig getEndpointConfig();

   /**
    * Set endpoint config
    * @param config  endpoint config
    */
   void setEndpointConfig(EndpointConfig config);
     
   Map<String, String> getAllConfigsMap();
}
