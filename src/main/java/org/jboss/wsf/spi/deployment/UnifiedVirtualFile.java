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
import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 *  
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 05-May-2006
 */
public interface UnifiedVirtualFile extends Serializable
{
   UnifiedVirtualFile findChild(String child) throws IOException;
   
   /**
    * Same as findChild(String child) but does not throw any exception
    * on child not found, simply returns null.
    * 
    * @param child   name of child to find
    * @return   found child
    */
   UnifiedVirtualFile findChildFailSafe(String child);
   
   List<UnifiedVirtualFile> getChildren() throws IOException;
   
   String getName();

   URL toURL();
}
