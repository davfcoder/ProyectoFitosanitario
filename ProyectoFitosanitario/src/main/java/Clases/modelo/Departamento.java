/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class Departamento {
    private String idDepartamento;
    private String codigoDane;
    private String nombre;
    
    public Departamento() {
    }

    public Departamento(String idDepartamento, String codigoDane, String nombre) {
        this.idDepartamento = idDepartamento;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
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

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombre = nombre;
    }

    
    
    
}
