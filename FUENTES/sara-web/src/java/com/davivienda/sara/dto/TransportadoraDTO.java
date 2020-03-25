/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Transportadora;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
public class TransportadoraDTO {

    public int idTrans;
    private Integer idTransportadora;
    private String nombre;
    private String dirArchivoArqueo;
    private Integer version;

    public void limpiarTranspotadoraDTO() {
        this.idTrans = 0;
        this.idTransportadora = 0;
        this.nombre = "";
        this.dirArchivoArqueo = "";
        this.version = 0;
    }

    public TransportadoraDTO entidadDTO(Transportadora item) {
        TransportadoraDTO respItems = new TransportadoraDTO();
        respItems.setIdTransportadora(item.getIdTransportadora());
        respItems.setNombre(item.getNombre());
        respItems.setDirArchivoArqueo(item.getDirArchivoArqueo());
        respItems.setVersion(item.getVersion());

        return respItems;
    }

    public Transportadora entidad() throws ServletException {
        Transportadora tc = new Transportadora();
        Integer intTmp = null;
        String strTmp = "";

        intTmp = getIdTransportadora();
        if (intTmp == null) {
            throw new ServletException("Código Transportadora inválido");
        }

        tc.setIdTransportadora(intTmp);

        strTmp = getNombre();
        if (strTmp.length() == 0) {
            throw new ServletException("nombre Transportadora inválida");
        }
        tc.setNombre(strTmp);

        strTmp = getDirArchivoArqueo();
        if (strTmp.length() == 0) {
            throw new ServletException("directorio Archivo Arqueo inválido");
        }
        tc.setDirArchivoArqueo(strTmp);

        return tc;
    }

    public int getIdTrans() {
        return idTrans;
    }

    public void setIdTrans(int idTrans) {
        this.idTrans = idTrans;
    }

    public Integer getIdTransportadora() {
        return idTransportadora;
    }

    public void setIdTransportadora(Integer idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirArchivoArqueo() {
        return dirArchivoArqueo;
    }

    public void setDirArchivoArqueo(String dirArchivoArqueo) {
        this.dirArchivoArqueo = dirArchivoArqueo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TransportadoraDTO{" + "idTrans=" + idTrans + ", idTransportadora=" + idTransportadora + ", nombre=" + nombre + ", dirArchivoArqueo=" + dirArchivoArqueo + ", version=" + version + '}';
    }

}
