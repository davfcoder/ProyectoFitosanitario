/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Predio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PredioDAO {

    private CConexion conexion;

    public PredioDAO() {
        this.conexion = new CConexion();
    }

   // CREATE - Insertar predio usando procedimiento almacenado
    public boolean insertar(Predio predio) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incPredio(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, predio.getNumPredial());
            cs.setString(2, predio.getNroRegistroICA());
            cs.setString(3, predio.getNomPredio());
            cs.setString(4, predio.getDireccion());
            cs.setString(5, predio.getCx());
            cs.setString(6, predio.getCy());
            cs.setFloat(7, predio.getAreaTotal());
            cs.setString(8, predio.getIdVereda());
            cs.setString(9, predio.getIdUsuarioPropietario());
            cs.setString(10, predio.getIdLugarProduccion()); // Nuevo campo

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar predio: " + e.getMessage());
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

    // ============================================================
    // READ - Listar todos los predios (usando función almacenada)
    // ============================================================
    public List<Predio> listarTodos() {
        List<Predio> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{ ? = call fun_listarPredios() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            // ✅ Recorrer resultados
            while (rs.next()) {
                Predio p = new Predio();
                p.setIdPredio(rs.getString("id_predio"));
                p.setNumPredial(rs.getString("num_predial"));
                p.setNroRegistroICA(rs.getString("nro_registro_ica"));
                p.setNomPredio(rs.getString("nom_predio"));
                p.setDireccion(rs.getString("direccion"));
                p.setNombreVereda(rs.getString("nombre_vereda"));
                p.setNombreUsuario(rs.getString("nombre_propietario"));
                p.setNombreLugarProduccion(rs.getString("nom_lugar_produccion"));

                lista.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar predios: " + e.getMessage());
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

    // ============================================================
    // UPDATE - Actualizar predio
    // ============================================================
    public boolean actualizar(Predio predio) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{ call pro_actPredio(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
            cs = con.prepareCall(sql);

            cs.setString(1, predio.getIdPredio());
            cs.setString(2, predio.getNumPredial());
            cs.setString(3, predio.getNroRegistroICA());
            cs.setString(4, predio.getNomPredio());
            cs.setString(5, predio.getDireccion());
            cs.setString(6, predio.getIdVereda());
            cs.setString(7, predio.getCx());
            cs.setString(8, predio.getCy());
            cs.setFloat(9, predio.getAreaTotal());
            cs.setString(10, predio.getIdUsuarioPropietario());
            cs.setString(11, predio.getIdLugarProduccion()); // Nuevo campo


            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar predio: " + e.getMessage());
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

    // ============================================================
    // DELETE - Eliminar predio
    // ============================================================
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimPredio(?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, id);
            cs.execute();

            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar predio: " + e.getMessage());
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

    // ============================================================
    // READ - Buscar predio por ID (usando función con SYS_REFCURSOR)
    // ============================================================
    public Predio buscarPorId(String id) {
        Predio predio = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{ ? = call fun_buscarPredioPorId(?) }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Asignar parámetro de entrada (ID)
            cs.setString(2, id);

            // ✅ Ejecutar
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                predio = new Predio();
                predio.setIdPredio(rs.getString("id_predio"));
                predio.setNumPredial(rs.getString("num_predial"));
                predio.setNroRegistroICA(rs.getString("nro_registro_ica"));
                predio.setNomPredio(rs.getString("nom_predio"));
                predio.setDireccion(rs.getString("direccion"));
                predio.setNombreDepartamento(rs.getString("nombre_departamento"));
                predio.setNombreMunicipio(rs.getString("nombre_municipio"));
                predio.setNombreVereda(rs.getString("nombre_vereda"));
                predio.setCx(rs.getString("cx"));
                predio.setCy(rs.getString("cy"));
                predio.setAreaTotal(rs.getFloat("area_total"));
                predio.setNombreUsuario(rs.getString("nombre_propietario"));
                predio.setNombreLugarProduccion(rs.getString("nombre_lugar_produccion"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar predio: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return predio;
    }

}
