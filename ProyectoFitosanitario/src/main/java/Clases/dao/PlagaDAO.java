/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Plaga;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PlagaDAO {

    private CConexion conexion;

    public PlagaDAO() {
        this.conexion = new CConexion();
    }

    // CREATE - Insertar plaga
    public boolean insertar(Plaga plaga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incPlaga(?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, plaga.getNomEspecie());
            cs.setString(2, plaga.getNombreComun());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar plaga: " + e.getMessage());
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

    // ============================================================
    // READ - Listar todas las plagas (función con SYS_REFCURSOR)
    // ============================================================
    public List<Plaga> listarTodas() {
        List<Plaga> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_listarPlagas() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Plaga p = new Plaga();
                p.setIdPlaga(rs.getString("id_plaga"));
                p.setNomEspecie(rs.getString("nom_especie"));
                p.setNombreComun(rs.getString("nombre_comun"));
                lista.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar plagas: " + e.getMessage());
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

    // ============================================================================================================
    // READ - Listar las plagas que no estén asociadas a la especie vegetal específica (función con SYS_REFCURSOR)
    // ============================================================================================================
    public List<Plaga> listarNoAsociadasAEspecie(String idEspecie) {
        List<Plaga> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_listarPlagasNoAsociadas(?) }";
            cs = con.prepareCall(sql);

            // Cursor de salida
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // Parámetro de entrada
            cs.setString(2, idEspecie);

            // Ejecutar function
            cs.execute();

            // Obtener cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Plaga p = new Plaga();
                p.setIdPlaga(rs.getString("id_plaga"));
                p.setNomEspecie(rs.getString("nom_especie"));
                p.setNombreComun(rs.getString("nombre_comun"));
                lista.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar plagas no asociadas: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return lista;
    }

        // ============================================================================================================
    // READ - Listar las plagas que no estén asociadas a la especie vegetal específica (función con SYS_REFCURSOR)
    // ============================================================================================================
    public List<Plaga> listarAsociadasAEspecie(String idEspecie) {
        List<Plaga> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_listarPlagasAsociadas(?) }";
            cs = con.prepareCall(sql);

            // Cursor de salida
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // Parámetro de entrada
            cs.setString(2, idEspecie);

            // Ejecutar function
            cs.execute();

            // Obtener cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Plaga p = new Plaga();
                p.setIdPlaga(rs.getString("id_plaga"));
                p.setNomEspecie(rs.getString("nom_especie"));
                p.setNombreComun(rs.getString("nombre_comun"));
                lista.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar plagas asociadas: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return lista;
    }
    
    // ============================================================
    // UPDATE - Actualizar plaga
    // ============================================================
    public boolean actualizar(Plaga plaga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actPlaga(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, plaga.getIdPlaga());
            cs.setString(2, plaga.getNomEspecie());
            cs.setString(3, plaga.getNombreComun());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar plaga: " + e.getMessage());
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

    // ============================================================
    // DELETE - Eliminar plaga
    // ============================================================
    public boolean eliminar(String idPlaga) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimPlaga(?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, idPlaga);
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
            JOptionPane.showMessageDialog(null, "Error al eliminar plaga: " + e.getMessage());
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

    // ============================================================
    // READ - Buscar plaga por ID (para módulo Editar)
    // ============================================================
    public Plaga buscarPorId(String idPlaga) {
        Plaga plaga = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_buscarPlagaPorId(?) }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Parámetro de entrada
            cs.setString(2, idPlaga);

            // ✅ Ejecutar
            cs.execute();

            // ✅ Obtener resultados
            rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                plaga = new Plaga();
                plaga.setIdPlaga(rs.getString("id_plaga"));
                plaga.setNomEspecie(rs.getString("nom_especie"));
                plaga.setNombreComun(rs.getString("nombre_comun"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar plaga: " + e.getMessage());
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

        return plaga;
    }
}
