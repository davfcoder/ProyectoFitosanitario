/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Vereda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VeredaDAO {

    private CConexion conexion;

    public VeredaDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar rol usando procedimiento almacenado
    public boolean insertar(Vereda vereda) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incVereda(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, vereda.getCodigoDane());
            cs.setString(2, vereda.getNombre());
            cs.setString(3, vereda.getIdMunicipio());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar vereda: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// ✅ READ - Listar todas las veredas (usando función almacenada)
    public List<Vereda> listarTodos() {
        List<Vereda> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // ✅ Llamada correcta a la función que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call fun_listarVeredas() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar el parámetro de salida (el cursor devuelto por la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            // ✅ Recorrer resultados y llenar la lista
            while (rs.next()) {
                Vereda v = new Vereda();
                v.setIdVereda(rs.getString("id_vereda"));
                v.setCodigoDane(rs.getString("codigo_dane"));
                v.setNombre(rs.getString("nombre_vereda"));        // Alias creado en SQL
                v.setNombreMunicipio(rs.getString("nombre_municipio")); // Alias creado en SQL
                lista.add(v);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar veredas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    // UPDATE - Actualizar vereda
    public boolean actualizar(Vereda vereda) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actVereda(?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, vereda.getIdVereda());
            cs.setString(2, vereda.getCodigoDane());
            cs.setString(3, vereda.getNombre());
            cs.setString(4, vereda.getIdMunicipio());
            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vereda: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE - Eliminar vereda
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);
            //llamo el procedimiento 
            String sql = "{call pro_elimMunicipio(?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, id);
            cs.execute();

            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar vereda: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ - Buscar vereda por ID para abrir EditVereda
// READ - Buscar vereda por ID para abrir EditVereda
    public Vereda buscarPorId(String id) {
        Vereda vereda = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // ✅ Llamada correcta a la función (retorna un SYS_REFCURSOR)
            String sql = "{ ? = call fun_buscarVeredaPorId(?) }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Asignar parámetro de entrada (ID)
            cs.setString(2, id);

            // ✅ Ejecutar
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            // ✅ Procesar resultado
            if (rs.next()) {
                vereda = new Vereda();
                vereda.setIdVereda(rs.getString("id_vereda"));
                vereda.setCodigoDane(rs.getString("codigo_dane"));
                vereda.setNombre(rs.getString("nombre_vereda"));
                vereda.setNombreMunicipio(rs.getString("nombre_municipio")); // ← corregido, antes usabas setNombre dos veces
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar vereda: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return vereda;
    }

}
