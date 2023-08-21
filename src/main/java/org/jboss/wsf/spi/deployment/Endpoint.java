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
package org.jboss.wsf.spi.deployment;

import java.util.List;

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
 * @since 20-Apr-2007 
 */
public interface Endpoint extends Extensible
{

   static final String SEPID_DOMAIN = "jboss.ws";
   static final String SEPID_PROPERTY_CONTEXT = "context";
   static final String SEPID_PROPERTY_ENDPOINT = "endpoint";

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
}
