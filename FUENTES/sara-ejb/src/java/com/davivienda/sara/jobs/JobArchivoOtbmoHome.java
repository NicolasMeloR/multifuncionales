/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.jobs;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 *
 * @author jediazs
 */
public abstract interface JobArchivoOtbmoHome extends EJBHome {
    public abstract JobArchivoOtbmoRemote create() throws CreateException, RemoteException;
}
