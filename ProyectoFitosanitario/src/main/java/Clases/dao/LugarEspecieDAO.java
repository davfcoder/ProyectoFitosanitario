/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Clases.modelo.LugarEspecie;

public class LugarEspecieDAO {
    private final CConexion conexion = new CConexion();
    
    public boolean insertar(String idLugarProduccion, String idEspecie) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedure de inserción
            String sql = "{ call pro_insLugarEspecie(?, ?) }";
            cs = con.prepareCall(sql);

            cs.setString(1, idLugarProduccion);
            cs.setString(2, idEspecie);

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al asociar la especie: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualiza los campos de área y capacidad para una relación existente.
     */
    public boolean actualizarDetalles(String idLugarProduccion, String idEspecie, float area, float capacidad) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedure de actualización
            String sql = "{ call pro_actLugarEspecieDetalle(?, ?, ?, ?) }";
            cs = con.prepareCall(sql);

            cs.setString(1, idLugarProduccion);
            cs.setString(2, idEspecie);
            cs.setFloat(3, area);
            cs.setFloat(4, capacidad);

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar detalles de la especie: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<LugarEspecie> listarEspeciesAsociadasConDetalles(String idLugarProduccion) {
        // Ahora devuelve una lista de objetos LugarEspecie, que contienen los IDs, área y capacidad.
        List<LugarEspecie> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // Llama a la función FUN_LISTAR_ESPECIES_LUGAR que creamos anteriormente
            String sql = "{ ? = call FUN_LISTAR_ESPECIES_LUGAR(?) }";
            cs = con.prepareCall(sql);

            // Parámetro de entrada: ID del Lugar de Producción
            cs.setString(2, idLugarProduccion);

            // Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            // Recorrer resultados
            while (rs.next()) {
                LugarEspecie le = new LugarEspecie();

                // Mapeo de los IDs (claves)
                le.setIdEspecie(rs.getString("ID_ESPECIE")); // Lo obtenemos del parámetro de entrada
                le.setNomComun(rs.getString("Nombre_Especie")); // El ID de la Especie viene del cursor

                // Mapeo de los campos de detalle (Área y Capacidad)
                le.setAreaDestCultivo(rs.getFloat("Area_Destinada"));
                le.setCapacidadProduccionMax(rs.getFloat("Capacidad_Maxima")); 

                lista.add(le);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar especies asociadas con detalles: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }
    
    public boolean eliminar(String idLugarProduccion, String idEspecie) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedure de eliminación
            String sql = "{ call pro_delLugarEspecie(?, ?) }";
            cs = con.prepareCall(sql);

            // Parámetros de entrada
            cs.setString(1, idLugarProduccion);
            cs.setString(2, idEspecie);

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la asociación de especie: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
