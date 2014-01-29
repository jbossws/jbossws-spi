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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public JBossWebservicesMetaData loadFromVFSRoot(final UnifiedVirtualFile root) {
        JBossWebservicesMetaData webservices = null;

        UnifiedVirtualFile wsdd = root.findChildFailSafe("META-INF/jboss-webservices.xml");

        // Maybe a web application deployment?
        if (null == wsdd) {
           wsdd = root.findChildFailSafe("WEB-INF/jboss-webservices.xml");
        }

        // the descriptor is optional
        if (wsdd != null) {
            return load(wsdd.toURL());
        }

        return webservices;
    }

    public JBossWebservicesMetaData load(final URL wsddUrl) {
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

    public JBossWebservicesMetaData parse(final InputStream is) {
        return parse(is, null);
    }

    public JBossWebservicesMetaData parse(final InputStream is, final URL descriptorURL) {
        try {
            final XMLStreamReader xmlr = StAXUtils.createXMLStreamReader(is);
            return parse(xmlr, descriptorURL);
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }

    public JBossWebservicesMetaData parse(final XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, null);
    }

    private JBossWebservicesMetaData parse(final XMLStreamReader reader, final URL descriptorURL)
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
                    metadata = parseWebservices(reader, nsUri, descriptorURL);
                } else {
                   throw MESSAGES.unexpectedElement(descriptorURL != null ? descriptorURL.toString() : "jboss-webservices.xml", reader.getLocalName());
                }
            }
        }
        return metadata;
    }

    private JBossWebservicesMetaData parseWebservices(final XMLStreamReader reader, final String nsUri, final URL descriptorURL)
            throws XMLStreamException {
        String contextRoot = null;
        String configName = null;
        String configFile = null;
        List<JBossPortComponentMetaData> jpcmds = new LinkedList<JBossPortComponentMetaData>();
        List<JBossWebserviceDescriptionMetaData> jwsdmds = new LinkedList<JBossWebserviceDescriptionMetaData>();
        Map<String, String> props = new HashMap<String, String>();
        while (reader.hasNext()) {
            switch (reader.nextTag()) {
                case XMLStreamConstants.END_ELEMENT: {
                    if (match(reader, nsUri, WEBSERVICES)) {
                        return new JBossWebservicesMetaData(contextRoot, configName, configFile, descriptorURL, props, jpcmds, jwsdmds);
                    } else {
                        throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
                case XMLStreamConstants.START_ELEMENT: {
                    if (match(reader, nsUri, CONTEXT_ROOT)) {
                        contextRoot = getElementText(reader);
                    } else if (match(reader, nsUri, CONFIG_NAME)) {
                        configName = getElementText(reader);
                    } else if (match(reader, nsUri, CONFIG_FILE)) {
                        configFile = getElementText(reader);
                    } else if (match(reader, nsUri, PROPERTY)) {
                       parseProperty(reader, nsUri, props);
                    } else if (match(reader, nsUri, PORT_COMPONENT)) {
                        jpcmds.add(parsePortComponent(reader, nsUri));
                    } else if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                       jwsdmds.add(parseWebserviceDescription(reader, nsUri));
                    } else {
                        throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
            }
        }
        throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
    }

    private JBossPortComponentMetaData parsePortComponent(XMLStreamReader reader, String nsUri) throws XMLStreamException {
        String ejbName = null;
        String portComponentName = null;
        String portComponentURI = null;
        String authMethod = null;
        String transportGuarantee = null;
        Boolean secureWsdlAccess = null;
        while (reader.hasNext()) {
            switch (reader.nextTag()) {
                case XMLStreamConstants.END_ELEMENT: {
                    if (match(reader, nsUri, PORT_COMPONENT)) {
                        return new JBossPortComponentMetaData(ejbName, portComponentName, portComponentURI,
                              authMethod, transportGuarantee, secureWsdlAccess);
                    } else {
                        throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
                case XMLStreamConstants.START_ELEMENT: {
                    if (match(reader, nsUri, EJB_NAME)) {
                        ejbName = getElementText(reader);
                    } else if (match(reader, nsUri, PORT_COMPONENT_NAME)) {
                        portComponentName = getElementText(reader);
                    } else if (match(reader, nsUri, PORT_COMPONENT_URI)) {
                        portComponentURI = getElementText(reader);
                    } else if (match(reader, nsUri, AUTH_METHOD)) {
                        authMethod = getElementText(reader);
                    } else if (match(reader, nsUri, TRANSPORT_GUARANTEE)) {
                        transportGuarantee = getElementText(reader);
                    } else if (match(reader, nsUri, SECURE_WSDL_ACCESS)) {
                        secureWsdlAccess = elementAsBoolean(reader);
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
        String webserviceDescriptionName = null;
        String wsdlPublishLocation = null;
        while (reader.hasNext()) {
            switch (reader.nextTag()) {
                case XMLStreamConstants.END_ELEMENT: {
                    if (match(reader, nsUri, WEBSERVICE_DESCRIPTION)) {
                        return new JBossWebserviceDescriptionMetaData(webserviceDescriptionName, wsdlPublishLocation);
                    } else {
                        throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
                case XMLStreamConstants.START_ELEMENT: {
                    if (match(reader, nsUri, WEBSERVICE_DESCRIPTION_NAME)) {
                        webserviceDescriptionName = getElementText(reader);
                    } else if (match(reader, nsUri, WSDL_PUBLISH_LOCATION)) {
                        wsdlPublishLocation = getElementText(reader);
                    } else {
                        throw MESSAGES.unexpectedElement(getDescriptorForLogs(), reader.getLocalName());
                    }
                }
            }
        }
        throw MESSAGES.reachedEndOfXMLDocUnexpectedly(getDescriptorForLogs());
    }
    
    private void parseProperty(XMLStreamReader reader, String nsUri, Map<String, String> map) throws XMLStreamException
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
                   map.put(name, value);
                   return;
                }
                else
                {
                   throw MESSAGES.unexpectedEndTag(getDescriptorForLogs(), reader.getLocalName());
                }
             }
             case XMLStreamConstants.START_ELEMENT : {
                if (match(reader, nsUri, NAME)) {
                   name = getElementText(reader);
                }
                else if (match(reader, nsUri, VALUE)) {
                   value = getElementText(reader);
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
    
    protected String getElementText(XMLStreamReader reader) throws XMLStreamException {
       return elementAsString(reader);
    }
    
    private String getDescriptorForLogs() {
       return descriptorURL != null ? descriptorURL.toString() : "jboss-webservices.xml";
    }

}
