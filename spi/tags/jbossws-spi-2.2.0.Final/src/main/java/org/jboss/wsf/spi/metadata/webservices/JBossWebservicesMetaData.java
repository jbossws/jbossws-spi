/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class JBossWebservicesMetaData {

    private String contextRoot;

    private String configName;

    private String configFile;
    
    private Map<String, String> properties = new HashMap<String, String>();

    private List<JBossPortComponentMetaData> portComponents = new LinkedList<JBossPortComponentMetaData>();

    private List<JBossWebserviceDescriptionMetaData> webserviceDescriptions = new LinkedList<JBossWebserviceDescriptionMetaData>();

    private URL descriptorURL;

    public JBossWebservicesMetaData(final URL descriptorURL) {
        this.descriptorURL = descriptorURL;
    }

    public URL getDescriptorURL() {
        return descriptorURL;
    }

    public void setContextRoot(final String contextRoot) {
        this.contextRoot = contextRoot;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setConfigName(final String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigFile(final String configFile) {
        this.configFile = configFile;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void addPortComponent(final JBossPortComponentMetaData portComponent) {
        portComponents.add(portComponent);
    }

    public JBossPortComponentMetaData[] getPortComponents() {
        final JBossPortComponentMetaData[] array = new JBossPortComponentMetaData[portComponents.size()];
        portComponents.toArray(array);
        return array;
    }

    public void addWebserviceDescription(final JBossWebserviceDescriptionMetaData webserviceDescriptionMD) {
        webserviceDescriptions.add(webserviceDescriptionMD);
    }

    public JBossWebserviceDescriptionMetaData[] getWebserviceDescriptions() {
        final JBossWebserviceDescriptionMetaData[] array = new JBossWebserviceDescriptionMetaData[webserviceDescriptions.size()];
        webserviceDescriptions.toArray(array);
        return array;
    }
    
    public void setProperty(String name, String value) {
       properties.put(name, value);
    }

    public String getProperty(String name) {
       return properties.get(name);
    }

    public Map<String, String> getProperties() {
       return properties;
    }

}
