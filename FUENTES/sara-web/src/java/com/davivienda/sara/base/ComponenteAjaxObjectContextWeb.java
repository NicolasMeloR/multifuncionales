package com.davivienda.sara.base;

import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.ConfAccesoAplicacionDTO;
import com.davivienda.sara.dto.ConfModulosAplicacionDTO;
import com.davivienda.sara.dto.OccaDTO;
import com.davivienda.sara.dto.OficinaMultiDTO;
import com.davivienda.sara.dto.RegionalDTO;
import com.davivienda.sara.dto.TipoCajeroDTO;
import com.davivienda.sara.dto.TransportadoraDTO;
import com.davivienda.sara.dto.UbicacionDTO;
import com.davivienda.sara.dto.UsuarioAccesoDTO;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.sara.dto.ZonaDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.utilidades.Constantes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ComponenteAjaxObjectContextWeb Descripción : Clase helper de manejo del
 * ObjectContext para ComponenteAjaxSevlet Fecha : 23/05/2008 03:22:16 PM
 *
 * @author : jjvargas
 *
 */
public class ComponenteAjaxObjectContextWeb extends com.davivienda.sara.base.BaseObjectContextWeb {

    /**
     * Crea una nueva instancia de <code>ComponenteAjaxObjectContext</code>.
     *
     * @param request
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @param response
     */
    public ComponenteAjaxObjectContextWeb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }

    /**
     * Retorna el nombre del componente solicitado
     *
     * @return
     */
    public String getNombreComponente() {
        return getAtributoString("nombreComponente");
    }

    /**
     * Retorna el usuario del request
     *
     * @return
     */
    public String getUsuario() {

        return getAtributoString("usuario");

    }

    public List<ConfAccesoAplicacionDTO> getConfAccesoAplicacionCB(Collection<ConfAccesoAplicacion> items) throws Exception {

        List<ConfAccesoAplicacionDTO> accesos = new ArrayList<ConfAccesoAplicacionDTO>();
        for (ConfAccesoAplicacion item : items) {
            ConfAccesoAplicacionDTO confAccesoAplicacionDTO = new ConfAccesoAplicacionDTO();
            if (item != null && item.getUsuarioAplicacion() != null) {
                confAccesoAplicacionDTO.setUsuarioAplicacion(item.getUsuarioAplicacion().getUsuario());
                confAccesoAplicacionDTO.setServicioAplicacion(item.getServicioAplicacion().getDescripcion());
                confAccesoAplicacionDTO.setCodigoServicioAplicacion(item.getConfAccesoAplicacionPK().getServicio());
                confAccesoAplicacionDTO.setActualizar(item.getActualizar().compareTo(new Short("1")) == 0);
                confAccesoAplicacionDTO.setAdministrar(item.getAdministrar().compareTo(new Short("1")) == 0);
                confAccesoAplicacionDTO.setBorrar(item.getBorrar().compareTo(new Short("1")) == 0);
                confAccesoAplicacionDTO.setConsultar(item.getConsultar().compareTo(new Short("1")) == 0);
                confAccesoAplicacionDTO.setCrear(item.getCrear().compareTo(new Short("1")) == 0);
                confAccesoAplicacionDTO.setEjecutar(item.getEjecutar().compareTo(new Short("1")) == 0);
                confAccesoAplicacionDTO.setVersion(item.getVersion());
            }
            accesos.add(confAccesoAplicacionDTO);
        }
        return accesos;
    }

    String getServicioAplicacionCB(Collection<ServicioAplicacion> items) {
//        JSONObject resp = new JSONObject();
//        JSONArray respItems = new JSONArray();
//        for (ServicioAplicacion item : items) {
//            JSONObject itemJSon = new JSONObject();
//            itemJSon.put("idServicio", item.getServicio());
//            itemJSon.put("descripcion", item.getDescripcion());
//            respItems.put(itemJSon);
//        }
//        resp.put("identifier", "idServicio");
//        resp.put("label", "descripcion");
//        resp.put("items", respItems);
//        return resp.toString();
        return "";
    }

    public List<UsuarioAplicacionDTO> getUsuarioAplicacionCB(Collection<UsuarioAplicacion> items) throws Exception {
        List<UsuarioAplicacionDTO> resp = new ArrayList<UsuarioAplicacionDTO>();

        for (UsuarioAplicacion ua : items) {
            UsuarioAplicacionDTO item = this.aArrayUsuarioAplicacion(ua);
            resp.add(item);
        }
        return resp;
    }

    private UsuarioAplicacionDTO aArrayUsuarioAplicacion(UsuarioAplicacion usuarioAplicacion) {
        UsuarioAplicacionDTO resp = new UsuarioAplicacionDTO();
        resp.setUsuario(usuarioAplicacion.getUsuario());
        resp.setNombre(usuarioAplicacion.getNombre());
        resp.setSistema(usuarioAplicacion.getSistema().toString());
        resp.setNormal(usuarioAplicacion.getNormal().toString());
        resp.setAuditoria(usuarioAplicacion.getAuditoria().toString());
        resp.setDireccionIP(usuarioAplicacion.getDireccionIp());
        resp.setToken(usuarioAplicacion.getToken());
        resp.setClaveEstatica(usuarioAplicacion.getClaveEstatica());

        for (ConfAccesoAplicacion confAccesoAplicacion : usuarioAplicacion.getConfAccesoAplicacionCollection()) {
            UsuarioAccesoDTO accesoDto = new UsuarioAccesoDTO();
            accesoDto.setConsultar(confAccesoAplicacion.getConsultar().toString());
            accesoDto.setActualizar(confAccesoAplicacion.getActualizar().toString());
            accesoDto.setEjecutar(confAccesoAplicacion.getEjecutar().toString());
            accesoDto.setAdministrar(confAccesoAplicacion.getAdministrar().toString());
            accesoDto.setCrear(confAccesoAplicacion.getCrear().toString());
            accesoDto.setBorrar(confAccesoAplicacion.getBorrar().toString());
            resp.getUsuarioAcceso().put("srvc" + confAccesoAplicacion.getServicioAplicacion().getServicio().toString(), confAccesoAplicacion.getServicioAplicacion().getServicio().toString());
        }

        return resp;
    }

    /**
     * Retorna todos los tipos de cajero para armar un combobox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<TipoCajeroDTO> getTipoCajeroCB(Collection<TipoCajero> items) throws Exception {
        List<TipoCajeroDTO> lista = new ArrayList<TipoCajeroDTO>();
        for (TipoCajero item : items) {
            TipoCajeroDTO resp = new TipoCajeroDTO();
            resp.setCodigoTipoCajero(item.getCodigoTipoCajero());
            resp.setDescripcion(item.getDescripcion());
            resp.setMarca(item.getMarca());
            resp.setModelo(item.getModelo());
            resp.setSistemaOperativo(item.getSistemaOperativo());
            resp.setAplicativoCajero(item.getAplicativoCajero());
            resp.setFormatoDiarioElectronico(item.getFormatoDiarioElectronico());
            resp.setPapelImpresion(item.getPapelImpresion());
            lista.add(resp);
        }
        return lista;
    }

    /**
     * Retorna todas las Occas para armar un ComboBox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<OccaDTO> getOccaCB(Collection<Occa> items) {
        List<OccaDTO> resp = new ArrayList<OccaDTO>();
        for (Occa item : items) {
            OccaDTO respItems = new OccaDTO();
            respItems.setCodigoOcca(item.getCodigoOcca());
            respItems.setNombre(item.getNombre());
            respItems.setNombreArchivoMovimiento(item.getNombreArchivoMovimiento());
            respItems.setUbicacionArchivoMovimiento(item.getUbicacionArchivoMovimiento());
            respItems.setCodTerminalCaja(item.getCodigoTerminal());
            resp.add(respItems);
        }

        return resp;
    }

    public List<OficinaMultiDTO> getOfimultiCB(Collection<Oficinamulti> items) {
        List<OficinaMultiDTO> resp = new ArrayList<OficinaMultiDTO>();
        for (Oficinamulti item : items) {
            OficinaMultiDTO respItems = new OficinaMultiDTO();
            respItems.setCodigooficinamulti(item.getCodigooficinamulti());
            respItems.setNombre(item.getNombre());
            respItems.setCodigocentroefectivo(item.getCodigocentroefectivo());
            respItems.setVersion(item.getVersion());
            resp.add(respItems);
        }

        return resp;
    }

    /**
     * Retorna todas las ubicaciones para armar un ComboBox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<UbicacionDTO> getUbicacionCB(Collection<Ubicacion> items) {
        List<UbicacionDTO> resp = new ArrayList<UbicacionDTO>();

        for (Ubicacion item : items) {
            UbicacionDTO respItems = new UbicacionDTO();
            respItems.setCodigoUbicacion(item.getCodigoUbicacion());
            respItems.setTipoUbicacion(item.getTipoUbicacion());
            respItems.setSucursal(item.getSucursal());
            respItems.setOficina(item.getOficina());
            respItems.setDireccion(item.getDireccion().replaceAll("\\p{Cc}", ""));
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
            resp.add(respItems);
        }
        return resp;
    }

    public List<RegionalDTO> getRegionalCB(Collection<Regional> items) {
        List<RegionalDTO> resp = new ArrayList<RegionalDTO>();
        for (Regional item : items) {
            RegionalDTO respItems = new RegionalDTO();
            respItems.setIdRegional(item.getIdRegional());
            respItems.setRegional(item.getRegional());;
            resp.add(respItems);
        }
        return resp;
    }

    /**
     * Retorna todas las transportadoras para armar un ComboBox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<TransportadoraDTO> getTransportadoraCB(Collection<Transportadora> items) {
        List<TransportadoraDTO> resp = new ArrayList<TransportadoraDTO>();
        for (Transportadora item : items) {
            TransportadoraDTO respItems = new TransportadoraDTO();
            respItems.setIdTransportadora(item.getIdTransportadora());
            respItems.setNombre(item.getNombre());
            respItems.setDirArchivoArqueo(item.getDirArchivoArqueo());
            respItems.setVersion(item.getVersion());
            resp.add(respItems);
        }
        return resp;
    }

    /**
     * Retorna todos los cajeros para armar un ComboBox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<CajeroDTO> getCajeroCB(Collection<Cajero> items) {
        List<CajeroDTO> resp = new ArrayList<CajeroDTO>();

        for (Cajero item : items) {
            CajeroDTO respItems = new CajeroDTO();
            respItems.setCodigoCajero(item.getCodigoCajero());
            respItems.setSerie(item.getSerie());
            respItems.setNombre(item.getNombre());
            respItems.setVersionAplicativo(item.getVersionAplicativo());
            respItems.setTipoProvision(item.getTipoProvision());
            respItems.setProvisionAprobada(item.getProvisionAprobada());
            respItems.setGav1Denominacion(item.getGav1Denominacion());
            respItems.setGav2Denominacion(item.getGav2Denominacion());
            respItems.setGav3Denominacion(item.getGav3Denominacion());
            respItems.setGav4Denominacion(item.getGav4Denominacion());
            respItems.setNumeroGavetas(item.getNumeroGavetas());
            respItems.setActivo(item.getActivo());
            respItems.setTipoLecturaEDC(item.getTipoLecturaEDC());
            respItems.setUbicacionEDC(item.getUbicacionEDC());
            respItems.setTipoEncripcion(item.getTipoEncripcion());
            respItems.setUbicacion(item.getUbicacion().getCodigoUbicacion());
            respItems.setTipoCajero(item.getTipoCajero().getCodigoTipoCajero());
            respItems.setOcca(item.getOcca().getCodigoOcca());
            respItems.setTransportadora(item.getTransportadora().getIdTransportadora());
            respItems.setIdtransportadora2(item.getIdtransportadora2());
            respItems.setOficinaMulti(item.getOficinaMulti().getCodigooficinamulti());
            respItems.setRed(item.getRed());
            respItems.setHorario(item.getHorario());
            respItems.setDireccion(item.getDireccion());

            resp.add(respItems);
        }
        return resp;
    }

    /**
     * Retorna todos los cajeros esten activos o no para armar un ComboBox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<CajeroDTO> getCajeroSinFiltroCB(Collection<Cajero> items) {
        List<CajeroDTO> resp = new ArrayList<CajeroDTO>();

        for (Cajero item : items) {
            CajeroDTO respItems = new CajeroDTO();
            respItems.setCodigoCajero(item.getCodigoCajero());
            respItems.setSerie(item.getSerie());
            respItems.setNombre(item.getNombre());
            respItems.setVersionAplicativo(item.getVersionAplicativo());
            respItems.setTipoProvision(item.getTipoProvision());
            respItems.setProvisionAprobada(item.getProvisionAprobada());
            respItems.setGav1Denominacion(item.getGav1Denominacion());
            respItems.setGav2Denominacion(item.getGav2Denominacion());
            respItems.setGav3Denominacion(item.getGav3Denominacion());
            respItems.setGav4Denominacion(item.getGav4Denominacion());
            respItems.setNumeroGavetas(item.getNumeroGavetas());
            respItems.setActivo(item.getActivo());
            respItems.setTipoLecturaEDC(item.getTipoLecturaEDC());
            respItems.setUbicacionEDC(item.getUbicacionEDC());
            respItems.setTipoEncripcion(item.getTipoEncripcion());
            respItems.setUbicacion(item.getUbicacion().getCodigoUbicacion());
            respItems.setTipoCajero(item.getTipoCajero().getCodigoTipoCajero());
            respItems.setOcca(item.getOcca().getCodigoOcca());
            respItems.setTransportadora(item.getTransportadora().getIdTransportadora());
            respItems.setIdtransportadora2(item.getIdtransportadora2() == null ? 0 : item.getIdtransportadora2());
            respItems.setOficinaMulti(item.getOficinaMulti().getCodigooficinamulti());
            respItems.setRed(item.getRed() == null ? "0" : item.getRed());
            respItems.setHorario(item.getHorario() == null ? "0" : item.getHorario());
            respItems.setDireccion(item.getDireccion() == null ? "0" : item.getDireccion());
            respItems.setCodigoDispensador(String.valueOf(item.getCodigoDispensador()));
            resp.add(respItems);
        }
        return resp;
    }

    /**
     * Retorna todas las zonas para armar un ComboBox
     *
     * @param items
     * @return String
     * @throws JSONException
     */
    public List<ZonaDTO> getZonaCB(Collection<Zona> items) {
        List<ZonaDTO> resp = new ArrayList<ZonaDTO>();
        for (Zona item : items) {
            ZonaDTO respItems = new ZonaDTO();
            respItems.setIdZona(item.getIdZona());
            respItems.setZona(item.getZona());
            respItems.setDescripcionZona(item.getDescripcionZona());
            resp.add(respItems);
        }
        return resp;
    }

    public List<ConfModulosAplicacionDTO> getConfModulosAplicacionCB(Collection<ConfModulosAplicacion> items) throws Exception {
        List<ConfModulosAplicacionDTO> resp = new ArrayList<ConfModulosAplicacionDTO>();

        for (ConfModulosAplicacion item : items) {
            ConfModulosAplicacionDTO mod = new ConfModulosAplicacionDTO();
            mod.setIdConfModulosAplicacion(item.getConfModulosAplicacionPK().getModulo() + " - " + item.getConfModulosAplicacionPK().getAtributo());
            mod.setModulo(item.getConfModulosAplicacionPK().getModulo());
            mod.setAtributo(item.getConfModulosAplicacionPK().getAtributo());
            mod.setDescripcion(item.getDescripcion());
            mod.setValor(item.getValor());
            resp.add(mod);
        }
        return resp;
    }

    public String getConceptosMovimientoCuadreCB(Collection<ConceptoMovimientoCuadre> items) throws Exception {
//        JSONObject resp = new JSONObject();
//        JSONArray respItems = new JSONArray();
//        for (ConceptoMovimientoCuadre item : items) {
//            JSONObject itemJSon = new JSONObject();
//            itemJSon.put("codigoConcepto", item.getCodigoConcepto());
//            itemJSon.put("descripcion", item.getDescripcion());
//            respItems.put(itemJSon);
//        }
//        resp.put("identifier", "codigoConcepto");
//        resp.put("label", "descripcion");
//        resp.put("items", respItems);
//        return resp.toString();
        return "";
    }

    public String getTiposMovimientoCuadreCB(Collection<TipoMovimientoCuadre> items, boolean blnLinea) throws Exception {
//        JSONObject resp = new JSONObject();
//        JSONArray respItems = new JSONArray();
//        for (TipoMovimientoCuadre item : items) {
////             if ( (item.getCodigoTipoMovimientoCuadre() >= 700 && item.getCodigoTipoMovimientoCuadre() < 800 )
////                        || item.getCodigoTipoMovimientoCuadre() <= 199)
//            if (blnLinea) {
//                if ((item.getCodigoTipoMovimientoCuadre() >= 700 && item.getCodigoTipoMovimientoCuadre() != 712)) {
//                    JSONObject itemJSon = new JSONObject();
//                    itemJSon.put("codigoTipoMovimientoCuadre", item.getCodigoTipoMovimientoCuadre());
//                    itemJSon.put("descripcion", item.getDescripcion());
//                    respItems.put(itemJSon);
//                }
//            } else if ((item.getCodigoTipoMovimientoCuadre() >= 130 && item.getCodigoTipoMovimientoCuadre() <= 199)
//                    || item.getCodigoTipoMovimientoCuadre() == 100) {
//                JSONObject itemJSon = new JSONObject();
//                itemJSon.put("codigoTipoMovimientoCuadre", item.getCodigoTipoMovimientoCuadre());
//                itemJSon.put("descripcion", item.getDescripcion());
//                respItems.put(itemJSon);
//            }
//        }
//        resp.put("identifier", "codigoTipoMovimientoCuadre");
//        resp.put("label", "descripcion");
//        resp.put("items", respItems);
//        return resp.toString();
        return "";
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

    public UsuarioAplicacionDTO getUsuarioAplicacionDTO(UsuarioAplicacion usuarioAplicacion) {

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
        usuarioAplicacionDTO.setUsuarioAplicacion(usuarioAplicacion);

        for (ConfAccesoAplicacion confAccesoAplicacion : usuarioAplicacion.getConfAccesoAplicacionCollection()) {
            UsuarioAccesoDTO accesoDto = new UsuarioAccesoDTO();
            accesoDto.setConsultar(confAccesoAplicacion.getConsultar().toString());
            accesoDto.setActualizar(confAccesoAplicacion.getActualizar().toString());
            accesoDto.setEjecutar(confAccesoAplicacion.getEjecutar().toString());
            accesoDto.setAdministrar(confAccesoAplicacion.getAdministrar().toString());
            accesoDto.setCrear(confAccesoAplicacion.getCrear().toString());
            accesoDto.setBorrar(confAccesoAplicacion.getBorrar().toString());
            
            String a = "srvc" + confAccesoAplicacion.getServicioAplicacion().getServicio().toString();
            UsuarioAccesoDTO b = accesoDto;
            
            //usuarioAplicacionDTO.getUsuarioAcceso().put("srvc" + confAccesoAplicacion.getServicioAplicacion().getServicio().toString(), accesoDto);
            usuarioAplicacionDTO.getUsuarioAcceso().put(a, b);
        }

        return usuarioAplicacionDTO;
    }

    public ConfModulosAplicacionDTO getConfModulosAplicacionDTO(ConfModulosAplicacion item) throws Exception {
        ConfModulosAplicacionDTO resp = new ConfModulosAplicacionDTO();
        resp.setModulo(item.getConfModulosAplicacionPK().getModulo());
        resp.setAtributo(item.getConfModulosAplicacionPK().getAtributo());
        resp.setDescripcion(item.getDescripcion());
        resp.setValor(item.getValor());
        return resp;
    }

    /**
     * Metodo para obtener la ip desde el request
     *
     * @param request
     * @return the ip
     */
    public String getIpFromRequest(HttpServletRequest request) {

        String ip = getIpFromHeader(request, Constantes.HEADER_HTTP_IP_1);
        if (ip == null) {
            ip = getIpFromHeader(request, Constantes.HEADER_HTTP_IP_2);
        }
        if (ip == null) {
            ip = getIpFromHeader(request, Constantes.HEADER_HTTP_IP_3);
        }
        if (ip == null) {
            ip = getIpFromHeader(request, Constantes.HEADER_HTTP_IP_4);
        }
        if (ip == null) {
            ip = getIpFromHeader(request, Constantes.HEADER_HTTP_IP_5);
        }
        if (ip == null) {
            ip = getIpFromHeader(request, Constantes.HEADER_HTTP_IP_6);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private static String getIpFromHeader(HttpServletRequest request, String header) {

        String ip;
        if ((ip = request.getHeader(header)) != null) {
            StringTokenizer tokenizer = new StringTokenizer(ip, Constantes.CARACTER_COMA);
            while (tokenizer.hasMoreElements()) {
                ip = tokenizer.nextToken().trim();
                return ip;
            }
        }
        return null;
    }
}
