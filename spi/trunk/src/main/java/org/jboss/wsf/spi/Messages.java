/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
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
package org.jboss.wsf.spi;

import java.io.IOException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.ws.WebServiceException;

import org.jboss.logging.Cause;
import org.jboss.logging.Message;
import org.jboss.logging.MessageBundle;

/**
 * JBossWS API exception messages
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
