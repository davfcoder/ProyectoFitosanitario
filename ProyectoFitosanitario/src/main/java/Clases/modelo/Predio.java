/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class Predio {

    private String idPredio;
    private String numPredial;
    private String nomPredio;
    private String idVereda;
    private String direccion;
    private String cx;
    private String cy;
    private Double areaTotal;
    private String idUsuarioPropietario;
    private String nroRegistroICA;

    // Constructor vacío
    public Predio() {
    }

    public Predio(String idPredio, String numPredial, String nomPredio, String idVereda, String direccion, String cx, String cy, Double areaTotal, String idUsuarioPropietario, String nroRegistroICA) {
        this.idPredio = idPredio;
        this.numPredial = numPredial;
        this.nomPredio = nomPredio;
        this.idVereda = idVereda;
        this.direccion = direccion;
        this.cx = cx;
        this.cy = cy;
        this.areaTotal = areaTotal;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.nroRegistroICA = nroRegistroICA;
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

    public String getNomPredio() {
        return nomPredio;
    }

    public void setNomPredio(String nomPredio) {
        this.nomPredio = nomPredio;
    }

    public String getIdVereda() {
        return idVereda;
    }

    public void setIdVereda(String idVereda) {
        this.idVereda = idVereda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public Double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getIdUsuarioPropietario() {
        return idUsuarioPropietario;
    }

    public void setIdUsuarioPropietario(String idUsuarioPropietario) {
        this.idUsuarioPropietario = idUsuarioPropietario;
    }

    public String getNroRegistroICA() {
        return nroRegistroICA;
    }

    public void setNroRegistroICA(String nroRegistroICA) {
        this.nroRegistroICA = nroRegistroICA;
    }



}
