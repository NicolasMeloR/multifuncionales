/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.jobs;

import com.davivienda.utilidades.SchedulerInfo;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import javax.ejb.Remote;
import javax.ejb.TimerHandle;

/**
 *
 * @author jediazs
 */

@Remote
public abstract interface JobArchivoCfamoRemote extends EJBObject{
    
    
  public abstract TimerHandle createTimer(SchedulerInfo schedulerInfo)  throws RemoteException;
  
}
