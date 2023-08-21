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

import java.io.Serializable;

/**
 * @author Thomas.Diesler@jboss.org
 * @author alessio.soldano@jboss.com
 * @since 06-May-2004
 */
public class UnifiedStubPropertyMetaData implements Serializable
{
   private static final long serialVersionUID = -4584869798181720949L;
   // The required <prop-name> element
   private final String propName;
   // The required <prop-value> element
   private final String propValue;
   
   public UnifiedStubPropertyMetaData(String propName, String propValue)
   {
      this.propName = propName;
      this.propValue = propValue;
   }

   public String getPropName()
   {
      return propName;
   }

   public String getPropValue()
   {
      return propValue;
   }

   public String toString()
   {
      return "[name=" + propName + ",value=" + propValue + "]";
   }
}
