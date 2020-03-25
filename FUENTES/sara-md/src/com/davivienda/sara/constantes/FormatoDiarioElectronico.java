package com.davivienda.sara.constantes;

/**
 * FormatoDiarioElectronico
 * 
 * Enumeración de los tipos de formato Diario electrónico
 * @author jjvargas
 */
public enum FormatoDiarioElectronico {

    EDC(0, "EDC");
    public Integer codigo;
    public String nombre;

    FormatoDiarioElectronico(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static FormatoDiarioElectronico getFormatoDiarioElectronico(Integer codigo) {
        FormatoDiarioElectronico formatoDiarioElectronico = FormatoDiarioElectronico.EDC;
        for (FormatoDiarioElectronico item : FormatoDiarioElectronico.values()) {
            if (item.codigo.equals(codigo)) {
                formatoDiarioElectronico = item;
                break;
            }
        }
        return formatoDiarioElectronico;
    }
   
            
     public static FormatoDiarioElectronico getFormatoDiarioElectronico(String nombre) {
        FormatoDiarioElectronico formatoDiarioElectronico = FormatoDiarioElectronico.EDC;
        for (FormatoDiarioElectronico item : FormatoDiarioElectronico.values()) {
            if (item.nombre.equals(nombre)) {
                formatoDiarioElectronico = item;
                break;
            }
        }
        return formatoDiarioElectronico;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
