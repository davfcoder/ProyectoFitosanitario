/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class LugarEspecie {

    // Claves primarias compuestas
    private String idLugarProduccion; // ID_LUGAR_PRODUCCION (VARCHAR2(10))
    private String idEspecie;         // ID_ESPECIE (VARCHAR2(10))

    // Campos de detalle de la relación
    private float areaDestCultivo;       // AREA_DEST_CULTIVO (NUMBER(10,2))
    private float capacidadProduccionMax; // CAPACIDAD_PRODUCCION_MAX (NUMBER(10,2))
    
    // Atributo adicional obtenido mediante JOIN para mostrar en la interfaz
    private String nomEspecie;
    // --- Constructor Vacío ---
    public LugarEspecie() {
    }

    // --- Constructor Completo ---
    public LugarEspecie(String idLugarProduccion, String idEspecie, float areaDestCultivo, float capacidadProduccionMax) {
        this.idLugarProduccion = idLugarProduccion;
        this.idEspecie = idEspecie;
        this.areaDestCultivo = areaDestCultivo;
        this.capacidadProduccionMax = capacidadProduccionMax;
    }

    // --- Getters y Setters ---

    public String getIdLugarProduccion() {
        return idLugarProduccion;
    }

    public void setIdLugarProduccion(String idLugarProduccion) {
        this.idLugarProduccion = idLugarProduccion;
    }

    public String getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(String idEspecie) {
        this.idEspecie = idEspecie;
    }

    public float getAreaDestCultivo() {
        return areaDestCultivo;
    }

    public void setAreaDestCultivo(float areaDestCultivo) {
        this.areaDestCultivo = areaDestCultivo;
    }

    public float getCapacidadProduccionMax() {
        return capacidadProduccionMax;
    }

    public void setCapacidadProduccionMax(float capacidadProduccionMax) {
        this.capacidadProduccionMax = capacidadProduccionMax;
    }
    
    public String getNomComun() {
        return nomEspecie;
    }
    public void setNomComun(String nomEspecie) {
        this.nomEspecie = nomEspecie;
    }
}
