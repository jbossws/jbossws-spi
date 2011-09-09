package org.jboss.wsf.spi.deployment;

import org.jboss.wsf.spi.deployment.Endpoint.EndpointType;

public interface EndpointTypeFilter
{
   boolean accept(EndpointType type);

}
