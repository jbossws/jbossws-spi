/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata.injection;

import java.lang.annotation.Annotation;
import java.util.ResourceBundle;
import org.jboss.ws.api.util.BundleUtils;
import java.util.Collections;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * Injections metadata container.
 *
 * @author <a href="mailto:richard.opalka@jboss.org">Richard Opalka</a>
 * @see org.jboss.wsf.spi.metadata.injection.InjectionMetaData
 * @see org.jboss.wsf.spi.metadata.injection.ReferenceResolver
 */
public final class InjectionsMetaData
{
   private static final ResourceBundle bundle = BundleUtils.getBundle(InjectionsMetaData.class);
   
   /**
    * Empty list constant.
    */
   private static final Collection<InjectionMetaData> EMPTY_LIST = Collections.emptyList();
   
   /**
    * Injections metadata.
    */
   private final Collection<InjectionMetaData> injections;
   
   /**
    * Reference resolvers.
    */
   private final Map<Class<? extends Annotation>, ReferenceResolver> referenceResolvers;
   
   /**
    * Constructor.
    * 
    * @param injections injection definitions list
    * @param resolvers reference resolvers
    */
   public InjectionsMetaData(Collection<InjectionMetaData> injections, Map<Class<? extends Annotation>, ReferenceResolver> resolvers)
   {
      super();
      
      if (injections == null)
         throw new IllegalArgumentException(BundleUtils.getMessage(bundle, "INJECTIONS_METADATA_LIST_CANNOT_BE_NULL"));
      if ((resolvers == null) || (resolvers.size() == 0))
         throw new IllegalArgumentException(BundleUtils.getMessage(bundle, "REFERENCE_RESOLVERS_LIST_CANNOT_BE_NULL"));
      
      this.injections = injections;
      this.referenceResolvers = resolvers;
   }
   
   /**
    * Returns all descriptor driven injections metadata for particular class.
    * 
    * @param clazz class to return injection definitions for
    * @return list of descriptor driven injections to be performed.
    */
   public Collection<InjectionMetaData> getInjectionsMetaData(Class<?> clazz)
   {
      if (clazz == null)
         throw new IllegalArgumentException(BundleUtils.getMessage(bundle, "CLASS_CANNOT_BE_NULL"));
      
      if (this.injections.size() == 0)
         return EMPTY_LIST;
      
      Collection<InjectionMetaData> retVal = null;
      
      for (InjectionMetaData injectionMD : injections)
      {
         if (clazz.getName().equals(injectionMD.getTargetClass()))
         {
            if (injectionMD.isEnvEntryValueSpecified())
            {
               if (retVal == null)
               {
                  retVal = new LinkedList<InjectionMetaData>();
               }
                  
               retVal.add(injectionMD);
            }
         }
      }
      
      return (retVal == null) ? EMPTY_LIST : retVal;
   }
   
   /**
    * Returns reference resolver for annotation.
    * 
    * @param annotation to find resolver for
    * @return reference resolver
    */
   public ReferenceResolver getResolver(final Class<? extends Annotation> annotation)
   {
      final ReferenceResolver resolver = this.referenceResolvers.get(annotation);
      if (resolver == null)
      {
         throw new IllegalArgumentException(BundleUtils.getMessage(bundle, "NO_REGISTERED_REFERENCE_RESOLVER_FOR",  annotation));
      }
      
      return resolver;
   }
   
}
