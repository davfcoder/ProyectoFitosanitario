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

    
//////////////TRAE Nombre Municipio 
    // En la parte de atributos (junto con los demás campos)
    private String nombreMunicipio;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }
    
    //////////////////TRAE NOMBRE DEPARTAMENTO
    // En la parte de atributos (junto con los demás campos)
    private String nombreDepartamento;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
    
        //////////////////TRAE NOMBRE VEREDA
    // En la parte de atributos (junto con los demás campos)
    private String nombreVereda;

// Debajo de los getters y setters existentes, agrega estos:
    public String getNombreVereda() {
        return nombreVereda;
    }

    public void setNombreVereda(String nombreVereda) {
        this.nombreVereda = nombreVereda;
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
