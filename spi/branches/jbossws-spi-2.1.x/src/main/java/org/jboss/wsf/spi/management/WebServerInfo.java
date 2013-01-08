package org.jboss.wsf.spi.management;

public interface WebServerInfo
{
   int getPort(String protocol, boolean secure);
}
