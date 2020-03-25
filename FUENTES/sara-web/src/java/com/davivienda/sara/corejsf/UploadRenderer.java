package com.davivienda.sara.corejsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;

@FacesRenderer(componentFamily = "javax.faces.Input", rendererType = "com.corejsf.Upload")
public class UploadRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (!component.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();

        String clientId = component.getClientId(context);

        writer.startElement("input", component);
        writer.writeAttribute("type", "file", "type");
        writer.writeAttribute("name", clientId, "clientId");
        writer.endElement("input");
        writer.flush();
    }

    public void decode(FacesContext context, UIComponent component) {
        try {
            ExternalContext external = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) external.getRequest();

            String clientId = component.getClientId(context);
            FileItem item = (FileItem) request.getAttribute(clientId);
            Object newValue = null;
            ValueExpression valueExpr = component.getValueExpression("value");
            if (valueExpr != null) {
                Class<?> valueType = valueExpr.getType(context.getELContext());

                if (item != null) {

                    if (valueType == byte[].class) {
                        newValue = item.get();
                    } else if (valueType == InputStream.class) {
                        try {
                            newValue = item.getInputStream();
                        } catch (IOException ex) {
                            throw new FacesException(ex);
                        } finally {
                            if (newValue instanceof InputStream) {
                                ((InputStream) newValue).close();
                            }
                        }

                    } else {
                        String encoding = request.getCharacterEncoding();
                        if (encoding != null) {
                            try {
                                newValue = item.getString(encoding);
                            } catch (UnsupportedEncodingException ex) {
                                newValue = item.getString();
                            }
                        } else {
                            newValue = item.getString();
                        }
                    }
                    ((EditableValueHolder) component).setSubmittedValue(item);
                    ((EditableValueHolder) component).setValid(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
