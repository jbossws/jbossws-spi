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

package org.jboss.wsf.spi.metadata.webservices;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public final class JBossWebserviceDescriptionMetaData
{
   private final String webserviceDescriptionName;
   private final String wsdlPublishLocation;

   public JBossWebserviceDescriptionMetaData(String webserviceDescriptionName, String wsdlPublishLocation)
   {
      this.webserviceDescriptionName = webserviceDescriptionName;
      this.wsdlPublishLocation = wsdlPublishLocation;
   }

   public String getWsdlPublishLocation()
   {
      return wsdlPublishLocation;
   }

   public String getWebserviceDescriptionName()
   {
      return webserviceDescriptionName;
   }

   public static JBossWebserviceDescriptionMetaData merge(JBossWebserviceDescriptionMetaData base, JBossWebserviceDescriptionMetaData override) {
      if (base == null) {
         return override;
      }
      if (override == null) {
         return base;
      }
      return new JBossWebserviceDescriptionMetaData(override.webserviceDescriptionName != null ? override.webserviceDescriptionName : base.webserviceDescriptionName,
            override.wsdlPublishLocation != null ? override.wsdlPublishLocation : base.wsdlPublishLocation);
   }
}