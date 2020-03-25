/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.entitys.Zona;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
public class UbicacionDTO {

    public int idUbicacion;
    private Integer codigoUbicacion;
    private Integer tipoUbicacion;
    private Integer sucursal;
    private Integer oficina;
    private String direccion;
    private Long codigoCiudad;
    private String horario;
    private String nombreoficina;
    private String telefono;
    private String diashorarionormal;
    private String horarionormal;
    private String horarioadicional;
    private String horariosabado;
    private String horariodomingo;
    private String tipooficina;
    private Integer estado;
    private String nombreciudad;
    private Integer regional;
    private Integer zona;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private String descripcionLabel;

    public void limpiarUbicacionDTO(int primerZona, int primerRegional) {
        this.idUbicacion = 0;
        this.codigoUbicacion = 0;
        this.tipoUbicacion = 0;
        this.sucursal = 0;
        this.oficina = 0;
        this.direccion = "";
        this.codigoCiudad = 0L;
        this.horario = "";
        this.nombreoficina = "";
        this.telefono = "";
        this.diashorarionormal = "";
        this.horarionormal = "";
        this.horarioadicional = "";
        this.horariosabado = "";
        this.horariodomingo = "";
        this.tipooficina = "0";
        this.estado = 0;
        this.nombreciudad = "";
        this.regional = primerRegional;
        this.zona = primerZona;
        this.lunes = false;
        this.martes = false;
        this.miercoles = false;
        this.jueves = false;
        this.viernes = false;
    }

    public UbicacionDTO entidadDTO(Ubicacion item) {
        UbicacionDTO respItems = new UbicacionDTO();
        respItems.setCodigoUbicacion(item.getCodigoUbicacion());
        respItems.setTipoUbicacion(item.getTipoUbicacion());
        respItems.setSucursal(item.getSucursal());
        respItems.setOficina(item.getOficina());
        respItems.setDireccion(item.getDireccion());
        respItems.setCodigoCiudad(item.getCodigoCiudad());
        respItems.setHorario(item.getHorario());
        respItems.setNombreoficina(item.getNombreoficina());
        respItems.setTelefono(item.getTelefono());
        respItems.setDiashorarionormal(item.getDiashorarionormal());
        respItems.setHorarionormal(item.getHorarionormal());
        respItems.setHorarioadicional(item.getHorarioadicional());
        respItems.setHorariosabado(item.getHorariosabado());
        respItems.setHorariodomingo(item.getHorariodomingo());
        respItems.setTipooficina(item.getTipooficina());
        respItems.setEstado(item.getEstado());
        respItems.setNombreciudad(item.getNombreciudad());
        respItems.setRegional(item.getRegional().getIdRegional());
        respItems.setZona(item.getZona().getIdZona());

        respItems.setLunes(getDia(item.getDiashorarionormal(), "Lunes"));
        respItems.setMartes(getDia(item.getDiashorarionormal(), "Martes"));
        respItems.setMiercoles(getDia(item.getDiashorarionormal(), "Miercoles"));
        respItems.setJueves(getDia(item.getDiashorarionormal(), "Jueves"));
        respItems.setViernes(getDia(item.getDiashorarionormal(), "Viernes"));

        return respItems;
    }

    public Ubicacion entidad() throws ServletException {
        Ubicacion tc = new Ubicacion();
        Integer intTmp = null;
        Long lngTmp = null;
        String strTmp;
        String strHorario = "Dias_Horario_Normal : ";
        intTmp = getCodigoUbicacion();
        if (intTmp == null) {
            throw new ServletException("Código ubicación inválido");
        }
        tc.setCodigoUbicacion(intTmp);

        intTmp = getTipoUbicacion();
        if (intTmp == null) {
            throw new ServletException("Tipo ubicación inválido");
        }
        tc.setTipoUbicacion(intTmp);

        intTmp = getSucursal();
        if (intTmp == null) {
            throw new ServletException("Código sucursal inválido");
        }
        tc.setSucursal(intTmp);

        intTmp = getOficina();
        if (intTmp == null) {
            throw new ServletException("Código oficina inválido");
        }
        tc.setOficina(intTmp);

        strTmp = getDireccion();
        if (strTmp.length() == 0) {
            throw new ServletException("Dirección inválida");
        }
        tc.setDireccion(strTmp);
        lngTmp = getCodigoCiudad();
        if (lngTmp == null) {
            throw new ServletException("Código ciudad inválido");
        }
        tc.setCodigoCiudad(lngTmp);

        intTmp = getZona();
        if (intTmp == null) {
            throw new ServletException("Código zona inválido");
        }
        Zona objZona = new Zona();
        objZona.setIdZona(intTmp);
        tc.setZona(objZona);

        intTmp = getRegional();
        if (intTmp == null) {
            throw new ServletException("Código Regional inválido");
        }
        Regional objRegional = new Regional();
        objRegional.setIdRegional(intTmp);
        tc.setRegional(objRegional);

        strTmp = getNombreoficina();
        if (strTmp.length() == 0) {
            throw new ServletException("nombre Oficina inválida");
        }
        tc.setNombreoficina(strTmp);

        strTmp = getTelefono();
        if (strTmp.length() == 0) {
            throw new ServletException("telefono inválido");
        }
        tc.setTelefono(strTmp);

        strTmp = getDiasHorarioSemana();
        if (strTmp.length() == 0) {
            throw new ServletException("Seleccione los dias de horario normal");
        } else {
            strHorario = strHorario + strTmp + " : ";
        }
        tc.setDiashorarionormal(strTmp);

        strTmp = getHorarionormal();
        if (strTmp.length() == 0) {
            throw new ServletException("horario Normal inválido");
        } else {
            strHorario = strHorario + strTmp + " // ";
        }
        tc.setHorarionormal(strTmp);

        strTmp = getHorarioadicional();
        if (strTmp.length() != 0) {
            strHorario = strHorario + " Horario_Adicional : " + strTmp + " // ";
        }

        tc.setHorarioadicional(strTmp);

        strTmp = getHorariosabado();
        if (strTmp.length() != 0) {
            strHorario = strHorario + " Horario_Sabado : " + strTmp + " // ";
        }

        tc.setHorariosabado(strTmp);

        strTmp = getHorariodomingo();
        if (strTmp.length() != 0) {
            strHorario = strHorario + " Horario_Domingo : " + strTmp;
        }
        tc.setHorariodomingo(strTmp);
        tc.setHorario(strHorario);

        strTmp = getTipooficina();
        if (strTmp.length() == 0) {
            throw new ServletException("tipo Oficina inválido");
        }
        tc.setTipooficina(strTmp);

        strTmp = getNombreciudad();
        if (strTmp.length() == 0) {
            throw new ServletException("nombre Ciudad no válido");
        }
        tc.setNombreciudad(strTmp);

        intTmp = getEstado();
        if (intTmp == null) {
            throw new ServletException("cajero activo inválido");
        }
        tc.setEstado(intTmp);

        return tc;
    }

