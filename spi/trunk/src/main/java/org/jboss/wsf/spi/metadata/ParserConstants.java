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

import javax.xml.namespace.QName;

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

   public static final QName QNAME_CHAIN_PORT_PATTERN = new QName(JAVAEE_NS, "port-name-pattern");

   public static final QName QNAME_CHAIN_PROTOCOL_BINDING = new QName(JAVAEE_NS, "protocol-bindings");

   public static final QName QNAME_CHAIN_SERVICE_PATTERN = new QName(JAVAEE_NS, "service-name-pattern");

   public static final QName QNAME_HANDLER_CHAIN = new QName(JAVAEE_NS, "handler-chain");

   public static final QName QNAME_HANDLER_CHAINS = new QName(JAVAEE_NS, "handler-chains");

   public static final QName QNAME_HANDLER = new QName(JAVAEE_NS, "handler");

   public static final QName QNAME_HANDLER_NAME = new QName(JAVAEE_NS, "handler-name");

   public static final QName QNAME_HANDLER_CLASS = new QName(JAVAEE_NS, "handler-class");

   public static final QName QNAME_HANDLER_SOAP_ROLE = new QName(JAVAEE_NS, "soap-role");

   public static final QName QNAME_HANDLER_SOAP_HEADER = new QName(JAVAEE_NS, "soap-header");

   public static final QName QNAME_HANDLER_PARAM = new QName(JAVAEE_NS, "init-param");

   public static final QName QNAME_HANDLER_PARAM_NAME = new QName(JAVAEE_NS, "param-name");

   public static final QName QNAME_HANDLER_PARAM_VALUE = new QName(JAVAEE_NS, "param-value");

   public static final QName QNAME_WEBSERVICES = new QName(JAVAEE_NS, "webservices");

   public static final QName QNAME_WEBSERVICE_DESCRIPTION = new QName(JAVAEE_NS, "webservice-description");

   public static final QName QNAME_WEBSERVICE_DESCRIPTION_NAME = new QName(JAVAEE_NS, "webservice-description-name");

   public static final QName QNAME_JAXRPC_MAPPING_FILE = new QName(JAVAEE_NS, "jaxrpc-mapping-file");

   public static final QName QNAME_WSDL_FILE = new QName(JAVAEE_NS, "wsdl-file");

   public static final QName QNAME_PORT_COMPONENT = new QName(JAVAEE_NS, "port-component");

   public static final QName QNAME_PORT_COMPONENT_NAME = new QName(JAVAEE_NS, "port-component-name");

   public static final QName QNAME_WSDL_SERVICE = new QName(JAVAEE_NS, "wsdl-service");

   public static final QName QNAME_WSDL_PORT = new QName(JAVAEE_NS, "wsdl-port");

   public static final QName QNAME_ENABLE_MTOM = new QName(JAVAEE_NS, "enable-mtom");

   public static final QName QNAME_MTOM_THRESHOLD = new QName(JAVAEE_NS, "mtom-threshold");

   public static final QName QNAME_ADDRESSING = new QName(JAVAEE_NS, "addressing");

   public static final QName QNAME_ADDRESSING_RESPONSES = new QName(JAVAEE_NS, "responses");

   public static final QName QNAME_RESPECT_BINDING = new QName(JAVAEE_NS, "respect-binding");

   public static final QName QNAME_PROTOCOL_BINDING = new QName(JAVAEE_NS, "protocol-binding");

   public static final QName QNAME_SERVICE_ENDPOINT_INTERFACE = new QName(JAVAEE_NS, "service-endpoint-interface");

   public static final QName QNAME_SERVICE_IMPL_BEAN = new QName(JAVAEE_NS, "service-impl-bean");

   public static final QName QNAME_EJB_LINK = new QName(JAVAEE_NS, "ejb-link");

   public static final QName QNAME_SERVLET_LINK = new QName(JAVAEE_NS, "servlet-link");

   public static final QName QNAME_ENABLED = new QName(JAVAEE_NS, "enabled");

   public static final QName QNAME_REQUIRED = new QName(JAVAEE_NS, "required");
}
