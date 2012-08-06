/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata;

/**
 * Constants for parsing descriptors
 * 
 * @author alessio.soldano@jboss.com
 * @since 30-Nov-2010
 *
 */
public abstract class ParserConstants
{
   
   public static final String JAVAEE_NS = "http://java.sun.com/xml/ns/javaee";
   
   public static final String JBOSSEE_NS = "http://www.jboss.com/xml/ns/javaee";
   
   public static final String J2EE_NS = "http://java.sun.com/xml/ns/j2ee";

   public static final String AUTH_METHOD = "auth-method";

   public static final String CHAIN_PORT_PATTERN = "port-name-pattern";

   public static final String CHAIN_PROTOCOL_BINDING = "protocol-bindings";

   public static final String CHAIN_SERVICE_PATTERN = "service-name-pattern";

   public static final String EJB_NAME = "ejb-name";

   public static final String HANDLER_CHAIN = "handler-chain";

   public static final String HANDLER_CHAINS = "handler-chains";

   public static final String HANDLER = "handler";

   public static final String HANDLER_NAME = "handler-name";

   public static final String HANDLER_CLASS = "handler-class";

   public static final String HANDLER_SOAP_ROLE = "soap-role";

   public static final String HANDLER_SOAP_HEADER = "soap-header";

   public static final String HANDLER_PARAM = "init-param";

   public static final String HANDLER_PARAM_NAME = "param-name";

   public static final String HANDLER_PARAM_VALUE = "param-value";

   public static final String WEBSERVICES = "webservices";

   public static final String WEBSERVICE_DESCRIPTION = "webservice-description";

   public static final String WEBSERVICE_DESCRIPTION_NAME = "webservice-description-name";

   public static final String JAXRPC_MAPPING_FILE = "jaxrpc-mapping-file";

   public static final String WSDL_FILE = "wsdl-file";

   public static final String WSDL_PUBLISH_LOCATION = "wsdl-publish-location";

   public static final String PORT_COMPONENT = "port-component";

   public static final String PORT_COMPONENT_NAME = "port-component-name";

   public static final String PORT_COMPONENT_URI = "port-component-uri";

   public static final String WSDL_SERVICE = "wsdl-service";

   public static final String WSDL_PORT = "wsdl-port";

   public static final String ENABLE_MTOM = "enable-mtom";

   public static final String MTOM_THRESHOLD = "mtom-threshold";

   public static final String ADDRESSING = "addressing";

   public static final String ADDRESSING_RESPONSES = "responses";

   public static final String RESPECT_BINDING = "respect-binding";

   public static final String PROTOCOL_BINDING = "protocol-binding";

   public static final String SERVICE_ENDPOINT_INTERFACE = "service-endpoint-interface";

   public static final String SERVICE_IMPL_BEAN = "service-impl-bean";

   public static final String EJB_LINK = "ejb-link";

   public static final String SERVLET_LINK = "servlet-link";

   public static final String ENABLED = "enabled";

   public static final String REQUIRED = "required";
   
   public static final String JBOSSWS_JAXWS_CONFIG_NS = "urn:jboss:jbossws-jaxws-config:4.0";
   
   public static final String JAXWS_CONFIG = "jaxws-config";
   
   public static final String ENDPOINT_CONFIG = "endpoint-config";
   
   public static final String CLIENT_CONFIG = "client-config";
   
   public static final String CONFIG_NAME = "config-name";
   
   public static final String CONFIG_FILE = "config-file";
   
   public static final String CONTEXT_ROOT = "context-root";
   
   public static final String PRE_HANDLER_CHAINS = "pre-handler-chains";
   
   public static final String POST_HANDLER_CHAINS = "post-handler-chains";
   
   public static final String FEATURE = "feature";
   
   public static final String FEATURE_DATA = "feature-data";

   public static final String FEATURE_NAME = "feature-name";
   
   public static final String PROPERTY = "property";
   
   public static final String PROPERTY_NAME = "property-name";
   
   public static final String PROPERTY_VALUE = "property-value";
   
   public static final String NAME = "name";
   
   public static final String VALUE = "value";
   
   public static final String SECURE_WSDL_ACCESS = "secure-wsdl-access";

   public static final String TRANSPORT_GUARANTEE = "transport-guarantee";
   
}
