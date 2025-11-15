/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class VariedadEspecie {

    private String idVariedad;
    private String nomVariedad;
    private String idEspecie;

    public VariedadEspecie(String idVariedad, String nomVariedad, String idEspecie) {
        this.idVariedad = idVariedad;
        this.nomVariedad = nomVariedad;
        this.idEspecie = idEspecie;
    }   
 

    public VariedadEspecie() {
    }

    public String getIdVariedad() {
        return idVariedad;
    }

    public void setIdVariedad(String idVariedad) {
        this.idVariedad = idVariedad;
    }

    public String getNomVariedad() {
        return nomVariedad;
    }

    public void setNomVariedad(String nomVariedad) {
        this.nomVariedad = nomVariedad;
    }

    public String getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(String idEspecie) {
        this.idEspecie = idEspecie;
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

    public void idEspecie(String idEspv) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
