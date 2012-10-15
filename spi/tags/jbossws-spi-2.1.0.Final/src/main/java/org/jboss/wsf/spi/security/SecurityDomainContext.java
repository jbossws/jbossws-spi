/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.security;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;

/**
 * A container independent security domain related facility
 *
 * @author alessio.soldano@jboss.com
 * @since 13-May-2011
 */
public interface SecurityDomainContext
{
   public String getSecurityDomain();
   
   // Authentication methods
   
   /**
    * The isValid method is invoked to see if a user identity and associated
    * credentials as known in the operational environment are valid proof of the
    * user identity.
    * 
    * @param principal - the user identity in the operation environment 
    * @param credential - the proof of user identity as known in the
    * operation environment
    * @param activeSubject - the Subject which should be populated with the
    * validated Subject contents. A JAAS based implementation would typically
    * populate the activeSubject with the LoginContext.login result.
    * @return true if the principal, credential pair is valid, false otherwise.
    * 
    */
   public boolean isValid(Principal principal, Object credential, Subject activeSubject);
   
   
   // Authorization methods
   
   /**
    * Validates the application domain roles to which the operational
    * environment Principal belongs.
    * 
    * @param principal the caller principal as known in the operation environment.
    * @param roles The Set<Principal> for the application domain roles that the
    * principal is to be validated against.
    * @return true if the principal has at least one of the roles in the roles set,
    *        false otherwise.
    */
   public boolean doesUserHaveRole(Principal principal, Set<Principal> roles);
   
   
   /**
    * Return the set of domain roles the principal has been assigned.
    * 
    * @return The Set<Principal> for the application domain roles that the principal has been assigned.
    */
   public Set<Principal> getUserRoles(Principal principal);
   
   /**
    * Push the provided subject into the current security context; if that's not set yet,
    * also creates a new security context and associates it with the current thread.
    * 
    * @param subject
    * @param principal
    * @param credential
    */
   public void pushSubjectContext(final Subject subject, final Principal principal, final Object credential);
}
