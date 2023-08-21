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
package org.jboss.wsf.spi;

import java.io.IOException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import jakarta.xml.ws.WebServiceException;

import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageBundle;

/**
 * JBossWS SPI exception messages
 * 
 * @author alessio.soldano@jboss.com
 */
@MessageBundle(projectCode = "JBWS")
public interface Messages {

    Messages MESSAGES = org.jboss.logging.Messages.getBundle(Messages.class);
    
    @Message(id = 21000, value = "Unexpected end tag parsing %s: %s")
    IllegalStateException unexpectedEndTag(String descriptor, String tag);
    
    @Message(id = 21001, value = "Unexpected element parsing %s: %s")
    IllegalStateException unexpectedElement(String descriptor, String elem);
    
    @Message(id = 21002, value = "Unexpectedly reached end of XML document: %s")
    IllegalStateException reachedEndOfXMLDocUnexpectedly(String descriptor);
    
    @Message(id = 21004, value = "Failed to unmarshall %s")
    WebServiceException failedToUnmarshall(@Cause Throwable cause, URL url);
    
    @Message(id = 21007, value = "Unsupported port-component addressing response type '%s'. Only ALL, ANONYMOUS or NON_ANONYMOUS strings are allowed.")
    IllegalArgumentException unsupportedAddressingResponseType(String responseType);
    
    @Message(id = 21008, value = "Could not get a property name parsing: %s")
    IllegalStateException couldNotGetPropertyName(String descriptor);

    @Message(id = 21009, value = "Cannot find file %s")
    WebServiceException cannotFindFile(@Cause Throwable cause, String file);
    
    @Message(id = 21011, value = "Cannot match port-component-ref against null service endpoint interface and port QName")
    IllegalArgumentException cannotMatchAgainstNull();
    
    @Message(id = 21012, value = "Invalid handler type %s")
    IllegalArgumentException invalidHandlerType(String type);
    
    @Message(id = 21013, value = "Could not get a feature name parsing: %s")
    IllegalStateException couldNotGetFeatureName(String descriptor);

    @Message(id = 21014, value = "Reading external entities is disabled")
    XMLStreamException readingExternalEntitiesDisabled();

    @Message(id = 21015, value = "Could not parse stream")
    RuntimeException couldNotParseStream(@Cause Throwable cause);

    @Message(id = 21016, value = "Cannot get URL for %s")
    IOException cannotGetURLFor(String path);

}
