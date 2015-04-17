package org.jboss.wsf.spi.metadata.j2ee;

/**
 * WSDL publish location adapter.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public interface PublishLocationAdapter
{

   /**
    * Gets WSDL publish location for specified endpoint name.
    * 
    * @param endpointName endpoint name
    * @return WSDL publish location
    */
   public String getWsdlPublishLocationByName(String endpointName);

}