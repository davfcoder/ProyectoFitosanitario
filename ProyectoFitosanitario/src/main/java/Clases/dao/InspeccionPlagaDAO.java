/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import java.sql.*;
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
    
    public boolean eliminar(String idEspecie, String idPlaga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ call PRO_ELIMINSPECCIONPLAGA(?, ?) }";

            cs = con.prepareCall(sql);
            cs.setString(1, idEspecie);
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

    

}
