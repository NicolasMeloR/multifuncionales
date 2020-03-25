//package com.davivienda.sara.entitys;
//
//import com.davivienda.sara.constantes.EstructuraBase;
//import com.davivienda.sara.constantes.TipoRegistro;
//import java.io.Serializable;
//import java.util.Calendar;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Transient;
//import javax.persistence.Version;
//
///**
// * Entity class EdcRegistro
// * EdcRegistro.java
// *
// * Fecha       :24 de enero de 2007, 10:31 PM
// *
// * Descripci�n :Contiene la informaci�n de un registro guardado en el archivo de EDC
// *
// * @author     :jjvargas
// */
//
//@Entity
//@NamedQueries( {
//    @NamedQuery(
//    name = "EdcRegistro.RegistroUnico",
//            query = "select object(obj) from EdcRegistro obj where obj.idEdcRegistro = :idEdcRegistro"),
//    @NamedQuery(
//    name = "EdcRegistro.Todos",
//            query = "select object(obj) from EdcRegistro obj order by obj.idEdcRegistro"),
//    @NamedQuery(
//    name = "EdcRegistro.PorEdcCargue",
//            query = "select object(obj) from EdcRegistro obj where obj.edcCargue.idEdcCargue = :idEdcCargue order by obj.idEdcRegistro")
//            
//})
//
//@Table(name = "EDCREGISTRO")
//public class EdcRegistro implements Serializable {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdEdcRegistro")
//    @SequenceGenerator(name = "IdEdcRegistro", sequenceName = "IdEdcRegistro", allocationSize = 100)        
//    @Column(name = "IDEDCREGISTRO", nullable = false)
//    private Integer idEdcRegistro;
//    
//    @Column(name = "SECUENCIA", nullable = false)
//    private Short secuencia;
//    
//    @Column(name = "FECHA", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Calendar fecha;
//    
//    @Column(name = "TIPOREGISTRO")
//    private Character tipoRegistro;
//    
//    @Column(name = "INFORMACION")
//    private String informacion;
//    
//    @Version
//    private Integer version;
//    
//    @JoinColumn(name = "IDEDCCARGUE", referencedColumnName = "IDEDCCARGUE")
//    @ManyToOne(cascade = CascadeType.ALL)
//    private EdcCargue edcCargue;
//    
//    @Transient
//    String codigoCajero;
//
//    
//    
//    
//    /** Crea una nueva instancia de EdcRegistro */
//    public EdcRegistro() {
//    }
//
//    
//    /**
//     * Creates a new instance of EdcRegistro with the specified values.
//     * 
//     * @param idedcregistro 
//     * 
//     */
//    public EdcRegistro(Integer idedcregistro) {
//        this.idEdcRegistro = idedcregistro;
//    }
//    
//    /**
//     * Creates a new instance of EdcRegistro with the specified values.
//     * 
//     * 
//     * 
//     * @param idEdcRegistro the idEdcRegistro of the EdcRegistro
//     * @param SECUENCIA the SECUENCIA of the EdcRegistro
//     * @param FECHA the FECHA of the EdcRegistro
//     */
//    public EdcRegistro(Integer idedcregistro, Short secuencia, Calendar fecha) {
//        this.idEdcRegistro = idedcregistro;
//        this.secuencia = secuencia;
//        this.fecha = fecha;
//    }
//    
//    public void crearRegistro(String registro) throws Exception {
//        Character tipoRegistro;
//        if (registro != null) {
//            tipoRegistro = registro.charAt(0);
//            String tituloTran = registro.substring(EstructuraBase.INFORMACION.posIni, EstructuraBase.INFORMACION.posIni + 4);
//            codigoCajero = registro.substring(EstructuraBase.CAJERO.posIni, EstructuraBase.CAJERO.posFin);
//            if (tipoRegistro == '1' && !tituloTran.equals("TRAN")) {
//                tipoRegistro = TipoRegistro.TRANSACCION_SUPERVISOR.tipo;
//            }
//            if (tipoRegistro != TipoRegistro.REGISTRO_INICIAL.tipo) {
//                setTipoRegistro(tipoRegistro);
//                setSecuencia(Short.valueOf(registro.substring(EstructuraBase.SECUENCIA.posIni,
//                        EstructuraBase.SECUENCIA.posFin)));
//                setFecha(registro.substring(EstructuraBase.FECHA.posIni, EstructuraBase.FECHA.posFin));
//                setInformacion(registro.substring(EstructuraBase.INFORMACION.posIni, registro.length()));
//            }
//        }
//        
//    }
//    
//    
//    /**
//     * Gets the idEdcRegistro of this EdcRegistro.
//     * 
//     * @return the idEdcRegistro
//     */
//    public Integer getIdEdcRegistro() {
//        return this.idEdcRegistro;
//    }
//    
//    /**
//     * Sets the idEdcRegistro of this EdcRegistro to the specified value.
//     * 
//     * @param idEdcRegistro the new idEdcRegistro
//     */
//    public void setIdEdcRegistro(Integer idedcregistro) {
//        this.idEdcRegistro = idedcregistro;
//    }
//    
//    /**
//     * Gets the SECUENCIA of this EdcRegistro.
//     * 
//     * @return the SECUENCIA
//     */
//    public Short getSecuencia() {
//        return this.secuencia;
//    }
//    
//    /**
//     * Sets the SECUENCIA of this EdcRegistro to the specified value.
//     * 
//     * @param SECUENCIA the new SECUENCIA
//     */
//    public void setSecuencia(Short secuencia) {
//        this.secuencia = secuencia;
//    }
//    
//    /**
//     * Gets the FECHA of this EdcRegistro.
//     * 
//     * @return the FECHA
//     */
//    public Calendar getFecha() {
//        return this.fecha;
//    }
//    
//    
//    /**
//     * Sets the FECHA of this EdcRegistro to the specified value.
//     * 
//     * @param FECHA the new FECHA
//     */
//    public void setFecha(Calendar fecha) {
//        this.fecha = fecha;
//    }
//    
//    public void setFecha(String fecha) throws Exception {
//        // formato mmddyyhhmmss
//        if (fecha != null) {
//                this.fecha = com.davivienda.utilidades.conversion.Cadena.aCalendar(fecha, "MMddyyHHmmss");
//        }
//    }
//    
//    
//    /**
//     * Gets the tipoRegistro of this EdcRegistro.
//     * 
//     * @return the tipoRegistro
//     */
//    public Character getTipoRegistro() {
//        return this.tipoRegistro;
//    }
//    
//    /**
//     * Sets the tipoRegistro of this EdcRegistro to the specified value.
//     * 
//     * @param tipoRegistro the new tipoRegistro
//     */
//    public void setTipoRegistro(Character tiporegistro) {
//        this.tipoRegistro = tiporegistro;
//    }
//    
//    /**
//     * Gets the INFORMACION of this EdcRegistro.
//     * 
//     * @return the INFORMACION
//     */
//    public String getInformacion() {
//        return this.informacion;
//    }
//    
//    /**
//     * Sets the INFORMACION of this EdcRegistro to the specified value.
//     * 
//     * @param INFORMACION the new INFORMACION
//     */
//    public void setInformacion(String informacion) {
//        this.informacion = informacion;
//    }
//    
//    /**
//     * Gets the version of this EdcRegistro.
//     * @return the version
//     */
//    public Integer getVersion() {
//        return this.version;
//    }
//    
//    /**
//     * Sets the version of this EdcRegistro to the specified value.
//     * @param version the new version
//     */
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
//    
//    /**
//     * Gets the edcCargue of this EdcRegistro.
//     * 
//     * @return the edcCargue
//     */
//    public EdcCargue getEdcCargue() {
//        return this.edcCargue;
//    }
//    
//    /**
//     * Sets the edcCargue of this EdcRegistro to the specified value.
//     * 
//     * @param edcCargue the new edcCargue
//     */
//    public void setEdcCargue(EdcCargue idedccargue) {
//        this.edcCargue = idedccargue;
//    }
//    
//    /**
//     * Returns a hash code value for the object.  This implementation computes
//     * a hash code value based on the id fields in this object.
//     * @return a hash code value for this object.
//     */
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (this.idEdcRegistro != null ? this.idEdcRegistro.hashCode() : 0);
//        return hash;
//    }
//    
//    /**
//     * Determines whether another object is equal to this EdcRegistro.  The result is
//     * <code>true</code> if and only if the argument is not null and is a EdcRegistro object that
//     * has the same id field values as this object.
//     * @param object the reference object with which to compare
//     * @return <code>true</code> if this object is the same as the argument;
//     * <code>false</code> otherwise.
//     */
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof EdcRegistro)) {
//            return false;
//        }
//        EdcRegistro other = (EdcRegistro)object;
//        if (this.idEdcRegistro != other.idEdcRegistro && (this.idEdcRegistro == null || !this.idEdcRegistro.equals(other.idEdcRegistro))) return false;
//        return true;
//    }
//    
//    /**
//     * Returns a string representation of the object.  This implementation constructs
//     * that representation based on the id fields.
//     * @return a string representation of the object.
//     */
//    @Override
//    public String toString() {      StringBuffer sb = new StringBuffer();
//        sb.append("\n");
//        sb.append("Cajero      : " + this.edcCargue.getCodigoCajero() + "\n");
//        sb.append("Registro    : " + this.getTipoRegistro() + "\n");
//        sb.append("Secuencia   : " + this.secuencia + "\n");
//        sb.append("Fecha       : " + com.davivienda.utilidades.conversion.Fecha.aCadena(this.fecha, "yyyy-MM-dd") + "\n");
//        sb.append("Hora        : " + com.davivienda.utilidades.conversion.Fecha.aCadena(this.fecha, "HH:mm:ss") + "\n");
//        sb.append("Informaci�n : " + this.informacion + "\n");
//        return sb.toString();
//    }
//    
//}
