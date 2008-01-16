/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.wsf.spi.management.recording;

//$Id$

import java.util.List;

/**
 * Processes a record. A RecordProcessor may have filters to allow processing
 * of records matching given criteria. It also gives users fine management of
 * record's attributes to processed. 
 * 
 * @author alessio.soldano@jboss.com
 * @since 8-Dec-2007
 */
public interface RecordProcessor extends Cloneable
{
   String getName();

   void setName(String name);

   boolean isRecording();

   void setRecording(boolean value);

   void processRecord(Record record);

   List<RecordFilter> getFilters();

   void addFilter(RecordFilter filter);

   void setFilters(List<RecordFilter> filters);

   boolean isProcessSourceHost();

   void setProcessSourceHost(boolean value);

   boolean isProcessDestinationHost();

   void setProcessDestinationHost(boolean value);

   boolean isProcessMessageType();

   void setProcessMessageType(boolean value);

   boolean isProcessEnvelope();

   void setProcessEnvelope(boolean value);

   boolean isProcessHeaders();

   void setProcessHeaders(boolean value);

   boolean isProcessOperation();

   void setProcessOperation(boolean value);

   boolean isProcessDate();

   void setProcessDate(boolean value);

   /**
    * RecordFilters must override Object.clone()
    */
   Object clone() throws CloneNotSupportedException;
}
