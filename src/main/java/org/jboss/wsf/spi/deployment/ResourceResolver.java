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
package org.jboss.wsf.spi.deployment;

import java.io.IOException;
import java.net.URL;

/**
 * A resource resolver
 * 
 * @author alessio.soldano@jboss.com
 * @since 19-Nov-2009
 *  
 */
public interface ResourceResolver
{
   /**
    * Get the URL for a given resource path
    * 
    * @param resourcePath   resource path
    * @return  URL of resource
    * @throws IOException  IO exception
    */
   public URL resolve(String resourcePath) throws IOException;
   
   /**
    * Same as resolve(String resourcePath) except it does not throw exception
    * when resource is not found, simply returns null.
    * 
    * @param resourcePath   resource path
    * @return     URL of resource
    */
   public URL resolveFailSafe(String resourcePath);
   
}
