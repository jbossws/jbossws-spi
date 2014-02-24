/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.management;

import java.util.Collection;

import org.jboss.wsf.spi.metadata.config.AbstractCommonConfig;


/**
 * A store of client/endpoint config
 *
 * @author alessio.soldano@jboss.com
 * @since 04-Dec-2013
 */
public interface CommonConfigStore<T extends AbstractCommonConfig>
{
   /**
    * Registers a config in the store; the new config will affect runtime the first time the store is reloaded.
    * 
    * @param config
    */
   void register(T config);
   
   /**
    * Unregisters a config in the store; the runtime will be affected the first time the store is reloaded.
    * 
    * @param config
    */
   void unregister(T config);

   /**
    * Reloads the store, which involves iterating over the registered configs and creating a collection that is
    * returned when calling getConfig(..) / getConfigs().
    */
   void reload();
   
   /**
    * Unloads the store, cleaning up the loaded collection.
    */
   void unload();
   
   /**
    * Sets a wrapper config, to be merged with any registered config.
    * 
    * @param config
    * @param reload     Whether to reload the store after having set the wrapper or not
    */
   void setWrapperConfig(T config, boolean reload);
   
   /**
    * Returns current wrapper config
    * 
    * @return
    */
   T getWrapperConfig();
   
   /**
    * Retrieves a config by name from the loaded collection
    * 
    * @param name
    * @return
    */
   T getConfig(String name);
   
   /**
    * Returns the loaded config collection
    * 
    * @return
    */
   Collection<T> getConfigs();
}
