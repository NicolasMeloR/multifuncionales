// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.jobs;

import java.rmi.RemoteException;
import javax.ejb.TimerHandle;
import com.davivienda.utilidades.SchedulerInfo;
import javax.ejb.Remote;
import javax.ejb.EJBObject;

@Remote
public interface JobArchivoOtbmoRemote extends EJBObject
{
    TimerHandle createTimer(final SchedulerInfo p0) throws RemoteException;
}
