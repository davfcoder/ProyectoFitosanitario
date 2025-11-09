/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class Vereda {
    private String idVereda;
    private String codigoDane;
    private String nombre;

    public Vereda(String idVereda, String codigoDane, String nombre) {
        this.idVereda = idVereda;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }

    public String getIdVereda() {
        return idVereda;
    }

    public void setIdVereda(String idVereda) {
        this.idVereda = idVereda;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
}
