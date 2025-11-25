/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class InspeccionPlaga {

    // Atributos que mapean a las columnas de la tabla INSPECCIONPLAGA
    private String idInspeccion;
    private String idPlaga;
    private int cantidadPlantasInfestadas;
    private float porcentajeInfestacion; // Usamos float o double para el porcentaje

    // Atributo adicional obtenido mediante JOIN para mostrar en la interfaz
    private String nomEspecie; 

    /**
     * Constructor vacío por defecto.
     */
    public InspeccionPlaga() {
    }
    // --- Métodos Getters y Setters ---
    public String getIdInspeccion() {
        return idInspeccion;
    }
    public void setIdInspeccion(String idInspeccion) {
        this.idInspeccion = idInspeccion;
    }
    public String getIdPlaga() {
        return idPlaga;
    }
    public void setIdPlaga(String idPlaga) {
        this.idPlaga = idPlaga;
    }
    public int getCantidadPlantasInfestadas() {
        return cantidadPlantasInfestadas;
    }
    public void setCantidadPlantasInfestadas(int cantidadPlantasInfestadas) {
        this.cantidadPlantasInfestadas = cantidadPlantasInfestadas;
    }
    public float getPorcentajeInfestacion() {
        return porcentajeInfestacion;
    }
    public void setPorcentajeInfestacion(float porcentajeInfestacion) {
        this.porcentajeInfestacion = porcentajeInfestacion;
    }
    public String getNomEspecie() {
        return nomEspecie;
    }
    public void setNomEspecie(String nomEspecie) {
        this.nomEspecie = nomEspecie;
    }
}
