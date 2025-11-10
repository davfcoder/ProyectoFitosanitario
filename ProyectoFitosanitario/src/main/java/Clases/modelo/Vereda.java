/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class Vereda {

    private String idVereda;
    private String codigoDane;
    private String nombre;
    private String idMunicipio;

    public Vereda(String idVereda, String codigoDane, String nombre, String idMunicipio) {
        this.idVereda = idVereda;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
        this.idMunicipio = idMunicipio;
    }

    public Vereda() {
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

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    // En la parte de atributos (junto con los demás campos)
    private String nombreMunicipio;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

}
