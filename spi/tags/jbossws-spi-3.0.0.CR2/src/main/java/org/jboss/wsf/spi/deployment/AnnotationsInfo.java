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

/**
 * Utility for providing informations on annotations specified in a deployment.
 * This basically decouples the WS stack from the Jandex project.
 *
 * @author alessio.soldano@jboss.com
 */
public interface AnnotationsInfo
{
   /**
    * tells if the deployment includes classes annotated with the specified annotation(s).
    * 
    * @param annotation The FQN of the annotation
    * @return true if classes annotated with the specified annotation are found in the deployment, false otherwise.
    */
   boolean hasAnnotatedClasses(String... annotation);
}
