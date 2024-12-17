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
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

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

    @Override
    protected void doGet(SlingHttpServletRequest request,
            SlingHttpServletResponse response) throws ServletException,
            IOException {
        response.setContentType("application/content-stream");
        try ( ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            for ( Resource child : request.getResource().getChildren() ) {
                ZipEntry entry = new ZipEntry(child.getName() + ".txt");
                zos.putNextEntry(entry);
                StringBuilder sb = new StringBuilder();
                sb
                    .append(child.getValueMap().get("jcr:title", String.class))
                    .append("\n\n")
                    .append(child.getValueMap().get("jcr:contents"));
                zos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}

