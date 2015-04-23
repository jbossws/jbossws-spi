/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

}
