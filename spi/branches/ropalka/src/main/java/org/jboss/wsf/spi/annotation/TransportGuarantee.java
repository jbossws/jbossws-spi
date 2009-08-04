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
 * The transportGuarantee specifies that the communication
 * between client and server should be NONE, INTEGRAL, or
 * CONFIDENTIAL. NONE means that the application does not require any
 * transport guarantees. A value of INTEGRAL means that the application
 * requires that the data sent between the client and server be sent in
 * such a way that it can't be changed in transit. CONFIDENTIAL means
 * that the application requires that the data be transmitted in a
 * fashion that prevents other entities from observing the contents of
 * the transmission. In most cases, the presence of the INTEGRAL or
 * CONFIDENTIAL flag will indicate that the use of SSL is required.
 * 
 * @author ropalka@redhat.com
 */
public final class TransportGuarantee
{
   
   /**
    * Application does not require any transport guarantees.
    */
   public static final String NONE = "NONE";
   /**
    * Application requires that the data sent between the client and
    * server be sent in such a way that it can't be changed in transit.
    */
   public static final String INTEGRAL = "INTEGRAL";
   /**
    * Application requires that the data be transmitted in a fashion that
    * prevents other entities from observing the contents of the transmission.
    */
   public static final String CONFIDENTIAL = "CONFIDENTIAL";

   /**
    * Forbidden constructor.
    */
   private TransportGuarantee()
   {
      super();
   }
   
   /**
    * Returns string representing correct transport guarantee value.
    * @param s string to convert, both lowercased and uppercased values are accepted
    * @return correct transport guarantee value
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
         if (s.equals(TransportGuarantee.NONE))
         {
            return TransportGuarantee.NONE;
         }
         if (s.equals(TransportGuarantee.INTEGRAL))
         {
            return TransportGuarantee.INTEGRAL;
         }
         if (s.equals(TransportGuarantee.CONFIDENTIAL))
         {
            return TransportGuarantee.CONFIDENTIAL;
         }
      }
      
      throw new IllegalArgumentException("Illegal transport guarantee value: " + s);
   }
   
}
