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
package org.jboss.wsf.spi.management.recording;

import java.util.Stack;

import org.jboss.logging.Logger;

//$Id$

/**
 * 
 * @author alessio.soldano@jboss.com
 * @since 8-Dec-2007
 */
public class RecordGroupAssociation
{
   private static ThreadLocal<Stack<Long>> groupIDAssoc = new ThreadLocal<Stack<Long>>();
   
   // provide logging
   private static Logger log = Logger.getLogger(RecordGroupAssociation.class);
  

   public static void pushGroupID(Long groupID)
   {
      if(log.isDebugEnabled()) log.debug("pushGroupID: " + groupID + " (Thread " +Thread.currentThread().getName()+ ")");
      Stack<Long> stack = groupIDAssoc.get();
      if (stack == null)
      {
         stack = new Stack<Long>();
         groupIDAssoc.set(stack);
      }
      stack.push(groupID);
   }

   public static Long peekGroupID()
   {
      Long groupID = null;
      Stack<Long> stack = groupIDAssoc.get();
      if (stack != null && stack.isEmpty() == false)
      {
         groupID = stack.peek();
      }
      if(log.isDebugEnabled()) log.debug("peekGroupID: " + groupID + " (Thread " +Thread.currentThread().getName()+ ")");
      return groupID;
   }

   public static Long popGroupID()
   {
      Long groupID = null;
      Stack<Long> stack = groupIDAssoc.get();
      if (stack != null && stack.isEmpty() == false)
      {
         groupID = stack.pop();
      }
      if(log.isDebugEnabled()) log.debug("popGroupID: " + groupID +" (Thread " +Thread.currentThread().getName()+ ")");
      return groupID;
   }
   
}
