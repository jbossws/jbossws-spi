/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
