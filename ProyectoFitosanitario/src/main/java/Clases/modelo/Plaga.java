/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class Plaga {

    private String idPlaga;
    private String nomEspecie;
    private String nombreComun;

    public Plaga(String idPlaga, String nomEspecie, String nombreComun) {
        this.idPlaga = idPlaga;
        this.nomEspecie = nomEspecie;
        this.nombreComun = nombreComun;
    }
       
    // Constructor vacío
    public Plaga() {
    }

    public String getIdPlaga() {
        return idPlaga;
    }

    public void setIdPlaga(String idPlaga) {
        this.idPlaga = idPlaga;
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
    
    
    

}