    private String getDiasHorarioSemana() {
        String diasSemana = "";

        if (isLunes()) {
            diasSemana = "Lunes";
        }
        if (isMartes()) {
            diasSemana = diasSemana + " Martes";
        }

        if (isMiercoles()) {
            diasSemana = diasSemana + " Miercoles";
        }
        if (isJueves()) {
            diasSemana = diasSemana + " Jueves";
        }
        if (isViernes()) {
            diasSemana = diasSemana + " Viernes";

        }
        return diasSemana;

    }

    private boolean getDia(String dias, String dia) {
        boolean estaDia = false;
        if (dias != null) {
            if (dias.contains(dia)) {
                estaDia = true;
            }
        }
        return estaDia;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Integer getCodigoUbicacion() {
        return codigoUbicacion;
    }

    public void setCodigoUbicacion(Integer codigoUbicacion) {
        this.codigoUbicacion = codigoUbicacion;
    }

    public Integer getTipoUbicacion() {
        return tipoUbicacion;
    }

    public void setTipoUbicacion(Integer tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }

    public Integer getSucursal() {
        return sucursal;
    }

    public void setSucursal(Integer sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getOficina() {
        return oficina;
    }

    public void setOficina(Integer oficina) {
        this.oficina = oficina;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Long codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombreoficina() {
        return nombreoficina;
    }

    public void setNombreoficina(String nombreoficina) {
        this.nombreoficina = nombreoficina;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDiashorarionormal() {
        return diashorarionormal;
    }

    public void setDiashorarionormal(String diashorarionormal) {
        this.diashorarionormal = diashorarionormal;
    }

    public String getHorarionormal() {
        return horarionormal;
    }

    public void setHorarionormal(String horarionormal) {
        this.horarionormal = horarionormal;
    }

    public String getHorarioadicional() {
        return horarioadicional;
    }

    public void setHorarioadicional(String horarioadicional) {
        this.horarioadicional = horarioadicional;
    }

    public String getHorariosabado() {
        return horariosabado;
    }

    public void setHorariosabado(String horariosabado) {
        this.horariosabado = horariosabado;
    }

    public String getHorariodomingo() {
        return horariodomingo;
    }

    public void setHorariodomingo(String horariodomingo) {
        this.horariodomingo = horariodomingo;
    }

    public String getTipooficina() {
        return tipooficina;
    }

    public void setTipooficina(String tipooficina) {
        this.tipooficina = tipooficina;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombreciudad) {
        this.nombreciudad = nombreciudad;
    }

    public Integer getRegional() {
        return regional;
    }

    public void setRegional(Integer regional) {
        this.regional = regional;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public String getDescripcionLabel() {
        return descripcionLabel;
    }

    public void setDescripcionLabel(String descripcionLabel) {
        this.descripcionLabel = descripcionLabel;
    }

    @Override
    public String toString() {
        return "UbicacionDTO{" + "idUbicacion=" + idUbicacion + ", codigoUbicacion=" + codigoUbicacion + ", tipoUbicacion=" + tipoUbicacion + ", sucursal=" + sucursal + ", oficina=" + oficina + ", direccion=" + direccion + ", codigoCiudad=" + codigoCiudad + ", horario=" + horario + ", nombreoficina=" + nombreoficina + ", telefono=" + telefono + ", diashorarionormal=" + diashorarionormal + ", horarionormal=" + horarionormal + ", horarioadicional=" + horarioadicional + ", horariosabado=" + horariosabado + ", horariodomingo=" + horariodomingo + ", tipooficina=" + tipooficina + ", estado=" + estado + ", nombreciudad=" + nombreciudad + ", regional=" + regional + ", zona=" + zona + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves + ", viernes=" + viernes + '}';
    }

}
