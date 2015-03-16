/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.deployment;

import java.util.Map;
import java.util.Set;

/**
 * Runtime config interface to allow change various property in runtime 
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
public interface RuntimeConfig
{
   static final String STATISTICS_ENABLED = "statistics-enabled";

   /** Get runtime changeable property */
   String getRuntimeProperty(String key);

   /** Set runtime changeable property */
   void setRuntimeProperty(String key, String value);

   /** Remove a runtime changeable property */
   void removeRuntimeProperty(String key);

   /** Get the set of runtime changeable property names */
   Map<String, String> getRuntimeProperties();
   /** Get the runtime config name set that can be get/set*/
   Set<String> getRuntimeConfigFlags();

}
