/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.corejsf;

import com.davivienda.utilidades.Constantes;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JULIANENRIQUEDIAZSAR
 */
@FacesRenderer(componentFamily = "javax.faces.Input", rendererType = "com.corejsf.CSRFToken")
public class CSRFTokenInput extends Renderer {

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

        // get the session (don't create a new one!)
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        // get the token from the session
        String token = (String) session.getAttribute(Constantes.CSRFTOKEN_NAME);

        // write the component html to the response
        ResponseWriter responseWriter = context.getResponseWriter();
        responseWriter.startElement("input", null);
        responseWriter.writeAttribute("type", "hidden", null);
        responseWriter.writeAttribute("name", (component.getClientId(context)), "clientId");
        responseWriter.writeAttribute("value", token, "CSRFTOKEN_NAME");
        responseWriter.endElement("input");
        System.out.println("encode: " + component.getClientId(context));
        System.out.println("encode token: " + token);

    }

    @Override
    public void decode(FacesContext context, UIComponent component) {

        String clientId = component.getClientId(context);
        ExternalContext external = context.getExternalContext();
        Map<String, String> requestMap = external.getRequestParameterMap();

        String value = String.valueOf(requestMap.get(clientId));

        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String token = (String) session.getAttribute(Constantes.CSRFTOKEN_NAME);

        System.out.println("decode  value: " + value + "--> token: " + token);


        if (!value.equalsIgnoreCase(token)) {
            System.out.println("decode CSRFToken does not match!");
            String CSRFTokenAttack = String.valueOf(context.getExternalContext().getSessionMap().get(Constantes.CSRFTOKEN_ATTR));
            System.out.println("decode  CSRFTokenAttack: " + CSRFTokenAttack);
            context.getExternalContext().getSessionMap().put(Constantes.CSRFTOKEN_ATTR, true);

            throw new RuntimeException("CSRFToken does not match!");
        }

        if (value == null || "".equals(value)) {
            System.out.println("decode CSRFToken is missing!");
            String CSRFTokenAttack = String.valueOf(context.getExternalContext().getSessionMap().get(Constantes.CSRFTOKEN_ATTR));
            System.out.println("decode  CSRFTokenAttack: " + CSRFTokenAttack);
            context.getExternalContext().getSessionMap().put(Constantes.CSRFTOKEN_ATTR, true);

            throw new RuntimeException("CSRFToken is missing!");
        }

    }
}
