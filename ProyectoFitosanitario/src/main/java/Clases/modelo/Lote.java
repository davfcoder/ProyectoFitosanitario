/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

import java.sql.Date;

public class Lote {

    private String idLote;
    private String numero;
    private Float areaTotal;
    private Date fecSiembra;
    private Date fecEliminacion;
    private String idVariedad;
    private String idLugarProduccion;

    public Lote(String idLote, String numero, Float areaTotal, Date fecSiembra, Date fecEliminacion, String idVariedad, String idLugarProduccion) {
        this.idLote = idLote;
        this.numero = numero;
        this.areaTotal = areaTotal;
        this.fecSiembra = fecSiembra;
        this.fecEliminacion = fecEliminacion;
        this.idVariedad = idVariedad;
        this.idLugarProduccion = idLugarProduccion;
    }
    
    public Lote() {
    } 

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Float areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Date getFecSiembra() {
        return fecSiembra;
    }

    public void setFecSiembra(Date fecSiembra) {
        this.fecSiembra = fecSiembra;
    }

    public Date getFecEliminacion() {
        return fecEliminacion;
    }

    public void setFecEliminacion(Date fecEliminacion) {
        this.fecEliminacion = fecEliminacion;
    }

    public String getIdVariedad() {
        return idVariedad;
    }

    public void setIdVariedad(String idVariedad) {
        this.idVariedad = idVariedad;
    }

    public String getIdLugarProduccion() {
        return idLugarProduccion;
    }

    public void setIdLugarProduccion(String idLugarProduccion) {
        this.idLugarProduccion = idLugarProduccion;
    }
    
    // En la parte de atributos (junto con los demás campos)
    private String nombreEspecie;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreEspecie() {
        return nombreEspecie;
    }

    public void setNombreEspecie(String nombreEspecie) {
        this.nombreEspecie = nombreEspecie;
    }       
   
//////////////TRAE Nombre Variedad 
    // En la parte de atributos (junto con los demás campos)
    private String nombreVariedad;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreVariedad() {
        return nombreVariedad;
    }

    public void setNombreVariedad(String nombreVariedad) {
        this.nombreVariedad = nombreVariedad;
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
}
