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

import org.jboss.wsf.spi.tools.cmd.WSConsume;

import java.security.Permission;

import junit.framework.TestCase;

/**
 * Test the command line interface to WSConsume.
 *
 * @author Heiko.Braun@jboss.com
 * @version $Revision$
 */
public class CmdConsumeTestCase extends TestCase
{

   SecurityManager systemDefault = System.getSecurityManager();
   SecurityManager interceptor = new InterceptedSecurity();

   class InterceptedSecurity extends  SecurityManager
   {
      private final SecurityManager parent = systemDefault;

      public void checkPermission(Permission perm)
      {
         if (parent != null)
         {
            parent.checkPermission(perm);
         }
      }

      public void checkExit(int status)
      {
         String msg = (status == 0) ? "WSConsume did exit without errors" : "WSConsume did exit with an error";
         throw new InterceptedExit(msg, status);
      }
   }

   class InterceptedExit extends SecurityException
   {
      private int exitCode;

      public InterceptedExit(String s, int code)
      {
         super(s);
         this.exitCode = code;
      }


      public int getExitCode()
      {
         return exitCode;
      }
   }

   private void swapSecurityManager()
   {
      if(System.getSecurityManager() instanceof InterceptedSecurity)
         System.setSecurityManager(systemDefault);
      else
         System.setSecurityManager(interceptor);
   }

   protected void setUp() throws Exception
   {
      super.setUp();

      // enforce loading of the tracker implemenation
      System.setProperty(
        "org.jboss.wsf.spi.tools.ConsumerFactoryImpl",
        "org.jboss.test.wsf.spi.tools.CmdConsumeTrackerFactory"
        );
   }

   /*
      new LongOpt("binding", LongOpt.REQUIRED_ARGUMENT, null, 'b'),
         new LongOpt("catalog", LongOpt.REQUIRED_ARGUMENT, null, 'c'),
         new LongOpt("package", LongOpt.REQUIRED_ARGUMENT, null, 'p'),
         new LongOpt("wsdlLocation", LongOpt.REQUIRED_ARGUMENT, null, 'w'),
         new LongOpt("output", LongOpt.REQUIRED_ARGUMENT, null, 'o'),
         new LongOpt("source", LongOpt.REQUIRED_ARGUMENT, null, 's'),
         new LongOpt("target", LongOpt.REQUIRED_ARGUMENT, null, 't'),
         new LongOpt("keep", LongOpt.NO_ARGUMENT, null, 'k'),
         new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),
         new LongOpt("quiet", LongOpt.NO_ARGUMENT, null, 'q'),
         new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v'),
         new LongOpt("load-consumer", LongOpt.NO_ARGUMENT, null, 'l'),

   */

   public void testInvalidBindingOption() throws Exception
   {
      executeCmd("-b", true);
   }

   public void testValidBindingOption() throws Exception
   {
      executeCmd("-b binding-file.xml Service.wsdl", false);
      assertEquals(CmdConsumeTracker.LAST_EVENT, "setBindingFiles");
   }

   public void testMissingOptions() throws Exception
   {
      executeCmd("", false);  // strange, shouldn't this exit(1) ?
   }

   // TODO: add arbitrary combinations on a case by case basis

   /**
    * Pass a number of arguments to WSConsume and specify if
    * you expect this to cause an exception.
    *
    * @param arguments
    * @param expectedException
    * @throws Exception
    */
   private void executeCmd(String arguments,  boolean expectedException) throws Exception
   {
      swapSecurityManager();

      String[] args = arguments.split("\\s");
      try
      {
         WSConsume.main(args);
         if(expectedException)
            fail("Did expect exception on args: " +args);
      }
      catch (InterceptedExit e)
      {
         boolean positivStatus = (e.getExitCode() == 0);
         if( (expectedException && positivStatus)
           || (!expectedException && !positivStatus) )
         {
            String s = expectedException ? "Did expect an exception, but " : "Did not expect an exception, but ";
            String s2 = positivStatus ? "status was positiv" : "status was negativ";
            throw new Exception(s+s2);
         }

      }
      finally
      {
         swapSecurityManager();
      }
   }
}
