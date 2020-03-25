// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.autenticacion.webservice.session;

import javax.ejb.Local;

@Local
public interface AutenticacionLdapWSProcesosSessionLocal
{
    String autenticarLdap(final String p0, final String p1);
}
