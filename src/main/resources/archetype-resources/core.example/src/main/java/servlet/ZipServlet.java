#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ${package}.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Download servlet
 * 
 * <p>This servlet binds all resources of the type <tt>${appsFolderName}/home</tt>
 * and the zip extension. It generates a simplistic zip archive of all the child
 * pages, based on the <tt>jcr:title</tt> and <tt>jcr:content</tt> properties.</p>
 * 
 * <p>Assuming the sample content is installed, it will serve requests of the form 
 * <tt>GET /content/{contentFolderName}/home.zip</tt></p>
 * 
 * @see <a href="https://sling.apache.org/documentation/the-sling-engine/servlets.html">Servlets and Scripts</a>
 */
@Component(
    service = Servlet.class,
    property = {
        Constants.SERVICE_DESCRIPTION + "=Hello World Path Servlet",
        Constants.SERVICE_VENDOR + "=The Apache Software Foundation",
        "sling.servlet.resourceTypes=${appsFolderName}/home",
        "sling.servlet.extensions=zip",
    }
)
@SuppressWarnings("serial")
public class ZipServlet extends SlingSafeMethodsServlet {
    
    private final Logger log = LoggerFactory.getLogger(ByResourceTypeServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request,
            SlingHttpServletResponse response) throws ServletException,
            IOException {
        Resource resource = request.getResource();

        Writer w = response.getWriter();
        w.write("<!DOCTYPE html PUBLIC ${symbol_escape}"-//IETF//DTD HTML 2.0//EN${symbol_escape}">");
        w.write("<html>");
        w.write("<head>");
        w.write("<title>Hello World Servlet</title>");
        w.write("</head>");
        w.write("<body>");
        w.write("<h1>Hello ");
        w.write(resource.getPath());
        w.write("</h1>");
        w.write("</body>");
        w.write("</html>");
        
        log.info("Hello World Servlet");
        
    }

}

