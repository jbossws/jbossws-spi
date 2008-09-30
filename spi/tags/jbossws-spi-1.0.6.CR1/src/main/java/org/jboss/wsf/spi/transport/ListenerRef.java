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
package org.jboss.wsf.spi.transport;

import java.net.URI;

/**
 * A reference to an endpoint listener that can be
 * used to drive subsequent {@link org.jboss.wsf.spi.transport.TransportManager} operations
 * after the listener has been created.<br/>
 * I.e. the most simple usecase is to tear down the listener when the endpoint is undeployed.
 * @author Heiko.Braun <heiko.braun@jboss.com>
 */
public interface ListenerRef
{
   /**
    * UUID that distinct across protocols
    * @return
    */
   String getUUID();

   /**
    * The protocol itself
    * @return
    */
   Protocol getProtocol();

   /**
    * Address that can be published in WSDL
    * @return
    */
   URI getAddress();

}
