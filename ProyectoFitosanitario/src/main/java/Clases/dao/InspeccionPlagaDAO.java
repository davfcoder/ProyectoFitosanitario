/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.InspeccionPlaga;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class InspeccionPlagaDAO {
    private final CConexion conexion = new CConexion();
    
    public boolean insertarInspecPlaga(String idInspeccion, String idPlaga) {
        Connection con = null;
        CallableStatement cs = null;
        
        try {
            con = conexion.estableceConexion();
            String sql = "{ call PRO_INCINSPECCIONPLAGA (?, ?) }";

            cs = con.prepareCall(sql);
            cs.setString(1, idInspeccion);
            cs.setString(2, idPlaga);

            cs.execute();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al insertar relación Inspeccion-Plaga: " + ex.getMessage());
            return false;

        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean insertarInspecPlagaCantidad(String idInspeccion, String idPlaga, int cantidadInfest, float porcentajeInfest) {
        Connection con = null;
        CallableStatement cs = null;
        
        try {
            con = conexion.estableceConexion();
            String sql = "{ call PRO_INCINSPECCIONPLAGACANTIDAD(?, ?, ?, ?) }";

            cs = con.prepareCall(sql);
            cs.setString(1, idInspeccion);
            cs.setString(2, idPlaga);
            cs.setInt(3, cantidadInfest);
            cs.setFloat(4, porcentajeInfest);

            cs.execute();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al insertar relación Inspeccion-Plaga: " + ex.getMessage());
            return false;

        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean eliminar(String idInspeccion, String idPlaga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ call PRO_ELIMINSPECCIONPLAGA(?, ?) }";

            cs = con.prepareCall(sql);
            cs.setString(1, idInspeccion);
            cs.setString(2, idPlaga);

            cs.execute();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                "Error al eliminar relación Inspección-Plaga: " + ex.getMessage());
            return false;

        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public List<InspeccionPlaga> listarPlagasInspeccion(String idInspeccion) {
        List<InspeccionPlaga> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            //  Llamada a la función que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call FUN_LISTAR_PLAGAS_INSPECCION(?) }";
            cs = con.prepareCall(sql);

            //  Registrar el parámetro de salida (el cursor devuelto por la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            
            //  Establecer el parámetro de entrada para la inspección específica
            cs.setString(2, idInspeccion);

            //  Ejecutar la función
            cs.execute();

            //  Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            //  Recorrer resultados y llenar la lista
            while (rs.next()) {
                InspeccionPlaga ip = new InspeccionPlaga();
                // Mapeamos los resultados de la consulta JOIN
                ip.setIdPlaga(rs.getString("ID_PLAGA")); // El ID real lo ocultamos/no lo traemos si no es necesario
                ip.setNomComun(rs.getString("Nombre_Plaga")); // Usamos el alias de la función SQL
                ip.setCantidadPlantasInfestadas(rs.getInt("Cantidad_Infestada"));
                ip.setPorcentajeInfestacion(rs.getFloat("Porcentaje_Infestacion")); 

                lista.add(ip);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar plagas de la inspeccion: " + e.getMessage());
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
}
