/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Departamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DepartamentoDAO {

    private CConexion conexion;

    public DepartamentoDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar rol usando procedimiento almacenado
    public boolean insertar(Departamento departamento) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call pro_incDepartamento(?, ?)}";
            cs = con.prepareCall(sql);

            // Asignar parámetros (en el mismo orden que en el procedimiento)
            cs.setString(1, departamento.getCodigoDane());
            cs.setString(2, departamento.getNombre());

            // Ejecutar el procedimiento
            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar departamento: " + e.getMessage());
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

    // READ - Listar todos los departamentos
    public List<Departamento> listarTodos() {
        List<Departamento> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{call pro_listarDepartamentos(?)}";
            cs = con.prepareCall(sql);
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();

            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Departamento dep = new Departamento();
                dep.setIdDepartamento(rs.getString("id_departamento"));
                dep.setCodigoDane(rs.getString("codigo_dane"));
                dep.setNombre(rs.getString("nombre"));
                lista.add(dep);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar departamentos: " + e.getMessage());
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

    // UPDATE - Actualizar departamento
    public boolean actualizar(Departamento departamento) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{call pro_actDepartamento(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, departamento.getIdDepartamento());
            cs.setString(2, departamento.getCodigoDane());
            cs.setString(3, departamento.getNombre());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar departamento: " + e.getMessage());
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

    // DELETE - Eliminar departamento
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimDepartamento(?)}";
            cs = con.prepareCall(sql);

            try {
                int idNum = Integer.parseInt(id);
                cs.setInt(1, idNum);
            } catch (NumberFormatException nfe) {
                cs.setString(1, id);
            }

            cs.execute();
            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar departamento: " + e.getMessage());
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

    // READ - Buscar departamento por ID
    public Departamento buscarPorId(String id) {
        Departamento dep = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{call pro_buscarDepartamentoPorId(?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, id);
            cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();

            rs = (ResultSet) cs.getObject(2);

            if (rs.next()) {
                dep = new Departamento();
                dep.setIdDepartamento(rs.getString("id_departamento"));
                dep.setCodigoDane(rs.getString("codigo_dane"));
                dep.setNombre(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar departamento: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dep;
    }

}
