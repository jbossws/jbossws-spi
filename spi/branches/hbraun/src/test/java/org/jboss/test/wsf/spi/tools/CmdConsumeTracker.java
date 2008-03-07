/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.test.wsf.spi.tools;

import org.jboss.wsf.spi.tools.WSContractConsumer;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.net.URL;

/**
 * @author Heiko.Braun@jboss.com
 * @version $Revision$
 */
public class CmdConsumeTracker extends WSContractConsumer
{
   public static String LAST_EVENT = "";

   public void setBindingFiles(List<File> bindingFiles)
   {
      LAST_EVENT += "setBindingFiles";
   }

   public void setCatalog(File catalog)
   {
      LAST_EVENT += "setCatalog";
   }

   public void setOutputDirectory(File directory)
   {
      LAST_EVENT += "setOutputDirectory";
   }

   public void setSourceDirectory(File directory)
   {
      LAST_EVENT += "setSourceDirectory";
   }

   public void setGenerateSource(boolean generateSource)
   {
      LAST_EVENT += "setGenerateSource";
   }

   public void setTargetPackage(String targetPackage)
   {
      LAST_EVENT += "setTargetPackage";
   }

   public void setWsdlLocation(String wsdlLocation)
   {
      LAST_EVENT += "setWsdlLocation";
   }

   public void setMessageStream(PrintStream messageStream)
   {
      LAST_EVENT += "setMessageStream";
   }

   public void setAdditionalCompilerClassPath(List<String> classPath)
   {
      LAST_EVENT += "setAdditionalCompilerClassPath";
   }

   public void setTarget(String target)
   {
      LAST_EVENT += "setTarget";
   }

   public void consume(URL wsdl)
   {
      LAST_EVENT += "consume";
   }
}
