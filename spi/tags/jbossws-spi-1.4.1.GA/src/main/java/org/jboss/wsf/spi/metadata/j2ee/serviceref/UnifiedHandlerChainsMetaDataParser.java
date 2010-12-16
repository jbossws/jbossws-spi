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
package org.jboss.wsf.spi.metadata.j2ee.serviceref;

import java.io.IOException;
import java.io.InputStream;

import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;

/** The interface of the parser for the unified metadata handler chains element
 * 
 * @author alessio.soldano@jboss.com
 * @since 26-Nov-2010
 */
public class UnifiedHandlerChainsMetaDataParser
{
   @SuppressWarnings("deprecation")
   public static UnifiedHandlerChainsMetaData parse(InputStream is) throws IOException
   {
      try
      {
         Unmarshaller unmarshaller = UnmarshallerFactory.newInstance().newUnmarshaller();
         return (UnifiedHandlerChainsMetaData) unmarshaller.unmarshal(is, new HandlerChainsObjectFactory(), null);
      }
      catch (JBossXBException xbe)
      {
         throw new IOException(xbe);
      }
   }
}
