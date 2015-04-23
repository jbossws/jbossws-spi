/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
