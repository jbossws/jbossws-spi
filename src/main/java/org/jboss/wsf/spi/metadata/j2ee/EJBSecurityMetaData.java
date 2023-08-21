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

/**
 * The container independent EJB security meta data class  
 *
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2006
 */
public class EJBSecurityMetaData
{
   private final String authMethod;
   private final String realmName;
   private final String transportGuarantee;
   private final boolean secureWSDLAccess;

   public EJBSecurityMetaData(String authMethod, String transportGuarantee, boolean secureWSDLAccess)
   {
      this(authMethod, null, transportGuarantee, secureWSDLAccess);
   }
   
   public EJBSecurityMetaData(String authMethod, String realmName, String transportGuarantee, boolean secureWSDLAccess)
   {
      this.authMethod = authMethod;
      this.realmName = realmName;
      this.transportGuarantee = transportGuarantee;
      this.secureWSDLAccess = secureWSDLAccess;
   }

   public String getAuthMethod()
   {
      return authMethod;
   }

   public String getTransportGuarantee()
   {
      return transportGuarantee;
   }

   public boolean getSecureWSDLAccess()
   {
      return secureWSDLAccess;
   }
   
   public String getRealmName() 
   {
      return realmName;
   }
}
