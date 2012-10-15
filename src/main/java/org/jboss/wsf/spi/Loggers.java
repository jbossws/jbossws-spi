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

import static org.jboss.logging.Logger.Level.WARN;
import static org.jboss.logging.Logger.Level.ERROR;

import java.net.URL;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Cause;
import org.jboss.logging.LogMessage;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.Message;
import org.jboss.logging.MessageLogger;

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
    
}
