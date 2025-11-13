/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class EspecieVegetal {

    private String idEspecie;
    private String nomEspecie;
    private String nombreComun;
    private String cicloCultivo;

    public EspecieVegetal(String idEspecie, String nomEspecie, String nombreComun, String cicloCultivo) {
        this.idEspecie = idEspecie;
        this.nomEspecie = nomEspecie;
        this.nombreComun = nombreComun;
        this.cicloCultivo = cicloCultivo;
    }
       
    // Constructor vacío
    public EspecieVegetal() {
    }

    public String getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(String idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getNomEspecie() {
        return nomEspecie;
    }

    public void setNomEspecie(String nomEspecie) {
        this.nomEspecie = nomEspecie;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getCicloCultivo() {
        return cicloCultivo;
    }

    public void setCicloCultivo(String cicloCultivo) {
        this.cicloCultivo = cicloCultivo;
    }

    
    
}
