/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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

package org.jboss.wsf.spi.metadata.webservices;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static org.jboss.wsf.spi.Messages.MESSAGES;
import static org.jboss.wsf.spi.metadata.ParserConstants.AUTH_METHOD;
import static org.jboss.wsf.spi.metadata.ParserConstants.CONFIG_FILE;
import static org.jboss.wsf.spi.metadata.ParserConstants.CONFIG_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.CONTEXT_ROOT;
import static org.jboss.wsf.spi.metadata.ParserConstants.EJB_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.JBOSSEE_NS;
import static org.jboss.wsf.spi.metadata.ParserConstants.NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.PORT_COMPONENT;
import static org.jboss.wsf.spi.metadata.ParserConstants.PORT_COMPONENT_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.PORT_COMPONENT_URI;
import static org.jboss.wsf.spi.metadata.ParserConstants.PROPERTY;
import static org.jboss.wsf.spi.metadata.ParserConstants.SECURE_WSDL_ACCESS;
import static org.jboss.wsf.spi.metadata.ParserConstants.TRANSPORT_GUARANTEE;
import static org.jboss.wsf.spi.metadata.ParserConstants.VALUE;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICES;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICE_DESCRIPTION;
import static org.jboss.wsf.spi.metadata.ParserConstants.WEBSERVICE_DESCRIPTION_NAME;
import static org.jboss.wsf.spi.metadata.ParserConstants.WSDL_PUBLISH_LOCATION;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsBoolean;
import static org.jboss.wsf.spi.util.StAXUtils.elementAsString;
import static org.jboss.wsf.spi.util.StAXUtils.match;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.ws.WebServiceException;

