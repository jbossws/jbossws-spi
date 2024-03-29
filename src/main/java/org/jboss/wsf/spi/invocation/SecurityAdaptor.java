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

import java.security.Principal;

/**
 * A container independent security adaptor
 *
 * @author Thomas.Diesler@jboss.org
 * @since 10-May-2005
 */
@Deprecated
public interface SecurityAdaptor 
{
   Principal getPrincipal();
   void setPrincipal(Principal pricipal);
   
   Object getCredential();
   void setCredential(Object credential);
}
