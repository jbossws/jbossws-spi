/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.metadata;

import java.net.URL;

import org.jboss.xb.binding.ObjectModelFactory;

/**
 * Descriptor processor is abstraction over configuration procesing.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public interface DescriptorProcessor<T>
{
   /**
    * Indicates whether validation is turned on or off.
    * @return true if validation is on, false otherwise
    */
   boolean isValidating();
   /**
    * Descriptor name to parse and process.
    * @return descriptor name to consume.
    */
   String getDescriptorName();
   /**
    * OM factory building object tree from the configuration file.
    * @return OM factory
    */
   ObjectModelFactory getFactory(final URL url);
}
