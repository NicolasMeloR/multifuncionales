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
public enum TipoAjuste {

    Inicio(0, "0 - Inicio"),
    Aumento_Provision(1, "1 - Aumento Provision"),
    Disminucion_Provision(2, "2 - Disminucion Provision"),
    Sobrante_Efectivo(3, "3 - Sobrante Efectivo"),
    Faltante_Efectivo(4, "4 - Faltante Efectivo"),
    Ajuste_Egresos(5, "9612 - Ajuste Egresos"),
    Ajuste_Ingresos(6, "9710 - Ajuste Ingresos");
    public Integer codigo;
    public String nombre;
    
//         	{codigo:'0', nombre:'0 - Inicio'},
//                {codigo:'1', nombre:'1 - Aumento Provision'},
//                {codigo:'2', nombre:'2 - Disminucion Provision'},
//                {codigo:'3', nombre:'3 - Sobrante Efectivo'},
//                {codigo:'4', nombre:'4 - Faltante Efectivo'},
//                {codigo:'5', nombre:'9612 - Ajuste Egresos'},
//                {codigo:'6', nombre:'9710 - Ajuste Ingresos'}

    TipoAjuste(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoAjuste getTipoAjuste(Integer codigo) {
        TipoAjuste tipoAjuste = TipoAjuste.Inicio;
        for (TipoAjuste item : TipoAjuste.values()) {
            if (item.codigo.equals(codigo)) {
                tipoAjuste = item;
                break;
            }
        }
        return tipoAjuste;
    }
     public static TipoAjuste getTipoAjuste(String nombre) {
        TipoAjuste tipoAjuste = TipoAjuste.Inicio;
        for (TipoAjuste item : TipoAjuste.values()) {
            if (item.nombre.equals(nombre)) {
                tipoAjuste = item;
                break;
            }
        }
        return tipoAjuste;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}