import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.util.StAXUtils;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public class JBossWebservicesFactory {

    // The URL to the jboss-webservices.xml descriptor
    private URL descriptorURL;

    public JBossWebservicesFactory(final URL descriptorURL) {
        this.descriptorURL = descriptorURL;
    }

    public URL getDescriptorURL() {
        return descriptorURL;
    }

    /**
     * Load jboss-webservices.xml from <code>META-INF/jboss-webservices.xml</code> or <code>WEB-INF/jboss-webservices.xml</code>
     * .
     * 
     * @param root virtual file root
     * @return JBossWebservicesMetaData or <code>null</code> if it cannot be found
     */
    public static JBossWebservicesMetaData loadFromVFSRoot(final UnifiedVirtualFile root) {
        JBossWebservicesMetaData webservices = null;

        UnifiedVirtualFile wsdd = null;
        try {
            wsdd = root.findChild("META-INF/jboss-webservices.xml");
        } catch (IOException e) {
            //
        }

        // Maybe a web application deployment?
        if (null == wsdd) {
            try {
                wsdd = root.findChild("WEB-INF/jboss-webservices.xml");
            } catch (IOException e) {
                //
            }
        }

        // the descriptor is optional
        if (wsdd != null) {
            return load(wsdd.toURL());
        }

        return webservices;
    }

    public static JBossWebservicesMetaData load(final URL wsddUrl) {
        InputStream is = null;
        try {
            is = wsddUrl.openStream();
            XMLStreamReader xmlr = StAXUtils.createXMLStreamReader(is);
            return parse(xmlr, wsddUrl);
        } catch (Exception e) {
            throw MESSAGES.failedToUnmarshall(e, wsddUrl);
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            } // ignore
        }
    }

    public static JBossWebservicesMetaData parse(final InputStream is) {
        return parse(is, null);
    }

    public static JBossWebservicesMetaData parse(final InputStream is, final URL descriptorURL) {
        try {
            final XMLStreamReader xmlr = StAXUtils.createXMLStreamReader(is);
            return parse(xmlr, descriptorURL);
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }

    public static JBossWebservicesMetaData parse(final XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, null);
    }

    private static JBossWebservicesMetaData parse(final XMLStreamReader reader, final URL descriptorURL)
            throws XMLStreamException {
        int iterate;
        try {
            iterate = reader.nextTag();
        } catch (XMLStreamException e) {
            // skip non-tag elements
            iterate = reader.nextTag();
        }
        JBossWebservicesMetaData metadata = null;
        switch (iterate) {
            case END_ELEMENT: {
                // we're done
                break;
            }
            case START_ELEMENT: {

                if (match(reader, JBOSSEE_NS, WEBSERVICES)) {
                    String nsUri = reader.getNamespaceURI();
                    JBossWebservicesFactory factory = new JBossWebservicesFactory(descriptorURL);
                    metadata = factory.parseWebservices(reader, nsUri, descriptorURL);
                } else {
                   throw MESSAGES.unexpectedElement(descriptorURL != null ? descriptorURL.toString() : "jboss-webservices.xml", reader.getLocalName());
                }
            }
        }
        return metadata;
    }

    private JBossWebservicesMetaData parseWebservices(final XMLStreamReader reader, final String nsUri, final URL descriptorURL)
            throws XMLStreamException {
        JBossWebservicesMetaData metadata = new JBossWebservicesMetaData(descriptorURL);
        while (reader.hasNext()) {
            switch (reader.nextTag()) {
                case XMLStreamConstants.END_ELEMENT: {
                    if (match(reader, nsUri, WEBSERVICES)) {
                        return metadata;
                    } else {
                        throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
                case XMLStreamConstants.START_ELEMENT: {
                    if (match(reader, nsUri, CONTEXT_ROOT)) {
                        metadata.setContextRoot(elementAsString(reader));
                    } else if (match(reader, nsUri, CONFIG_NAME)) {
                        metadata.setConfigName(elementAsString(reader));
                    } else if (match(reader, nsUri, CONFIG_FILE)) {
                        metadata.setConfigFile(elementAsString(reader));
                    } else if (match(reader, nsUri, PROPERTY)) {
                       parseProperty(reader, nsUri, metadata);
                    } else if (match(reader, nsUri, PORT_COMPONENT)) {
                        metadata.addPortComponent(parsePortComponent(reader, nsUri));
                    } else if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                        metadata.addWebserviceDescription(parseWebserviceDescription(reader, nsUri));
                    } else {
                        throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
            }
        }
        throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
    }

    private JBossPortComponentMetaData parsePortComponent(XMLStreamReader reader, String nsUri) throws XMLStreamException {
        JBossPortComponentMetaData pc = new JBossPortComponentMetaData();
        while (reader.hasNext()) {
            switch (reader.nextTag()) {
                case XMLStreamConstants.END_ELEMENT: {
                    if (match(reader, nsUri, PORT_COMPONENT)) {
                        return pc;
                    } else {
                        throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
                case XMLStreamConstants.START_ELEMENT: {
                    if (match(reader, nsUri, EJB_NAME)) {
                        pc.setEjbName(elementAsString(reader));
                    } else if (match(reader, nsUri, PORT_COMPONENT_NAME)) {
                        pc.setPortComponentName(elementAsString(reader));
                    } else if (match(reader, nsUri, PORT_COMPONENT_URI)) {
                        pc.setPortComponentURI(elementAsString(reader));
                    } else if (match(reader, nsUri, AUTH_METHOD)) {
                        pc.setAuthMethod(elementAsString(reader));
                    } else if (match(reader, nsUri, TRANSPORT_GUARANTEE)) {
                        pc.setTransportGuarantee(elementAsString(reader));
                    } else if (match(reader, nsUri, SECURE_WSDL_ACCESS)) {
                        pc.setSecureWSDLAccess(elementAsBoolean(reader));
                    } else {
                        throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
            }
        }
        throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
    }

    private JBossWebserviceDescriptionMetaData parseWebserviceDescription(XMLStreamReader reader, String nsUri)
            throws XMLStreamException {
        JBossWebserviceDescriptionMetaData description = new JBossWebserviceDescriptionMetaData();
        while (reader.hasNext()) {
            switch (reader.nextTag()) {
                case XMLStreamConstants.END_ELEMENT: {
                    if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                        return description;
                    } else {
                        throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
                case XMLStreamConstants.START_ELEMENT: {
                    if (match(reader, nsUri, WEBSERVICE_DESCRIPTION_NAME)) {
                        description.setWebserviceDescriptionName(elementAsString(reader));
                    } else if (match(reader, nsUri, WSDL_PUBLISH_LOCATION)) {
                        description.setWsdlPublishLocation(elementAsString(reader));
                    } else {
                        throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
            }
        }
        throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
    }
    
    private void parseProperty(XMLStreamReader reader, String nsUri, JBossWebservicesMetaData metadata) throws XMLStreamException
    {
       String name = null;
       String value = null;
       while (reader.hasNext())
       {
          switch (reader.nextTag())
          {
             case XMLStreamConstants.END_ELEMENT : {
                if (match(reader, nsUri, PROPERTY))
                {
                   if (name == null)
                   {
                      throw MESSAGES.couldNotGetPropertyName(getDescriptorForLogs());
                   }
                   metadata.setProperty(name, value);
                   return;
                }
                else
                {
                   throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                }
             }
             case XMLStreamConstants.START_ELEMENT : {
                if (match(reader, nsUri, NAME)) {
                   name = elementAsString(reader);
                }
                else if (match(reader, nsUri, VALUE)) {
                   value = elementAsString(reader);
                }
                else
                {
                   throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
                }
             }
          }
       }
       throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
    }
    
    private String getDescriptorForLogs() {
       return descriptorURL != null ? descriptorURL.toString() : "jboss-webservices.xml";
    }

}
