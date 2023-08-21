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

package org.jboss.wsf.spi.metadata.webservices;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public final class JBossPortComponentMetaData {

   private final String ejbName;
   private final String portComponentName;
   private final String portComponentURI;
   private final String authMethod;
   private final String transportGuarantee;
   private final Boolean secureWSDLAccess;
   private final String realmName;

   public JBossPortComponentMetaData(String ejbName, String portComponentName, String portComponentURI,
         String authMethod, String transportGuarantee, Boolean secureWSDLAccess)
   {
      this(ejbName, portComponentName, portComponentURI, authMethod, null, transportGuarantee, secureWSDLAccess);
   }
   
   public JBossPortComponentMetaData(String ejbName, String portComponentName, String portComponentURI,
         String authMethod, String realmName, String transportGuarantee, Boolean secureWSDLAccess)
   {
      this.ejbName = ejbName;
      this.portComponentName = portComponentName;
      this.portComponentURI = portComponentURI;
      this.authMethod = authMethod;
      this.realmName = realmName;
      this.transportGuarantee = transportGuarantee;
      this.secureWSDLAccess = secureWSDLAccess;
   }

    public String getEjbName() {
        return ejbName;
    }

    public String getPortComponentName() {
        return portComponentName;
    }

    public String getPortComponentURI() {
        return portComponentURI;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public String getTransportGuarantee() {
        return transportGuarantee;
    }

    public Boolean getSecureWSDLAccess() {
        return secureWSDLAccess;
    }
    
    public String getRealmName () {
       return realmName;
    }

    public static JBossPortComponentMetaData merge(JBossPortComponentMetaData base, JBossPortComponentMetaData override) {
       if (base == null) {
          return override;
       }
       if (override == null) {
          return base;
       }
       return new JBossPortComponentMetaData(override.ejbName != null ? override.ejbName : base.ejbName,
             override.portComponentName != null ? override.portComponentName : base.portComponentName,
             override.portComponentURI != null ? override.portComponentURI : base.portComponentURI,
             override.authMethod != null ? override.authMethod : base.authMethod,
             override.realmName != null ? override.realmName : base.realmName,
             override.transportGuarantee != null ? override.transportGuarantee : base.transportGuarantee,
             override.secureWSDLAccess != null ? override.secureWSDLAccess : base.secureWSDLAccess);
    }
}
