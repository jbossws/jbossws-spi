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
package org.jboss.wsf.spi.invocation;

import java.util.Map;

/**
 * Wraps AS7 NamespaceContextSelector logic to allow carrying its
 * contents for invocation served using thread pools.
 *
 * @author alessio.soldano@jboss.org
 * @since 03-Feb-2012
 *
 */
public interface NamespaceContextSelectorWrapper
{
   public void storeCurrentThreadSelector(Map<String, Object> map);
   
   public void setCurrentThreadSelector(Map<String, Object> map);
   
   public void clearCurrentThreadSelector(Map<String, Object> map);
}
