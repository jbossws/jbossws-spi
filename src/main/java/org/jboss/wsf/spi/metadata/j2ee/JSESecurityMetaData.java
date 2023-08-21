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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author darran.lofthouse@jboss.com
 * @since Oct 22, 2006
 */
public class JSESecurityMetaData
{
   // The optional security-constraint/user-data-constraint/transport-guarantee 
   private final String transportGuarantee;
   // The Map for the security-constraint/web-resource-collection elements.
   private final Map<String, JSEResourceCollection> webResources;

   public static class Builder
   {
      private String transportGuarantee;
      // The HashMap for the security-constraint/web-resource-collection elements.
      private HashMap<String, JSEResourceCollection> webResources = new HashMap<String, JSEResourceCollection>();
      
      public JSESecurityMetaData build()
      {
         return new JSESecurityMetaData(transportGuarantee, webResources);
      }
      
      public void addWebResource(final String name, final Collection<String> urlPatterns)
      {
         JSEResourceCollection wrc = new JSEResourceCollection(name, urlPatterns);
         webResources.put(name, wrc);
      }
      
      public void setTransportGuarantee(final String transportGuarantee)
      {
         this.transportGuarantee = transportGuarantee;
      }
   }
   
   protected JSESecurityMetaData(String transportGuarantee, HashMap<String, JSEResourceCollection> webResources)
   {
      this.transportGuarantee = transportGuarantee;
      this.webResources = webResources;
   }

   public Collection<JSEResourceCollection> getWebResources()
   {
      return webResources.values();
   }

   public String getTransportGuarantee()
   {
      return transportGuarantee;
   }

   public static class JSEResourceCollection
   {
      private final String name;
      private final Set<String> urlPatterns;

      public JSEResourceCollection(final String name, Collection<String> urlPatterns)
      {
         this.name = name;
         if (urlPatterns != null && !urlPatterns.isEmpty()) {
            this.urlPatterns = Collections.unmodifiableSet(new HashSet<String>(urlPatterns));
         } else {
            this.urlPatterns = Collections.emptySet();
         }
      }

      public String getName()
      {
         return name;
      }

      public Set<String> getUrlPatterns()
      {
         return urlPatterns;
      }
   }
}
