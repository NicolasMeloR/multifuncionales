// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.jobs;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface JobInformeDiferenciasHome extends EJBHome
{
    JobInformeDiferenciasRemote create() throws CreateException, RemoteException;
}
