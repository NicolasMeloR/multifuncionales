package com.davivienda.utilidades;

import org.eclipse.persistence.config.SessionCustomizer; 
import org.eclipse.persistence.sessions.Session; 

public class JPASessionCustomizer implements SessionCustomizer {

  private String schema = "ADMINATM";
  public JPASessionCustomizer() {
  }

  public void customize(Session session) { 
      session.getLogin().setTableQualifier(schema);
  } 
}