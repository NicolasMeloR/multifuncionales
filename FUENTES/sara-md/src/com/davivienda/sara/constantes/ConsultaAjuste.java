package com.davivienda.sara.constantes;

/**
 * SistemaOperativo.java
 *
 * Fecha       :23 de enero de 2007, 08:17 PM
 *
 * Descripción :
 *
 * @author     :jjvargas
 *
 */
public enum ConsultaAjuste {

  
    Informe_TotalesATM(0, "InformeTotalesATM"),
    ResumenIDO_Terminal(1, "ResumenIDOTerminal"),
    ResumenIDO_Oficina(2, "ResumenIDOOficina");
    public Integer codigo;
    public String nombre;
  
 ConsultaAjuste(Integer codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static ConsultaAjuste getConsultaAjuste(Integer codigo) {
        ConsultaAjuste consultaAjuste = ConsultaAjuste.Informe_TotalesATM;
        for (ConsultaAjuste item : ConsultaAjuste.values()) {
            if (item.codigo.equals(codigo)) {
                consultaAjuste = item;
                break;
            }
        }
        return consultaAjuste;
    }
     public static ConsultaAjuste getConsultaAjuste(String nombre) {
        ConsultaAjuste consultaAjuste = ConsultaAjuste.Informe_TotalesATM;
        for (ConsultaAjuste item : ConsultaAjuste.values()) {
            if (item.nombre.equals(nombre)) {
                consultaAjuste = item;
                break;
            }
        }
        return consultaAjuste;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}
