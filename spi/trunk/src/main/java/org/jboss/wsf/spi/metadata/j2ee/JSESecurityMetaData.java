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
         if (urlPatterns != null) {
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
