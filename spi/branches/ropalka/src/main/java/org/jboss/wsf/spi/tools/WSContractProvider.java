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
package org.jboss.wsf.spi.tools;

import java.io.File;
import java.io.PrintStream;

import org.jboss.wsf.spi.util.ServiceLoader;

/**
 * WSContractProvider is responsible for generating the required portable
 * JAX-WS artifacts for a service endpoint implementation. This includes class
 * files for wrapper types and fault beans. WSDL may be optionally generated as
 * well using this API.
 *
 * <p>The following example generates class files, source files and WSDL for an
 * endpoint:</p> 
 * <pre>
 * WSContractProvider provider = WSContractProvider.newInstance();
 * provider.setGenerateSource(true);
 * provider.setGenerateWsdl(true);
 * provider.setOutputDirectory(new File("output"));
 * provider.setMessageStream(System.out);
 * provider.provide(TestMe.class);
 * </pre>
 * 
 * <p>Thread-Safety:</p>
 * This class expects to be thread-confined, so it can not be shared between threads.
 *
 * @author <a href="mailto:jason.greene@jboss.com">Jason T. Greene</a>
 */
public abstract class WSContractProvider
{
   private static String DEFAULT_PROVIDER = "org.jboss.ws.tools.jaxws.impl.JBossWSProviderFactoryImpl";
   public static final String PROVIDER_PROPERTY = "org.jboss.wsf.spi.tools.ProviderFactoryImpl";

   protected WSContractProvider()
   {

   }

   /**
    * Obtain a new instance of a WSContractProvider. This will use the current
    * thread's context class loader to locate the WSContractProviderFactory
    * implementation.
    * 
    * @return a new WSContractProvider
    */
   public static WSContractProvider newInstance()
   {
      return newInstance(SecurityActions.getContextClassLoader());
   }

   /**
    * Obtain a new instance of a WSContractProvider. The specified ClassLoader will be used to
    * locate the WSContractProviderFactory implementation
    * 
    * @param loader the ClassLoader to use
    * @return a new WSContractProvider
    */
   public static WSContractProvider newInstance(ClassLoader loader)
   {
      ClassLoader oldLoader = SecurityActions.getContextClassLoader();
      try
      {
         SecurityActions.setContextClassLoader(loader);
         WSContractProviderFactory factory = (WSContractProviderFactory) ServiceLoader.loadService(PROVIDER_PROPERTY, DEFAULT_PROVIDER);
         return factory.createProvider(loader);
      }
      finally
      {
         SecurityActions.setContextClassLoader(oldLoader);
      }
   }

   /**
    * Enables/Disables WSDL generation.
    * 
    * @param generateWsdl whether or not to generate WSDL
    */
   public abstract void setGenerateWsdl(boolean generateWsdl);

   /**
    * Enables/Disables Java source generation.
    * 
    * @param generateSource whether or not to generate Java source.
    */
   public abstract void setGenerateSource(boolean generateSource);

   /**
    * Sets the main output directory. If the directory does not exist, it will be created.
    * 
    * @param directory the root directory for generated files
    */
   public abstract void setOutputDirectory(File directory);

   /**
    * Sets the resource directory. This directory will contain any generated
    * WSDL and XSD files. If the directory does not exist, it will be created.
    * If not specified, the output directory will be used instead.
    * 
    * @param directory the root directory for generated resource files
    */
   public abstract void setResourceDirectory(File directory);

   /**
    * Sets the source directory. This directory will contain any generated Java source.
    * If the directory does not exist, it will be created. If not specified, 
    * the output directory will be used instead.
    * 
    * @param directory the root directory for generated source code
    */
   public abstract void setSourceDirectory(File directory);

   /**
    * Sets the ClassLoader used to discover types. This defaults to the one used
    * in instantiation.
    * 
    * @param loader the ClassLoader to use
    */
   public abstract void setClassLoader(ClassLoader loader);

   /**
    * Generates artifacts using the current settings. This method may be invoked
    * more than once (e.g. multiple endpoints).
    * 
    * @param endpointClass the name of the endpoint implementation bean
    * @throws RuntimeException if any error occurs during processing, or the class is not found
    */
   public abstract void provide(String endpointClass);

   /**
    * Generates artifacts using the current settings. This method may be invoked
    * more than once (e.g. multiple endpoints).
    * 
    * @param endpointClass the endpoint implementation bean
    * @throws RuntimeException if any error occurs during processing
    */
   public abstract void provide(Class<?> endpointClass);

   /**
    * Sets the PrintStream to use for status feedback. The simplest example
    * would be to use System.out.
    * 
    * <p>Example output:</p> 
    * <pre>
    * Generating WSDL: 
    * TestMeService.wsdl 
    * Writing Source:
    * org/jboss/ws/tools/jaxws/TestMe.java
    * org/jboss/ws/tools/jaxws/TestMeResponse.java 
    * Writing Classes:
    * org/jboss/ws/tools/jaxws/TestMe.class
    * org/jboss/ws/tools/jaxws/TestMeResponse.class
    * </pre>
    * @param messageStream  the stream to use for status messages:
    */
   public abstract void setMessageStream(PrintStream messageStream);
}
