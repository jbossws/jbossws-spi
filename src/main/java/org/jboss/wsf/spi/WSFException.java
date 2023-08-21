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
package org.jboss.wsf.spi;

/**
 * @author Heiko.Braun@jboss.com
 *         Created: Jul 11, 2007
 */
public class WSFException extends RuntimeException
{
   public WSFException()
   {
      super();
   }

   public WSFException(String message)
   {
      super(message);
   }

   public WSFException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public WSFException(Throwable cause)
   {
      super(cause);    
   }

   public static void rethrow(String string, Throwable th)
   {
      if (th instanceof WSFException)
         throw (WSFException)th;

      throw new WSFException(string, th);
   }

   public static void rethrow(Throwable th)
   {
      if (th instanceof WSFException)
         throw (WSFException)th;

      throw new WSFException(th);
   }
}
