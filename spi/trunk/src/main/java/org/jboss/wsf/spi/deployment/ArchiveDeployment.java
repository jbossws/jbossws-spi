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

import java.util.List;

/**
 * A general web service deployment. 
 * 
 * @author Thomas.Diesler@jboss.com
 * @since 20-Apr-2007 
 */
public interface ArchiveDeployment extends Deployment
{
   /**
    * Get the optional parent of this deployment
    * @return   parent of the deployment
    */
   ArchiveDeployment getParent();

   /**
    * Get the root file for this deployment
    * @return   root file of the deployment
    */
   UnifiedVirtualFile getRootFile();

   /**
    * The concatenated names including all parents.
    * @return     concatenated names
    */
   String getCanonicalName();

   /**
    * Get additional metadata files attached to the deployment
    * @return    metadata files attached to the deployment
    */
   List<UnifiedVirtualFile> getMetadataFiles();

   /**
    * Add additional metadata file to the deployment
    * @param file    metadata file to the deployment
    */
   void addMetadataFile(UnifiedVirtualFile file);

   /**
    * Get the resource resolver to be used to access resources from this archive
    * @return   resource resolver
    */
   ResourceResolver getResourceResolver();
   
}
