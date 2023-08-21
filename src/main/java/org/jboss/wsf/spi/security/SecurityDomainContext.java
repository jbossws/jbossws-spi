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
package org.jboss.wsf.spi.security;

import org.wildfly.security.auth.server.SecurityDomain;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.Callable;

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
   public default SecurityDomain getElytronSecurityDomain() {
      return null;
   }
   
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
    * @param roles The <code>Set&lt;java.security.Principal&gt;</code> for the application domain roles that the
    * principal is to be validated against.
    * @return true if the principal has at least one of the roles in the roles set,
    *        false otherwise.
    */
   public boolean doesUserHaveRole(Principal principal, Set<Principal> roles);
   
   
   /**
    * Return the set of domain roles the principal has been assigned.
    *
    * @param principal principal
    * @return The <code>Set&lt;java.security.Principal&gt;</code> for the application domain roles that the principal has been assigned.
    */
   public Set<Principal> getUserRoles(Principal principal);
   
   /**
    * Push the provided subject into the current security context; if that's not set yet,
    * also creates a new security context and associates it with the current thread.
    * 
    * @param subject       subject
    * @param principal     principal
    * @param credential    credential
    */
   public void pushSubjectContext(final Subject subject, final Principal principal, final Object credential);
   
   /**
    * Cleans up the current association between thread and security context
    * 
    */
   public default void cleanupSubjectContext() {
       //NOOP
   }
   
   
   /**
    * Run action under this security context
    * @param action the action to run
    * @throws Exception if input action fails
    */
   public void runAs(Callable<Void> action) throws Exception;
}
