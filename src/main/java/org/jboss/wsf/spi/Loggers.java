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

import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;

import java.net.URL;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

/**
 * JBossWS SPI log messages
 * 
 * @author alessio.soldano@jboss.com
 */
@MessageLogger(projectCode = "JBWS")
public interface Loggers extends BasicLogger
{
    Loggers ROOT_LOGGER = org.jboss.logging.Logger.getMessageLogger(Loggers.class, "org.jboss.ws.spi");
    Loggers METADATA_LOGGER = org.jboss.logging.Logger.getMessageLogger(Loggers.class, "org.jboss.ws.spi.metadata");
    
    @LogMessage(level = WARN)
    @Message(id = 21003, value = "%s element not supported")
    void elementNotSupported(String feature);
    
    @LogMessage(level = ERROR)
    @Message(id = 21005, value = "Cannot get port component name {%s}, we have: %s")
    void cannotGetPortComponentName(String name, Collection<String> pcNames);
    
    @LogMessage(level = WARN)
    @Message(id = 21006, value = "Element in webservices.xml not namespace qualified: %s")
    void webservicesXmlElementNotNamespaceQualified(QName name);
    
    @LogMessage(level = WARN)
    @Message(id = 21010, value = "Multiple matching port-component-ref: sei={%s} port={%s}")
    void multipleMatchingPortComponentRef(String seiName, QName portName);
    
    @LogMessage(level = ERROR)
    @Message(id = 21017, value = "Cannot get children for resource %s")
    void cannotGetChildrenForResource(@Cause Throwable cause, URL url);
    
    @LogMessage(level = ERROR)
    @Message(id = 21018, value = "Cannot get name for resource %s")
    void cannotGetNameForResource(@Cause Throwable cause, URL url);
    
    @LogMessage(level = TRACE)
    @Message(id = 21019, value = "Cannot get URL for %s")
    void cannotGetURLFor(String path);
    
}
