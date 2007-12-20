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
public interface RecordProcessor
{
   public String getName();

   public void setName(String name);

   public boolean isRecording();

   public void setRecording(boolean value);

   public void processRecord(Record record);

   public List<RecordFilter> getFilters();

   public void addFilter(RecordFilter filter);

   public void setFilters(List<RecordFilter> filters);

   public boolean isProcessSourceHost();

   public void setProcessSourceHost(boolean value);

   public boolean isProcessDestinationHost();

   public void setProcessDestinationHost(boolean value);

   public boolean isProcessMessageType();

   public void setProcessMessageType(boolean value);

   public boolean isProcessEnvelope();

   public void setProcessEnvelope(boolean value);

   public boolean isProcessHeaders();

   public void setProcessHeaders(boolean value);

   public boolean isProcessOperation();

   public void setProcessOperation(boolean value);

   public boolean isProcessDate();

   public void setProcessDate(boolean value);
}
