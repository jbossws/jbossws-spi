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

import java.util.Collections;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;

/**
 * Injections metadata container.
 *
 * @author ropalka@redhat.com
 */
public final class InjectionsMetaData
{
   
   /**
    * Empty list constant.
    */
   private static final Collection<InjectionMetaData> EMPTY_LIST = Collections.emptyList();
   
   /**
    * Injections metadata.
    */
   private final Collection<InjectionMetaData> injections;
   
   /**
    * JNDI context.
    */
   private final Context ctx;
   
   /**
    * Environment context root.
    */
   private final String envCtx;

   /**
    * Constructor.
    * 
    * @param injections injection definitions list
    * @param ctx JNDI context
    */
   public InjectionsMetaData(Collection<InjectionMetaData> injections, Context ctx, String envCtx)
   {
      super();
      
      if (injections == null)
         throw new IllegalArgumentException("injections metadata list cannot be null");
      if (ctx == null)
         throw new IllegalArgumentException("JNDI context cannot be null");
      if (envCtx == null)
         throw new IllegalArgumentException("Environment JNDI context name cannot be null");
      
      this.injections = injections;
      this.ctx = ctx;
      this.envCtx = envCtx;
   }
   
   /**
    * Returns associated JNDI context.
    * @return associated JNDI context
    */
   public Context getContext()
   {
      return this.ctx;
   }
   
   /**
    * Returns JNDI context root.
    * @return JNDI context root
    */
   public String getContextRoot()
   {
      return this.envCtx;
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
         throw new IllegalArgumentException("class cannot be null");
      
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
   
}
