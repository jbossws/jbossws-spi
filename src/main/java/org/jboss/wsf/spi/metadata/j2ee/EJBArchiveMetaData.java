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
import java.util.Iterator;
import java.util.List;

/**
 * The container independent top level meta data from the jboss.xml and ejb-jar.xml descriptor. 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2006
 */
public class EJBArchiveMetaData
{
   /** ArrayList<BeanMetaData> for the ejbs */
   private final List<EJBMetaData> beans;
   /** The optional JBossWS config-name */
   private final String configName;
   /** The optional JBossWS config-file */
   private final String configFile;
   /** The web context root to use for web services */
   private final String webServiceContextRoot;
   /** The security-domain value assigned to the application */
   private final String securityDomain;
   /** A HashMap<String, String> for webservice description publish locations */
   private final PublishLocationAdapter publishLocationAdapter;
   
   public static class Builder
   {
      private List<EJBMetaData> beans = Collections.emptyList();
      private String configName;
      private String configFile;
      private String webServiceContextRoot;
      private String securityDomain;
      private PublishLocationAdapter publishLocationAdapter;
      
      public EJBArchiveMetaData build()
      {
         return new EJBArchiveMetaData(beans, configName, configFile, webServiceContextRoot, securityDomain,
               publishLocationAdapter);
      }
      
      public List<EJBMetaData> getEnterpriseBeans()
      {
         return beans;
      }
      public void setEnterpriseBeans(List<EJBMetaData> beans)
      {
         this.beans = beans;
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
      public String getWebServiceContextRoot()
      {
         return webServiceContextRoot;
      }
      public void setWebServiceContextRoot(String webServiceContextRoot)
      {
         this.webServiceContextRoot = webServiceContextRoot;
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
      
   }
   
   public EJBArchiveMetaData(List<EJBMetaData> beans, String configName, String configFile,
         String webServiceContextRoot, String securityDomain, PublishLocationAdapter publishLocationAdapter)
   {
      if (beans != null && !beans.isEmpty()) {
         this.beans = Collections.unmodifiableList(beans);
      } else {
         this.beans = Collections.emptyList();
      }
      this.configName = configName;
      this.configFile = configFile;
      this.webServiceContextRoot = webServiceContextRoot;
      this.securityDomain = securityDomain;
      this.publishLocationAdapter = publishLocationAdapter;
   }

   public EJBMetaData getBeanByEjbName(String ejbName)
   {
      for (EJBMetaData beanMetaData : beans)
      {
         if (beanMetaData.getEjbName().equals(ejbName))
         {
            return beanMetaData;
         }
      }
      return null;
   }

   public Iterator<EJBMetaData> getEnterpriseBeans()
   {
      return beans.iterator();
   }

   public String getConfigName()
   {
      return configName;
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public String getWebServiceContextRoot()
   {
      return webServiceContextRoot;
   }

   public String getSecurityDomain()
   {
      return securityDomain;
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
