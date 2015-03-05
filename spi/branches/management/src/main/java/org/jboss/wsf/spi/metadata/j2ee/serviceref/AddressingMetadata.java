/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata.j2ee.serviceref;

import static org.jboss.wsf.spi.Messages.MESSAGES;

public final class AddressingMetadata
{
   private final boolean annotationSpecified;
   private final boolean enabled;
   private final boolean required;
   private final String responses;

   public AddressingMetadata(boolean annotationSpecified, boolean enabled, boolean required, String responses)
   {
      this.annotationSpecified = annotationSpecified;
      this.enabled = enabled;
      this.required = required;
      if (!"ANONYMOUS".equals(responses) && !"NON_ANONYMOUS".equals(responses) && !"ALL".equals(responses))
         throw MESSAGES.unsupportedAddressingResponseType(responses);
      this.responses = responses;
   }

   public AddressingMetadata(boolean annotationSpecified, boolean enabled, boolean required)
   {
      this.annotationSpecified = annotationSpecified;
      this.enabled = enabled;
      this.required = required;
      this.responses = "ALL";
   }

   public boolean isAnnotationSpecified()
   {
      return annotationSpecified;
   }

   public boolean isEnabled()
   {
      return enabled;
   }

   public boolean isRequired()
   {
      return required;
   }

   public String getResponses()
   {
      return responses;
   }
}
