package com.davivienda.sara.filter;

import com.davivienda.sara.administracion.session.SaraConfiguracionSession;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

    private static Logger logger = Logger.getLogger(Constantes.LOGGER_ACCESO);

    private FilterConfig filterConfig;

    @EJB
    SaraConfiguracionSession saraConfiguracionSession;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            logger.info("SecurityFilter - doFilter");
            if (servletRequest instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                HttpSession session = request.getSession(true);

                response.setHeader("X-Content-Type-Options", "nosniff");
                response.setHeader("X-XSS-Protection", "1; mode=block");
                response.setHeader("X-Frame-Options", "Deny");

                if (request.isSecure()) {
                    response.setHeader("Strict-Transport-Security", "max-age=31622400; includeSubDomains");
                }

                String requestUrl = (String) request.getRequestURL().toString();
																   
																						   

                if (isValidURI(requestUrl)) {
																									

                    String requestProtocol = requestUrl.split("//")[0];
                    String requestHost = requestUrl.split("//")[1].split("/")[0].split(":")[0];
																																					 
																																			

                    logger.info("SecurityFilter - Cargando dominiosValidos");
                    String dominiosValidos[] = saraConfiguracionSession.getAppConfig().DOMINIOS_VALIDOS;

                    logger.info("SecurityFilter - Cargando Petición");
                    logger.info("SecurityFilter - Peticion URL Entrante: " + requestUrl);
                    logger.info("SecurityFilter - Peticion Protocolo Entrante: " + requestProtocol);
                    logger.info("SecurityFilter - Peticion Host Entrante: " + requestHost );

                    logger.info("SecurityFilter - Lista de dominios cargados " + dominiosValidos.length + " ---> " + Arrays.toString(dominiosValidos));

                    if (null != requestHost && !"".equals(requestHost) && !requestHost.isEmpty()) {
                        if (dominiosValidos != null) {
                            boolean valido = false;
                            for (String dominioValido : dominiosValidos) {
                                logger.info("SecurityFilter - Validación Dominios dominioValido: " + dominioValido + " --> requestHost: " + requestHost + " resultado: " + (dominioValido.equalsIgnoreCase(requestHost)));
                                if (dominioValido.equalsIgnoreCase(requestHost)) {
                                    valido = true;
                                    break;
                                }
                            }
						 
                            if (!valido) {
                                logger.info("El dominio " + requestHost + " que viene en el request no es válido, si lo es incluirlo en la propiedad DOMINIOS_VALIDOS de la base de datos Tabla: CONF_MODULOS_APLICACION");
                                usuarioNoLogeado(false, response, servletRequest, servletResponse);
                                return;
                            }
                        } else {
                            logger.info("No se encontro parámetro de dominios válidos.");
                            usuarioNoLogeado(false, response, servletRequest, servletResponse);
                            return;
                        }
							
																					   
																						   
							   
                    }
				 

                    String uri = request.getRequestURI();
                    if (!uri.startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
                        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                        response.setDateHeader("Expires", 0); // Proxies.
                        logger.info("SecurityFilter - getRequestURI " + SaraUtil.stripXSS(request.getMethod(), Constantes.REGEX_ACEPTAR_DEFAULT) + "  : " + SaraUtil.stripXSS(uri, Constantes.REGEX_ACEPTAR_DEFAULT));
                    }

                    if (!uri.startsWith(request.getContextPath() + "/pages")) {
                        filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
                        return;
                    }

                    boolean loggedIn = false;
                    logger.info("SecurityFilter - SessionId: " + session.getId());
                    UsuarioAplicacionDTO usuarioAplicacionDTO = (UsuarioAplicacionDTO) session.getAttribute(Constantes.SESSION_SECURITY_DTO);
                    if (usuarioAplicacionDTO != null) {
                        logger.info("SecurityFilter - Usuario " + usuarioAplicacionDTO.getUsuario() + " ya estaba logueado");
                        loggedIn = true;
                    } else {
                        logger.info("SecurityFilter - Usuario no logueado");
                    }
                    if (!loggedIn) {
                        usuarioNoLogeado(loggedIn, response, servletRequest, servletResponse);
                        return;
                    }
                }else{
                    filterConfig.getServletContext().getRequestDispatcher("/access-denied.xhtml?faces-redirect=true").forward(servletRequest, servletResponse);
                    return;
                }
            }

        } catch (Exception ex) {
            logger.info("SecurityFilter - Exception");
            saraConfiguracionSession.getAppConfig().loggerAcceso.log(Level.SEVERE, ex.getMessage(), ex);
        }
        filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);

    }

    public void usuarioNoLogeado(boolean loggedIn, HttpServletResponse response, ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        if (!loggedIn) {
            logger.info("SecurityFilter - doFilter - usuarioNoLogeado - redirect");
            String baseURL = saraConfiguracionSession.getAppConfig().URL_ACCESO_APP;
            response.sendRedirect(baseURL);
            if (null != filterConfig) {
                ServletContext servletContext = filterConfig.getServletContext();

                if (null != servletContext) {

                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(baseURL);

                    if (null != requestDispatcher) {
                        requestDispatcher.forward(servletRequest, servletResponse);
                    }
                }
            }
        }
    }

    public boolean isValidURI(String uriStr) {
        try {
            URI uri = new URI(uriStr);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("SecurityFilter - init");
        this.filterConfig = filterConfig;
    }

}
