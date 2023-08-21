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
    * @param config   Registers a config in the store
    */
   void register(T config);
   
   /**
    * Unregisters a config in the store; the runtime will be affected the first time the store is reloaded.
    * 
    * @param config  Unregisters a config in the store
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
    * @param config     Sets a wrapper config
    * @param reload     Whether to reload the store after having set the wrapper or not
    */
   void setWrapperConfig(T config, boolean reload);
   
   /**
    * Returns current wrapper config
    * 
    * @return     Returns current wrapper config
    */
   T getWrapperConfig();
   
   /**
    * Retrieves a config by name from the loaded collection
    * 
    * @param name    Retrieves a config by name
    * @return     The matching config
    */
   T getConfig(String name);
   
   /**
    * Returns the loaded config collection
    * 
    * @return   the config collection
    */
   Collection<T> getConfigs();
}
