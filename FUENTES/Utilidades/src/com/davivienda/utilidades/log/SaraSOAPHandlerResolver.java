package com.davivienda.utilidades.log;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class SaraSOAPHandlerResolver implements HandlerResolver {
    
	@Override
	public List<Handler> getHandlerChain(PortInfo port_info) {
        List<Handler> hchain = new ArrayList<Handler>();
        hchain.add(new SaraSOAPHandler());
        return hchain;
    }
}
