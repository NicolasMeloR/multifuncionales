/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jediazs
 */
public class UsuarioAplicacionDTO implements Serializable {
    
    public String error;
    public String usuario;
    public String nombre;
    public String sistema;
    public String normal;
    public String auditoria;
    public String direccionIP;
    public String token;
    public String claveEstatica;    
    public Map usuarioAcceso;
    public boolean srvc1;
    public boolean srvc2;
    public boolean srvc3;
    public boolean srvc4;
    public boolean srvc5;
    public boolean srvc6;
    public boolean srvc7;
    public boolean srvc8;
    public boolean srvc9;
    public boolean srvc101;
    public boolean srvc102;
    public boolean srvc121;
    public boolean srvc201;
    public boolean srvc202;
    public boolean srvc211;
    public boolean srvc212;
    public boolean srvc221;
    public boolean srvc301;
    public boolean srvc302;
    public boolean srvc311;
    public boolean srvc312;
    public boolean srvc321;
    public boolean srvc322;
    public boolean srvc331;
    public boolean srvc332;
    public boolean srvc333;
    public boolean srvc334;
    public boolean srvc341;
    public boolean srvc401;
    public boolean srvc402;
    public boolean srvc411;
    public boolean srvc412;
    public boolean srvc421;
    public boolean srvc422;
    public boolean srvc431;
    public boolean srvc432;
    public boolean srvc441;
    public boolean srvc442;
    public boolean srvc451;
    public boolean srvc452;
    public boolean srvc501;
    public boolean srvc502;
    public boolean srvc511;
    public boolean srvc512;
    public boolean srvc601;
    public boolean srvc602;
    public boolean srvc701;
    public boolean srvc702;
    public boolean srvc711;
    public boolean srvc712;
    public boolean srvc721;
    public boolean srvc722;
    public boolean srvc801;
    public boolean srvc802;
    public boolean srvc811;
    public boolean srvc812;
    public boolean srvc901;
    public boolean srvc902;
    public boolean srvc903;

    private UsuarioAplicacion usuarioAplicacion;
    
    
    public UsuarioAplicacionDTO entidadDTO(UsuarioAplicacion usuarioAplicacion) {
         
        UsuarioAplicacionDTO usuarioAplicacionDTO = new UsuarioAplicacionDTO();
        
        usuarioAplicacionDTO.setError("0000");
        usuarioAplicacionDTO.setUsuario(usuarioAplicacion.getUsuario());
        usuarioAplicacionDTO.setNombre(usuarioAplicacion.getNombre());
        usuarioAplicacionDTO.setSistema(usuarioAplicacion.getSistema().toString());
        usuarioAplicacionDTO.setNormal(usuarioAplicacion.getNormal().toString());
        usuarioAplicacionDTO.setAuditoria(usuarioAplicacion.getAuditoria().toString());
        usuarioAplicacionDTO.setDireccionIP(usuarioAplicacion.getDireccionIp());        
        usuarioAplicacionDTO.setToken(usuarioAplicacion.getToken());
        usuarioAplicacionDTO.setClaveEstatica(usuarioAplicacion.getClaveEstatica());
        
        for (ConfAccesoAplicacion confAccesoAplicacion : usuarioAplicacion.getConfAccesoAplicacionCollection()) {
            UsuarioAccesoDTO accesoDto = new UsuarioAccesoDTO();
            accesoDto.setConsultar(confAccesoAplicacion.getConsultar().toString());
            accesoDto.setActualizar(confAccesoAplicacion.getActualizar().toString());
            accesoDto.setEjecutar(confAccesoAplicacion.getEjecutar().toString());
            accesoDto.setAdministrar(confAccesoAplicacion.getAdministrar().toString());
            accesoDto.setCrear(confAccesoAplicacion.getCrear().toString());
            accesoDto.setBorrar(confAccesoAplicacion.getBorrar().toString());
            usuarioAplicacionDTO.getUsuarioAcceso().put("srvc" + confAccesoAplicacion.getServicioAplicacion().getServicio().toString(), accesoDto);
        }
        
        return usuarioAplicacionDTO;
    }
    
