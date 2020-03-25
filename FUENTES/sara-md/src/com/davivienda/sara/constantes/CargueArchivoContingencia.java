package com.davivienda.sara.constantes;


public enum CargueArchivoContingencia {

    TODOS(0 ,"todos"),
    CFAMO001(1 ,"cfamo001"),
    CFAMO003(2, "cfamo003"),
    GEATO002(3, "geato002"),
    GEATO003(4, "geato003"),
    OTBMO001(5, "otbmo001"),
    OTBMO003(6, "otbmo003"),
    CUADREDISPENSADOR(7,"CuadreCifrasDispensador"),
    CUADREMULTIFUNCIONAL(8,"CuadreCifrasMultifuncional"),
    MVTOATM01(9,"mvtoatm01"),
    MVTODEP03(10,"mvtodep03"),
    REINTEGROSDISPENSADOR(11,"ReintegrosDispensador"),
    REINTEGROSMULTI(12,"ReintegrosMulti"),
    DIARIOSDISPENSADOR(14,"EDC"),
    DIARIOSEFECTIVOMULTIFUNCIONAL(15,"CE"),
    DIARIOSREINTEGROS(16,"DiariosReintegros"),
    LOGCAJEROSMULTIFUNCIONAL(17,"LE"),
    INFORMEDIFERENCIAS(20,"Info.Diferecias");
    
    
    public Integer codigo;
    public String nombre;

    CargueArchivoContingencia(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static CargueArchivoContingencia getCarguearchivo(Integer codigo) {
        CargueArchivoContingencia cargueArchivo = CargueArchivoContingencia.TODOS;
        for (CargueArchivoContingencia item : cargueArchivo.values()) {
            if (item.codigo.equals(codigo)) {
                cargueArchivo = item;
                break;
            }
        }
        return cargueArchivo;
    }
    
     public static CargueArchivoContingencia getCarguearchivo(String nombre) {
        CargueArchivoContingencia cargueArchivo = CargueArchivoContingencia.TODOS;
        for (CargueArchivoContingencia item : CargueArchivoContingencia.values()) {
            if (item.nombre.equals(nombre)) {
                cargueArchivo = item;
                break;
            }
        }
        return cargueArchivo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}
