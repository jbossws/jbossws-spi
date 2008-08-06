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

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * 
 * @author alessio.soldano@jboss.com
 * @since 8-Dec-2007
 */
public interface Record
{
   public enum MessageType {INBOUND, OUTBOUND};
   
   /**
    * Gets the group ID corresponding to the current message exchange flow
    * 
    * @return
    */
   public String getGroupID();
   
   public void setGroupID(String groupID);
   
   /**
    * Gets the date of this record
    * 
    * @return
    */
   public Date getDate();
   
   public void setDate(Date date);
   
   /**
    * Gets the source (message sender) host. The result format conforms to RFC2732
    * 
    * @return source host
    */
   public String getSourceHost();
   
   public void setSourceHost(String host);
   
   /**
    * Gets the source (message sender) host. The result format conforms to RFC2732
    * 
    * @return the source host
    */
   public String getDestinationHost();
   
   public void setDestinationHost(String host);
   
   /**
    * Gets the message type, i.e. MessageType.INBOUND or MessageType.OUTBOUND
    * 
    * @return the message type
    */
   public MessageType getMessageType();
   
   public void setMessageType(MessageType type);
   
   
   /**
    * Gets the SOAP message envelope
    * 
    * @return
    */
   public String getEnvelope();
   
   public void setEnvelope(String envelope);
   
   /**
    * Gets the HTTP headers
    * 
    * @return the headers
    */
   public Map<String, List<String>> getHeaders();
   
   public void addHeaders(String key, List<String> value);
   
   public void setHeaders(Map<String, List<String>> headers);
   
   /**
    * Gets the invoked operation
    * 
    * @return the operation
    */
   public QName getOperation();
   
   public void setOperation(QName operation);
}
