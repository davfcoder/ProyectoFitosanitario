/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;
import java.util.Date;

public class InspeccionReporte {
    // Campos de INSPECCIONFITOSANITARIA
    private String idInspeccion;
    private Date fecInspeccion;
    private int cantidadPlantas;
    private String estadoFenologico;
    private String observaciones;
    
    // Campos combinados/JOIN
    private String asistenteTecnico; // u.nombres || ' ' || u.apellidos
    private String nombreLugarProduccion; // lp.nom_lugar_produccion
    private String numeroLote; // l.numero
    private String nombreEspecie; // ev.nom_especie

    // Campo calculado
    private int totalPlagas;

    public InspeccionReporte() {
    }

    // --- Getters y Setters ---

    public String getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(String idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public Date getFecInspeccion() {
        return fecInspeccion;
    }

    public void setFecInspeccion(Date fecInspeccion) {
        this.fecInspeccion = fecInspeccion;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAsistenteTecnico() {
        return asistenteTecnico;
    }

    public void setAsistenteTecnico(String asistenteTecnico) {
        this.asistenteTecnico = asistenteTecnico;
    }

    public String getNombreLugarProduccion() {
        return nombreLugarProduccion;
    }

    public void setNombreLugarProduccion(String nombreLugarProduccion) {
        this.nombreLugarProduccion = nombreLugarProduccion;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getNombreEspecie() {
        return nombreEspecie;
    }

    public void setNombreEspecie(String nombreEspecie) {
        this.nombreEspecie = nombreEspecie;
    }

    public int getTotalPlagas() {
        return totalPlagas;
    }

    public void setTotalPlagas(int totalPlagas) {
        this.totalPlagas = totalPlagas;
    }
}
