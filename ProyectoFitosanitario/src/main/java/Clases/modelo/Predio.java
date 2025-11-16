/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class Predio {

    private String idPredio;
    private String numPredial;
    private String nroRegistroICA;
    private String nomPredio;
    private String direccion;
    private String idVereda;
    private String cx;
    private String cy;
    private Float areaTotal;
    private String idUsuarioPropietario;
    private String idLugarProduccion;

    public Predio(String idPredio, String numPredial, String nroRegistroICA, String nomPredio, String direccion, String idVereda, String cx, String cy, Float areaTotal, String idUsuarioPropietario, String idLugarProduccion) {
        this.idPredio = idPredio;
        this.numPredial = numPredial;
        this.nroRegistroICA = nroRegistroICA;
        this.nomPredio = nomPredio;
        this.direccion = direccion;
        this.idVereda = idVereda;
        this.cx = cx;
        this.cy = cy;
        this.areaTotal = areaTotal;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.idLugarProduccion = idLugarProduccion;
    }


    public Predio() {
    } 

    public String getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(String idPredio) {
        this.idPredio = idPredio;
    }

    public String getNumPredial() {
        return numPredial;
    }

    public void setNumPredial(String numPredial) {
        this.numPredial = numPredial;
    }

    public String getNroRegistroICA() {
        return nroRegistroICA;
    }

    public void setNroRegistroICA(String nroRegistroICA) {
        this.nroRegistroICA = nroRegistroICA;
    }

    public String getNomPredio() {
        return nomPredio;
    }

    public void setNomPredio(String nomPredio) {
        this.nomPredio = nomPredio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdVereda() {
        return idVereda;
    }

    public void setIdVereda(String idVereda) {
        this.idVereda = idVereda;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public Float getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Float areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getIdUsuarioPropietario() {
        return idUsuarioPropietario;
    }

    public void setIdUsuarioPropietario(String idUsuarioPropietario) {
        this.idUsuarioPropietario = idUsuarioPropietario;
    }

    public String getIdLugarProduccion() {
        return idLugarProduccion;
    }

    public void setIdLugarProduccion(String idLugarProduccion) {
        this.idLugarProduccion = idLugarProduccion;
    }

    
//////////////TRAE Nombre Municipio 
    // En la parte de atributos (junto con los demás campos)
    private String nombreMunicipio;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }
    
    //////////////////TRAE NOMBRE DEPARTAMENTO
    // En la parte de atributos (junto con los demás campos)
    private String nombreDepartamento;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
    
        //////////////////TRAE NOMBRE VEREDA
    // En la parte de atributos (junto con los demás campos)
    private String nombreVereda;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreVereda() {
        return nombreVereda;
    }

    public void setNombreVereda(String nombreVereda) {
        this.nombreVereda = nombreVereda;
    }
        //////////////////TRAE NOMBRE USUARIO
    private String nombrePropietario;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
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
