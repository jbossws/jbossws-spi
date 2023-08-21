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
package org.jboss.wsf.spi.metadata.j2ee;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The container independent representation of the web.xml and jboss-web.xml deployment descriptors 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2006
 */
public class JSEArchiveMetaData
{
   // The war context root as specified at the jboss-web.xml descriptor level.
   private final String contextRoot;
   // The servlet-mapping <servlet-name, url-pattern> 
   private final Map<String, String> servletMappings;
   // The servlet <servlet-name, servlet-class> 
   private final Map<String, String> servletClassNames;
   // The optional JBossWS config-name 
   private final String configName;
   // The optional JBossWS config-file 
   private final String configFile;
   // The security-domain value assigned to the application 
   private final String securityDomain;
   // A HashMap<String, String> for webservice description publish locations 
   private final PublishLocationAdapter publishLocationAdapter;
   // web.xml security-constraints 
   private final List<JSESecurityMetaData> securityMetaData;
   
   public static class Builder
   {
      private String contextRoot;
      private Map<String, String> servletMappings = new HashMap<String, String>();
      private Map<String, String> servletClassNames = new HashMap<String, String>();
      private String configName;
      private String configFile;
      private String securityDomain;
      private PublishLocationAdapter publishLocationAdapter;
      private List<JSESecurityMetaData> securityMetaData;
      
      public JSEArchiveMetaData build()
      {
         return new JSEArchiveMetaData(contextRoot, servletMappings, servletClassNames, configName, configFile,
               securityDomain, publishLocationAdapter, securityMetaData);
      }
      
      public String getContextRoot()
      {
         return contextRoot;
      }
      public void setContextRoot(String contextRoot)
      {
         this.contextRoot = contextRoot;
      }
      public Map<String, String> getServletMappings()
      {
         return servletMappings;
      }
      public void setServletMappings(Map<String, String> servletMappings)
      {
         this.servletMappings = servletMappings;
      }
      public Map<String, String> getServletClassNames()
      {
         return servletClassNames;
      }
      public void setServletClassNames(Map<String, String> servletClassNames)
      {
         this.servletClassNames = servletClassNames;
      }
      public String getConfigName()
      {
         return configName;
      }
      public void setConfigName(String configName)
      {
         this.configName = configName;
      }
      public String getConfigFile()
      {
         return configFile;
      }
      public void setConfigFile(String configFile)
      {
         this.configFile = configFile;
      }
      public String getSecurityDomain()
      {
         return securityDomain;
      }
      public void setSecurityDomain(String securityDomain)
      {
         this.securityDomain = securityDomain;
      }
      public PublishLocationAdapter getPublishLocationAdapter()
      {
         return publishLocationAdapter;
      }
      public void setPublishLocationAdapter(PublishLocationAdapter publishLocationAdapter)
      {
         this.publishLocationAdapter = publishLocationAdapter;
      }
      public List<JSESecurityMetaData> getSecurityMetaData()
      {
         return securityMetaData;
      }
      public void setSecurityMetaData(List<JSESecurityMetaData> securityMetaData)
      {
         this.securityMetaData = securityMetaData;
      }
   }
   
   protected JSEArchiveMetaData(String contextRoot, Map<String, String> servletMappings,
         Map<String, String> servletClassNames, String configName, String configFile, String securityDomain,
         PublishLocationAdapter publishLocationAdapter, List<JSESecurityMetaData> securityMetaData)
   {
      this.contextRoot = contextRoot;
      if (servletMappings != null && !servletMappings.isEmpty()) {
         this.servletMappings = Collections.unmodifiableMap(servletMappings);
      } else {
         this.servletMappings = Collections.emptyMap();
      }
      if (servletClassNames != null && !servletClassNames.isEmpty()) {
         this.servletClassNames = Collections.unmodifiableMap(servletClassNames);
      } else {
         this.servletClassNames = Collections.emptyMap();
      }
      this.configName = configName;
      this.configFile = configFile;
      this.securityDomain = securityDomain;
      this.publishLocationAdapter = publishLocationAdapter;
      if (securityMetaData != null && !securityMetaData.isEmpty()) {
         this.securityMetaData = Collections.unmodifiableList(securityMetaData);
      } else {
         this.securityMetaData = Collections.emptyList();
      }
   }

   public String getContextRoot()
   {
      return contextRoot;
   }

   public Map<String, String> getServletMappings()
   {
      return servletMappings;
   }

   public Map<String, String> getServletClassNames()
   {
      return servletClassNames;
   }

   public String getConfigName()
   {
      return configName;
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public String getSecurityDomain()
   {
      return securityDomain;
   }

   public List<JSESecurityMetaData> getSecurityMetaData()
   {
      return securityMetaData;
   }

   public String getWsdlPublishLocationByName(String name)
   {
      if (this.publishLocationAdapter != null)
      {
         return this.publishLocationAdapter.getWsdlPublishLocationByName(name);
      }
      
      return null;
   }

}