    public UsuarioAplicacionDTO(){
        this.usuarioAcceso = new HashMap();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(String auditoria) {
        this.auditoria = auditoria;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public Map getUsuarioAcceso() {
        return usuarioAcceso;
    }

    public void setUsuarioAcceso(Map usuarioAcceso) {
        this.usuarioAcceso = usuarioAcceso;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClaveEstatica() {
        return claveEstatica;
    }

    public void setClaveEstatica(String claveEstatica) {
        this.claveEstatica = claveEstatica;
    }

    public boolean isSrvc1() {
        return srvc1;
    }

    public void setSrvc1(boolean srvc1) {
        this.srvc1 = srvc1;
    }

    public boolean isSrvc2() {
        return srvc2;
    }

    public void setSrvc2(boolean srvc2) {
        this.srvc2 = srvc2;
    }

    public boolean isSrvc3() {
        return srvc3;
    }

    public void setSrvc3(boolean srvc3) {
        this.srvc3 = srvc3;
    }

    public boolean isSrvc4() {
        return srvc4;
    }

    public void setSrvc4(boolean srvc4) {
        this.srvc4 = srvc4;
    }

    public boolean isSrvc5() {
        return srvc5;
    }

    public void setSrvc5(boolean srvc5) {
        this.srvc5 = srvc5;
    }

    public boolean isSrvc6() {
        return srvc6;
    }

    public void setSrvc6(boolean srvc6) {
        this.srvc6 = srvc6;
    }

    public boolean isSrvc7() {
        return srvc7;
    }

    public void setSrvc7(boolean srvc7) {
        this.srvc7 = srvc7;
    }

    public boolean isSrvc8() {
        return srvc8;
    }

    public void setSrvc8(boolean srvc8) {
        this.srvc8 = srvc8;
    }

    public UsuarioAplicacion getUsuarioAplicacion() {
        return usuarioAplicacion;
    }

    public void setUsuarioAplicacion(UsuarioAplicacion usuarioAplicacion) {
        this.usuarioAplicacion = usuarioAplicacion;
    }

    public boolean isSrvc101() {
        return srvc101;
    }

    public void setSrvc101(boolean srvc101) {
        this.srvc101 = srvc101;
    }

    public boolean isSrvc102() {
        return srvc102;
    }

    public void setSrvc102(boolean srvc102) {
        this.srvc102 = srvc102;
    }

    public boolean isSrvc121() {
        return srvc121;
    }

    public void setSrvc121(boolean srvc121) {
        this.srvc121 = srvc121;
    }

    public boolean isSrvc201() {
        return srvc201;
    }

    public void setSrvc201(boolean srvc201) {
        this.srvc201 = srvc201;
    }

    public boolean isSrvc202() {
        return srvc202;
    }

    public void setSrvc202(boolean srvc202) {
        this.srvc202 = srvc202;
    }

    public boolean isSrvc211() {
        return srvc211;
    }

    public void setSrvc211(boolean srvc211) {
        this.srvc211 = srvc211;
    }

    public boolean isSrvc212() {
        return srvc212;
    }

    public void setSrvc212(boolean srvc212) {
        this.srvc212 = srvc212;
    }

    public boolean isSrvc221() {
        return srvc221;
    }

    public void setSrvc221(boolean srvc221) {
        this.srvc221 = srvc221;
    }

    public boolean isSrvc301() {
        return srvc301;
    }

    public void setSrvc301(boolean srvc301) {
        this.srvc301 = srvc301;
    }

    public boolean isSrvc302() {
        return srvc302;
    }

    public void setSrvc302(boolean srvc302) {
        this.srvc302 = srvc302;
    }

    public boolean isSrvc311() {
        return srvc311;
    }

    public void setSrvc311(boolean srvc311) {
        this.srvc311 = srvc311;
    }

    public boolean isSrvc312() {
        return srvc312;
    }

    public void setSrvc312(boolean srvc312) {
        this.srvc312 = srvc312;
    }

    public boolean isSrvc321() {
        return srvc321;
    }

    public void setSrvc321(boolean srvc321) {
        this.srvc321 = srvc321;
    }

    public boolean isSrvc322() {
        return srvc322;
    }

    public void setSrvc322(boolean srvc322) {
        this.srvc322 = srvc322;
    }

    public boolean isSrvc331() {
        return srvc331;
    }

    public void setSrvc331(boolean srvc331) {
        this.srvc331 = srvc331;
    }

    public boolean isSrvc332() {
        return srvc332;
    }

    public void setSrvc332(boolean srvc332) {
        this.srvc332 = srvc332;
    }

    public boolean isSrvc333() {
        return srvc333;
    }

    public void setSrvc333(boolean srvc333) {
        this.srvc333 = srvc333;
    }
    
    public boolean isSrvc334() {
        return srvc334;
    }

    public void setSrvc334(boolean srvc334) {
        this.srvc334 = srvc334;
    }
    
    public boolean isSrvc341() {
        return srvc341;
    }

    public void setSrvc341(boolean srvc341) {
        this.srvc341 = srvc341;
    }

    public boolean isSrvc401() {
        return srvc401;
    }

    public void setSrvc401(boolean srvc401) {
        this.srvc401 = srvc401;
    }

    public boolean isSrvc402() {
        return srvc402;
    }

    public void setSrvc402(boolean srvc402) {
        this.srvc402 = srvc402;
    }

    public boolean isSrvc411() {
        return srvc411;
    }

    public void setSrvc411(boolean srvc411) {
        this.srvc411 = srvc411;
    }

    public boolean isSrvc412() {
        return srvc412;
    }

    public void setSrvc412(boolean srvc412) {
        this.srvc412 = srvc412;
    }

    public boolean isSrvc421() {
        return srvc421;
    }

    public void setSrvc421(boolean srvc421) {
        this.srvc421 = srvc421;
    }

    public boolean isSrvc422() {
        return srvc422;
    }

    public void setSrvc422(boolean srvc422) {
        this.srvc422 = srvc422;
    }

    public boolean isSrvc431() {
        return srvc431;
    }

    public void setSrvc431(boolean srvc431) {
        this.srvc431 = srvc431;
    }

    public boolean isSrvc432() {
        return srvc432;
    }

    public void setSrvc432(boolean srvc432) {
        this.srvc432 = srvc432;
    }

    public boolean isSrvc441() {
        return srvc441;
    }

    public void setSrvc441(boolean srvc441) {
        this.srvc441 = srvc441;
    }

    public boolean isSrvc442() {
        return srvc442;
    }

    public void setSrvc442(boolean srvc442) {
        this.srvc442 = srvc442;
    }

    public boolean isSrvc451() {
        return srvc451;
    }

    public void setSrvc451(boolean srvc451) {
        this.srvc451 = srvc451;
    }

    public boolean isSrvc452() {
        return srvc452;
    }

    public void setSrvc452(boolean srvc452) {
        this.srvc452 = srvc452;
    }

    public boolean isSrvc501() {
        return srvc501;
    }

    public void setSrvc501(boolean srvc501) {
        this.srvc501 = srvc501;
    }

    public boolean isSrvc502() {
        return srvc502;
    }

    public void setSrvc502(boolean srvc502) {
        this.srvc502 = srvc502;
    }

    public boolean isSrvc511() {
        return srvc511;
    }

    public void setSrvc511(boolean srvc511) {
        this.srvc511 = srvc511;
    }

    public boolean isSrvc512() {
        return srvc512;
    }

    public void setSrvc512(boolean srvc512) {
        this.srvc512 = srvc512;
    }

    public boolean isSrvc601() {
        return srvc601;
    }

    public void setSrvc601(boolean srvc601) {
        this.srvc601 = srvc601;
    }

    public boolean isSrvc602() {
        return srvc602;
    }

    public void setSrvc602(boolean srvc602) {
        this.srvc602 = srvc602;
    }

    public boolean isSrvc701() {
        return srvc701;
    }

    public void setSrvc701(boolean srvc701) {
        this.srvc701 = srvc701;
    }

    public boolean isSrvc702() {
        return srvc702;
    }

    public void setSrvc702(boolean srvc702) {
        this.srvc702 = srvc702;
    }

    public boolean isSrvc711() {
        return srvc711;
    }

    public void setSrvc711(boolean srvc711) {
        this.srvc711 = srvc711;
    }

    public boolean isSrvc712() {
        return srvc712;
    }

    public void setSrvc712(boolean srvc712) {
        this.srvc712 = srvc712;
    }

    public boolean isSrvc721() {
        return srvc721;
    }

    public void setSrvc721(boolean srvc721) {
        this.srvc721 = srvc721;
    }

    public boolean isSrvc722() {
        return srvc722;
    }

    public void setSrvc722(boolean srvc722) {
        this.srvc722 = srvc722;
    }

    public boolean isSrvc801() {
        return srvc801;
    }

    public void setSrvc801(boolean srvc801) {
        this.srvc801 = srvc801;
    }

    public boolean isSrvc802() {
        return srvc802;
    }

    public void setSrvc802(boolean srvc802) {
        this.srvc802 = srvc802;
    }

    public boolean isSrvc811() {
        return srvc811;
    }

    public void setSrvc811(boolean srvc811) {
        this.srvc811 = srvc811;
    }

    public boolean isSrvc812() {
        return srvc812;
    }

    public void setSrvc812(boolean srvc812) {
        this.srvc812 = srvc812;
    }

    public boolean isSrvc9() {
        return srvc9;
    }

    public void setSrvc9(boolean srvc9) {
        this.srvc9 = srvc9;
    }

    public boolean isSrvc901() {
        return srvc901;
    }

    public void setSrvc901(boolean srvc901) {
        this.srvc901 = srvc901;
    }

    public boolean isSrvc902() {
        return srvc902;
    }

    public void setSrvc902(boolean srvc902) {
        this.srvc902 = srvc902;
    }

    public boolean isSrvc903() {
        return srvc903;
    }

    public void setSrvc903(boolean srvc903) {
        this.srvc903 = srvc903;
    }

    @Override
    public String toString() {
        return "UsuarioAplicacionDTO{" + "error=" + error + ", usuario=" + usuario + ", nombre=" + nombre + ", sistema=" + sistema + ", normal=" + normal + ", auditoria=" + auditoria + ", direccionIP=" + direccionIP + ", token=" + token + ", claveEstatica=" + claveEstatica + ", usuarioAcceso=" + usuarioAcceso + '}';
    }
    
}
