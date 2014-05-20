/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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

import java.util.Set;

/**
 * A deployment aspect that does nothing.
 * 
 * A deployment aspects can require/provide a set of string conditions.
 * This determines the order of deployment aspects in the deployment aspect manager. 
 * 
 * @author Thomas.Diesler@jboss.com
 * @since 20-Apr-2007 
 */
public interface DeploymentAspect
{
   public static final String LAST_DEPLOYMENT_ASPECT = "LAST_DEPLOYMENT_ASPECT";

   public void setLast(boolean isLast);
   
   public boolean isLast();
   
   public String getProvides();

   public void setProvides(String provides);

   public String getRequires();

   public void setRequires(String requires);
   
   public void setRelativeOrder(int relativeOrder);
   
   public int getRelativeOrder();

   public void start(Deployment dep);

   public void stop(Deployment dep);

   public Set<String> getProvidesAsSet();

   public Set<String> getRequiresAsSet();
   
   public ClassLoader getLoader();
}
