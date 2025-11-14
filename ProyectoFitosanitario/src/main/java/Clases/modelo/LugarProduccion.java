/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.modelo;

public class LugarProduccion {

    private String idLugarProduccion;
    private String nomLugarProduccion;
    private String nroRegistroICA;
    private String idUsuarioProductor;

    public LugarProduccion(String idLugarProduccion, String nomLugarProduccion, String nroRegistroICA, String idUsuarioProductor) {
        this.idLugarProduccion = idLugarProduccion;
        this.nomLugarProduccion = nomLugarProduccion;
        this.nroRegistroICA = nroRegistroICA;
        this.idUsuarioProductor = idUsuarioProductor;
    }

    public LugarProduccion() {
    } 

    public String getIdLugarProduccion() {
        return idLugarProduccion;
    }

    public void setIdLugarProduccion(String idLugarProduccion) {
        this.idLugarProduccion = idLugarProduccion;
    }

    public String getNomLugarProduccion() {
        return nomLugarProduccion;
    }

    public void setNomLugarProduccion(String nomLugarProduccion) {
        this.nomLugarProduccion = nomLugarProduccion;
    }

    public String getNroRegistroICA() {
        return nroRegistroICA;
    }

    public void setNroRegistroICA(String nroRegistroICA) {
        this.nroRegistroICA = nroRegistroICA;
    }

    public String getIdUsuarioProductor() {
        return idUsuarioProductor;
    }

    public void setIdUsuarioProductor(String idUsuarioProductor) {
        this.idUsuarioProductor = idUsuarioProductor;
    }

    
        //////////////////TRAE NOMBRE USUARIO
    private String nombreUsuario;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
}
