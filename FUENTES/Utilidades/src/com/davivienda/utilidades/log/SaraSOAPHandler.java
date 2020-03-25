package com.davivienda.utilidades.log;

import com.davivienda.utilidades.Constantes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;


public class SaraSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    /**
     * Metodo que genera los request y response de los servicios en formato xml
     *
     * @param context
     * @return boolean
     * @throws SOAPException
     * @throws IOException
     */
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {

            boolean direction = ((Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();
            String pre = "";
            if (direction) {
                pre = "REQ: ";
            } else {
                pre = "RES: ";
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            context.getMessage().writeTo(out);
            String strMsg = new String(out.toByteArray());
            logger.finest(pre + strMsg);
        } catch (Exception e) {
            String error = "Se ha generado un error en la interceptaci?n del mensaje SOAP para hacer log. ";
            logger.warning(error + e == null ? "" : e.getMessage());
        }
        return true;
    }

    /**
     * Auto-generated method stub
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {

        return false;
    }

    /**
     * Auto-generated method stub
     */
    @Override
    public void close(MessageContext context) {

    }

    /**
     * Auto-generated method stub
     */
    @Override
    public Set<QName> getHeaders() {

        return null;
    }
}
