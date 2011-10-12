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
package org.jboss.wsf.spi.deployment;

import java.util.List;

/**
 * A general service deployment manager.
 * 
 * @author Thomas.Diesler@jboss.com
 *
 * @since 20-Apr-2007 
 */
public interface DeploymentAspectManager
{
   /** Get the name for this aspect manager */
   String getName();
   
   /** Get the optional parent for this manager */
   DeploymentAspectManager getParent();
   
   /** Set the optional parent for this manager */
   void setParent(DeploymentAspectManager dam);
   
   /** Get the list of registered deployment aspects */
   List<DeploymentAspect> getDeploymentAspects();
   
   /** Set the list of registered deployment aspects */
   void setDeploymentAspects(List<DeploymentAspect> aspects);

   /** Deploy a web service */
   void deploy(Deployment dep);

   /** Undeploy a web service */
   void undeploy(Deployment dep);
}
