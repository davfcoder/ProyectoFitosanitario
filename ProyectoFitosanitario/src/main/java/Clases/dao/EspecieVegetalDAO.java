/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.EspecieVegetal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EspecieVegetalDAO {

    private CConexion conexion;

    public EspecieVegetalDAO() {
        this.conexion = new CConexion();
    }

    // CREATE - Insertar Especie Vegetal
    public boolean insertar(EspecieVegetal especievegetal) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incEspecieVegetal(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, especievegetal.getNomEspecie());
            cs.setString(2, especievegetal.getNombreComun());
            cs.setString(3, especievegetal.getCicloCultivo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar Especie Vegetal: " + e.getMessage());
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
    // READ - Listar todas las Especies Vegetales (función con SYS_REFCURSOR)
    // ============================================================
    public List<EspecieVegetal> listarTodas() {
        List<EspecieVegetal> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_listarEspeciesVegetales() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                EspecieVegetal p = new EspecieVegetal();
                p.setIdEspecie(rs.getString("id_especie"));
                p.setNomEspecie(rs.getString("nom_especie"));
                p.setNombreComun(rs.getString("nom_comun"));
                p.setCicloCultivo(rs.getString("ciclo_cultivo")); //corto ,medio,largo
                lista.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Especies Vegetales: " + e.getMessage());
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

    // ============================================================
    // UPDATE - Actualizar Especie Vegetal
    // ============================================================
    public boolean actualizar(EspecieVegetal especievegetal) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actEspecieVegetal(?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, especievegetal.getIdEspecie());
            cs.setString(2, especievegetal.getNomEspecie());
            cs.setString(3, especievegetal.getNombreComun());
            cs.setString(4, especievegetal.getCicloCultivo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar especie vegetal: " + e.getMessage());
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
    // DELETE - Eliminar Especie Vegetal
    // ============================================================
    public boolean eliminar(String idEspecie) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimEspecieVegetal(?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, idEspecie);
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
            JOptionPane.showMessageDialog(null, "Error al eliminar Especie Vegetal: " + e.getMessage());
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
    // READ - Buscar Especie Vegetal por ID (para módulo Editar)
    // ============================================================
    public EspecieVegetal buscarPorId(String idEspecie) {
        EspecieVegetal especievegetal = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_buscarEspecieVegetalPorId(?) }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Parámetro de entrada
            cs.setString(2, idEspecie);

            // ✅ Ejecutar
            cs.execute();

            // ✅ Obtener resultados
            rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                especievegetal = new EspecieVegetal();
                especievegetal.setIdEspecie(rs.getString("id_especie"));
                especievegetal.setNomEspecie(rs.getString("nom_especie"));
                especievegetal.setNombreComun(rs.getString("nom_comun"));
                especievegetal.setCicloCultivo(rs.getString("ciclo_cultivo"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar Especie Vegetal: " + e.getMessage());
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

        return especievegetal;
    }
}
