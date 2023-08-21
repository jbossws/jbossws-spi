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
