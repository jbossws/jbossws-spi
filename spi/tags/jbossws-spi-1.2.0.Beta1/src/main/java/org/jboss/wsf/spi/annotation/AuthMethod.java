/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.annotation;

/**
 * The authMethod is used to configure the authentication mechanism for the web service. 
 * As a prerequisite to gaining access to any web service which are protected by an 
 * authorization constraint, a user must have authenticated using the configured mechanism.
 * 
 * @author ropalka@redhat.com
 */
public final class AuthMethod
{
   
   /**
    * Basic authentication.
    */
   public static final String BASIC = "BASIC";
   /**
    * Client certificate based authentication.
    */
   public static final String CLIENT_CERT = "CLIENT-CERT";
   
   /**
    * Forbidden constructor.
    */
   private AuthMethod()
   {
      super();
   }

   /**
    * Returns string representing correct auth method value.
    * @param s string to convert, both lowercased and uppercased values are accepted
    * @return correct auth method value
    * @throws IllegalArgumentException if <b>s</b> is <b>null</b> or it contains unknown value.
    */
   public static String valueOf(final String s)
   {
      if (s != null)
      {
         if (s.equals(""))
         {
            return s;
         }
         if (s.equals(AuthMethod.BASIC))
         {
            return AuthMethod.BASIC;
         }
         if (s.equals(AuthMethod.CLIENT_CERT))
         {
            return AuthMethod.CLIENT_CERT;
         }
      }
      
      throw new IllegalArgumentException("Illegal auth method value: " + s);
   }
   
}
