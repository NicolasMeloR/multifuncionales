package com.davivienda.sara.corejsf;

import com.davivienda.utilidades.Constantes;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFilter implements Filter {

    private int sizeThreshold = -1;
    private String repositoryPath;

    public void init(FilterConfig config) throws ServletException {
        repositoryPath = config.getInitParameter(
                "com.corejsf.UploadFilter.repositoryPath");
        try {
            String paramValue = config.getInitParameter(
                    "com.corejsf.UploadFilter.sizeThreshold");
            if (paramValue != null) {
                sizeThreshold = Integer.parseInt(paramValue);
            }
        } catch (NumberFormatException ex) {
            ServletException servletEx = new ServletException();
            servletEx.initCause(ex);
            throw servletEx;
        }
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      

        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(httpRequest);
        if (!isMultipartContent) {
            chain.doFilter(request, response);
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        if (sizeThreshold >= 0) {
            factory.setSizeThreshold(sizeThreshold);
        }
        if (repositoryPath != null) {
            File f = new File(repositoryPath);
            if (f.isDirectory()) {
                factory.setRepository(f);
            }
        }
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            @SuppressWarnings("unchecked")
            List<FileItem> items = (List<FileItem>) upload.parseRequest(httpRequest);
            final Map<String, String[]> map = new HashMap<String, String[]>();
            for (FileItem item : items) {
                String str = item.getString();
                String fileName = item.getName();
                if (null != fileName) {
                    String extensionArchivo = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    if (Constantes.EXTENSIONES_CICLO_DIARIO.equalsIgnoreCase(extensionArchivo)
                            || Constantes.EXTENSIONES_DIARIO_ELECTRONICO.equalsIgnoreCase(extensionArchivo)
                            || Constantes.EXTENSIONES_MULTIFUNCIONAL.equalsIgnoreCase(extensionArchivo)
                            || Constantes.EXTENSIONES_ARCHIVO_EXCEL.equalsIgnoreCase(extensionArchivo)
                            || Constantes.EXTENSIONES_ARCHIVO_EXCEL_XLSX.equalsIgnoreCase(extensionArchivo)) {
                        if (item.isFormField()) {
                            map.put(item.getFieldName(), new String[]{str});

                        } else {
                            httpRequest.setAttribute(item.getFieldName(), item);
                        }
                    } else {
                        item.delete();
                    }
                } else if (item.isFormField()) {
                    map.put(item.getFieldName(), new String[]{str});

                } else {
                    httpRequest.setAttribute(item.getFieldName(), item);
                }
            }

            chain.doFilter(new HttpServletRequestWrapper(httpRequest) {
                public Map<String, String[]> getParameterMap() {
                    return map;
                }
                // busywork follows ... should have been part of the wrapper

                public String[] getParameterValues(String name) {
                    Map<String, String[]> map = getParameterMap();
                    return (String[]) map.get(name);
                }

                public String getParameter(String name) {
                    String[] params = getParameterValues(name);
                    if (params == null) {
                        return null;
                    }
                    return params[0];
                }

                public Enumeration<String> getParameterNames() {
                    Map<String, String[]> map = getParameterMap();
                    return Collections.enumeration(map.keySet());
                }
            }, response);
        } catch (FileUploadException ex) {
            ServletException servletEx = new ServletException();
            servletEx.initCause(ex);
            throw servletEx;
        }
    }
}
