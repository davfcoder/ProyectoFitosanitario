/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

import java.sql.Date;

public class InspeccionFitosanitaria {

    private String idInspeccion;
    private int cantidadPlantas;
    private String estadoFenologico;
    private Date fecInspeccion;
    private String observaciones;
    private String idUsuarioAsistente;
    private String idLote;

    public InspeccionFitosanitaria(String idInspeccion, int cantidadPlantas, String estadoFenologico, Date fecInspeccion, String observaciones, String idUsuarioAsistente, String idLote) {
        this.idInspeccion = idInspeccion;
        this.cantidadPlantas = cantidadPlantas;
        this.estadoFenologico = estadoFenologico;
        this.fecInspeccion = fecInspeccion;
        this.observaciones = observaciones;
        this.idUsuarioAsistente = idUsuarioAsistente;
        this.idLote = idLote;
    }
    
    public InspeccionFitosanitaria() {
    }

    public String getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(String idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public int getCantidadPlantas() {
        return cantidadPlantas;
    }

    public void setCantidadPlantas(int cantidadPlantas) {
        this.cantidadPlantas = cantidadPlantas;
    }

    public String getEstadoFenologico() {
        return estadoFenologico;
    }

    public void setEstadoFenologico(String estadoFenologico) {
        this.estadoFenologico = estadoFenologico;
    }

    public Date getFecInspeccion() {
        return fecInspeccion;
    }

    public void setFecInspeccion(Date fecInspeccion) {
        this.fecInspeccion = fecInspeccion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdUsuarioAsistente() {
        return idUsuarioAsistente;
    }

    public void setIdUsuarioAsistente(String idUsuarioAsistente) {
        this.idUsuarioAsistente = idUsuarioAsistente;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    //////////////////TRAE NOMBRE LUGAR PRODUCCION
    private String nombreLugarProduccion;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreLugarProduccion() {
        return nombreLugarProduccion;
    }

    public void setNombreLugarProduccion(String nombreLugarProduccion) {
        this.nombreLugarProduccion = nombreLugarProduccion;
    }
    
        //////////////TRAE Nombre Lote 
    // En la parte de atributos (junto con los demás campos)
    private String numeroLote;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    //////////////////TRAE NOMBRE USUARIO ASISTENTE
    private String nombreUsuario;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
