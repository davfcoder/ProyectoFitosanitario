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

public class DashboardDAO {

    private CConexion conexion;

    public DashboardDAO() {
        this.conexion = new CConexion();
    }
    
    public int obtenerConteoTotal(String nombreFuncion) {
        Connection con = null;
        CallableStatement cs = null;
        int total = 0;

        try {
            con = conexion.estableceConexion();
            // Llamada a la función escalar
            String sql = "{ ? = call " + nombreFuncion + "() }"; 

            cs = con.prepareCall(sql);
            cs.registerOutParameter(1, java.sql.Types.NUMERIC); // El tipo de retorno es NUMBER

            cs.execute();

            total = cs.getInt(1); // Obtener el resultado del conteo

        } catch (SQLException ex) {
            System.err.println("Error al obtener conteo para " + nombreFuncion + ": " + ex.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return total;
    }
}
    

