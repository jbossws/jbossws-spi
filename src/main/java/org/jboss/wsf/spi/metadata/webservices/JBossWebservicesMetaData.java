/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public final class JBossWebservicesMetaData {

    private final String contextRoot;
    private final String configName;
    private final String configFile;
    private final Map<String, String> properties;
    private final List<JBossPortComponentMetaData> portComponents;
    private final List<JBossWebserviceDescriptionMetaData> webserviceDescriptions;
    private final URL descriptorURL;
    
    public JBossWebservicesMetaData(String contextRoot,
                                    String configName,
                                    String configFile,
                                    URL descriptorURL,
                                    Map<String, String> properties,
                                    List<JBossPortComponentMetaData> portComponents,
                                    List<JBossWebserviceDescriptionMetaData> webserviceDescriptions)
    {
      this.contextRoot = contextRoot;
      this.configName = configName;
      this.configFile = configFile;
      this.descriptorURL = descriptorURL;
      if (properties != null && !properties.isEmpty()) {
         this.properties = Collections.unmodifiableMap(properties);
      } else {
         this.properties = Collections.emptyMap();
      }
      if (portComponents != null && !portComponents.isEmpty()) {
         this.portComponents = Collections.unmodifiableList(portComponents);
      } else {
         this.portComponents = Collections.emptyList();
      }
      if (webserviceDescriptions != null && !webserviceDescriptions.isEmpty()) {
         this.webserviceDescriptions = Collections.unmodifiableList(webserviceDescriptions);
      } else {
         this.webserviceDescriptions = Collections.emptyList();
      }
    }

    public URL getDescriptorURL() {
        return descriptorURL;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public String getConfigName() {
        return configName;
    }

    public String getConfigFile() {
        return configFile;
    }

    public JBossPortComponentMetaData[] getPortComponents() {
        final JBossPortComponentMetaData[] array = new JBossPortComponentMetaData[portComponents.size()];
        portComponents.toArray(array);
        return array;
    }

    public JBossWebserviceDescriptionMetaData[] getWebserviceDescriptions() {
        final JBossWebserviceDescriptionMetaData[] array = new JBossWebserviceDescriptionMetaData[webserviceDescriptions.size()];
        webserviceDescriptions.toArray(array);
        return array;
    }
    
    public String getProperty(String name) {
       return properties.get(name);
    }

    public Map<String, String> getProperties() {
       return properties;
    }

    public static JBossWebservicesMetaData merge(JBossWebservicesMetaData base, JBossWebservicesMetaData override)
    {
       if (base == null) {
          return override;
       }
       if (override == null) {
          return base;
       }
       //properties
       Map<String, String> properties;
       if (base.properties.isEmpty()) {
          properties = override.properties;
       } else if (override.properties.isEmpty()) {
          properties = base.properties;
       } else {
          properties = new HashMap<String, String>();
          properties.putAll(base.properties);
          properties.putAll(override.properties);
          properties = Collections.unmodifiableMap(properties);
       }
       //port-components
       List<JBossPortComponentMetaData> portComponents;
       if (base.portComponents.isEmpty()) {
          portComponents = override.portComponents;
       } else if (override.portComponents.isEmpty()) {
          portComponents = base.portComponents;
       } else {
          Map<String, JBossPortComponentMetaData> portComponentsMap = new HashMap<String, JBossPortComponentMetaData>();
          for (JBossPortComponentMetaData jpcmd : base.portComponents) {
             portComponentsMap.put(jpcmd.getPortComponentName(), jpcmd);
          }
          for (JBossPortComponentMetaData jpcmd : override.portComponents) {
             JBossPortComponentMetaData b = portComponentsMap.get(jpcmd.getPortComponentName());
             if (b != null) {
                portComponentsMap.put(jpcmd.getPortComponentName(), JBossPortComponentMetaData.merge(b, jpcmd));
             } else {
                portComponentsMap.put(jpcmd.getPortComponentName(), jpcmd);
             }
          }
          portComponents = Collections.unmodifiableList(new ArrayList<JBossPortComponentMetaData>(portComponentsMap.values()));
       }
       //webservice-descriptions
       List<JBossWebserviceDescriptionMetaData> webserviceDescriptions;
       if (base.webserviceDescriptions.isEmpty()) {
          webserviceDescriptions = override.webserviceDescriptions;
       } else if (override.webserviceDescriptions.isEmpty()) {
          webserviceDescriptions = base.webserviceDescriptions;
       } else {
          Map<String, JBossWebserviceDescriptionMetaData> webserviceDescriptionsMap = new HashMap<String, JBossWebserviceDescriptionMetaData>();
          for (JBossWebserviceDescriptionMetaData jpcmd : base.webserviceDescriptions) {
             webserviceDescriptionsMap.put(jpcmd.getWebserviceDescriptionName(), jpcmd);
          }
          for (JBossWebserviceDescriptionMetaData jpcmd : override.webserviceDescriptions) {
             JBossWebserviceDescriptionMetaData b = webserviceDescriptionsMap.get(jpcmd.getWebserviceDescriptionName());
             if (b != null) {
                webserviceDescriptionsMap.put(jpcmd.getWebserviceDescriptionName(), JBossWebserviceDescriptionMetaData.merge(b, jpcmd));
             } else {
                webserviceDescriptionsMap.put(jpcmd.getWebserviceDescriptionName(), jpcmd);
             }
          }
          webserviceDescriptions = Collections.unmodifiableList(new ArrayList<JBossWebserviceDescriptionMetaData>(webserviceDescriptionsMap.values()));
       }
       
       return new JBossWebservicesMetaData(override.contextRoot != null ? override.contextRoot : base.contextRoot,
             override.configName != null ? override.configName : base.configName,
             override.configFile != null ? override.configFile : base.configFile,
             override.descriptorURL != null ? override.descriptorURL : base.descriptorURL,
             properties, portComponents, webserviceDescriptions);
    }
    
}
