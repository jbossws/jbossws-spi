/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.jboss.wsf.spi.deployer;

import java.net.URL;
import java.util.Map;

import org.jboss.wsf.spi.SPIView;

/**
 * Abstraction to provide AS agnostic remote deployer.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public interface Deployer extends SPIView
{
	/**
	 * Deploys specified archive remotely.
	 * @param archive to deploy
	 * @throws Exception if some problem occurs
	 */
    void deploy(URL archive) throws Exception;
	/**
	 * Undeploys specified archive remotely.
	 * @param archive to undeploy
	 * @throws Exception if some problem occurs
	 */
    void undeploy(URL archive) throws Exception;
    
    /**
     * Adds a new security domain
     * 
     * @param name
     * @param authenticationOptions
     */
    void addSecurityDomain(String name, Map<String,String> authenticationOptions) throws Exception;
    
    /**
     * Removes a security domain
     * 
     * @param name
     */
    void removeSecurityDomain(String name) throws Exception;
}
