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
package org.jboss.wsf.spi.serviceref;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.Referenceable;

import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedServiceRefMetaData;

/**
 * An implementation of this interface handles all service-ref binding concerns.
 * 
 * @author Thomas.Diesler@jboss.org
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public interface ServiceRefHandler
{
   enum Type {
      JAXRPC, JAXWS
   };

   /**
    * @deprecated use {@link #createReferenceable(org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedServiceRefMetaData, ClassLoader)} instead
    */
   @Deprecated
   void bindServiceRef(Context encCtx, String encName, UnifiedVirtualFile vfsRoot, ClassLoader loader,
         ServiceRefMetaData sref) throws NamingException;

   /**
    * Creates JNDI referenceable representing this <b>serviceRef</b>.
    *
    * @param serviceRefUMDM service reference UMDM
    * @param loader class loader
    * @return JNDI referenceable
    */
   Referenceable createReferenceable(UnifiedServiceRefMetaData serviceRefUMDM, ClassLoader loader);
}
