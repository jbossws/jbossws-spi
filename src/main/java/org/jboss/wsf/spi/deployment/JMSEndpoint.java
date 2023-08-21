/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.wsf.spi.deployment;

import java.net.URI;

/**
 * To represent the jms transport endpoint
 * 
 * @author <a href="ema@redhat.com">Jim Ma</a>
 */
public interface JMSEndpoint extends Endpoint
{
   //Set jms target destination
   void setTargetDestination(String dest);
   
   //Get jms target destination
   String getTargetDestination();
   
   //Set jms reply destination
   void setReplyDestination(String replyTo);
   
   //Get jms reply destination
   String getReplyDestination();
   
   //Set soap over jms requestURI  
   void setRequestURI(URI uri);
   
   //Get soap over jms requestURI  
   URI getRequestURI();
}
