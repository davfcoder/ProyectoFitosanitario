/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;
import java.util.ArrayList;
import java.util.List;

public class EspecieVegetal {

    private String idEspecie;
    private String nomEspecie;
    private String nombreComun;
    private String cicloCultivo;
    private List<Plaga> plagas;

    public EspecieVegetal(String idEspecie, String nomEspecie, String nombreComun, String cicloCultivo) {
        this.idEspecie = idEspecie;
        this.nomEspecie = nomEspecie;
        this.nombreComun = nombreComun;
        this.cicloCultivo = cicloCultivo;
    }

    public EspecieVegetal() {
        this.plagas = new ArrayList<>();
    }

    // getters y setters...
    public List<Plaga> getPlagas() {
        return plagas;
    }

    public void setPlagas(List<Plaga> plagas) {
        this.plagas = plagas;
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
