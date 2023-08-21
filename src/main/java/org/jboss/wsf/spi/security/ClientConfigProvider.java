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
package org.jboss.wsf.spi.security;

import org.jboss.wsf.spi.metadata.config.ClientConfig;

import jakarta.xml.ws.BindingProvider;

/**
 * Interface to enable configuration of authentication properties on the client side.
 * @author dvilkola@redhat.com
 */
public interface ClientConfigProvider {

    String CLIENT_PROVIDER_CONFIGURED = "clientProviderConfigured";
    String CLIENT_USERNAME = "clientUsername";
    String CLIENT_PASSWORD = "clientPassword";
    String CLIENT_HTTP_MECHANISM = "clientHttpMechanism";
    String CLIENT_WS_SECURITY_TYPE = "clientWSSecurityType";
    String CLIENT_SSL_CONTEXT = "clientSSLContext";

    ClientConfig configure(ClientConfig config, BindingProvider bindingProvider) throws ClientConfigException;
}
