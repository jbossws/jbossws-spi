/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.deployment;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter to forward endpoint management request to cxf endpoint interceptor
 * @author  @author <a href="mailto:ema@redhat.com>Jim Ma</a>
 *
 */
public class ManagementFilter implements Filter {
    private String pattern;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pattern = filterConfig.getInitParameter("httpURLPattern");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("PUT".equals(req.getMethod()) || "GET".equals(req.getMethod())) {
            String query = req.getQueryString();
            if (query != null && !"wsdl".equals(query)) {
                String forwardPath = pattern.endsWith("/*") ? pattern.substring(0, pattern.length()-2) : pattern;
                req.getRequestDispatcher(forwardPath + "?" + query).forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }
    @Override
    public void destroy() {

    }

}
