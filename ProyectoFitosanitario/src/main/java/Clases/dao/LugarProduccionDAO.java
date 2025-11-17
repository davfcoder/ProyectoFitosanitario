/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.LugarProduccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LugarProduccionDAO {

    private CConexion conexion;

    public LugarProduccionDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar vereda usando procedimiento almacenado
    public boolean insertar(LugarProduccion lugar) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incLugarProduccion(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, lugar.getNomLugarProduccion());
            cs.setString(2, lugar.getNroRegistroICA());
            cs.setString(3, lugar.getIdUsuarioProductor());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar lugar de producción: " + e.getMessage());
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
    // READ - Listar todos los lugares de producción
    // ============================================================
    public List<LugarProduccion> listarTodos() {
        List<LugarProduccion> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{ ? = call fun_listarLugaresProduccion() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar función
            cs.execute();

            // ✅ Obtener resultados
            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                LugarProduccion lp = new LugarProduccion();
                lp.setIdLugarProduccion(rs.getString("id_lugar_produccion"));
                lp.setNomLugarProduccion(rs.getString("nom_lugar_produccion"));
                lp.setNroRegistroICA(rs.getString("nro_registro_ica"));
                lp.setNombreUsuario(rs.getString("nombre_productor"));

                lista.add(lp);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar lugares de producción: " + e.getMessage());
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
    // UPDATE - Actualizar lugar de producción
    // ============================================================
    public boolean actualizar(LugarProduccion lugar) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actLugarProduccion(?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, lugar.getIdLugarProduccion());
            cs.setString(2, lugar.getNomLugarProduccion());
            cs.setString(3, lugar.getNroRegistroICA());
            cs.setString(4, lugar.getIdUsuarioProductor());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar de producción: " + e.getMessage());
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
    // DELETE - Eliminar lugar de producción
    // ============================================================
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimLugarProduccion(?)}";
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
            JOptionPane.showMessageDialog(null, "Error al eliminar lugar de producción: " + e.getMessage());
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
    // READ - Buscar lugar por ID (para módulo Editar)
    // ============================================================
    public LugarProduccion buscarPorId(String id) {
        LugarProduccion lugar = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{ ? = call fun_buscarLugarProdPorId(?) }";
            cs = con.prepareCall(sql);

            // ✅ Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Parámetro de entrada
            cs.setString(2, id);

            // ✅ Ejecutar
            cs.execute();

            // ✅ Obtener resultados
            rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                lugar = new LugarProduccion();
                lugar.setIdLugarProduccion(rs.getString("id_lugar_produccion"));
                lugar.setNomLugarProduccion(rs.getString("nom_lugar_produccion"));
                lugar.setNroRegistroICA(rs.getString("nro_registro_ica"));
                lugar.setNombreUsuario(rs.getString("nombre_productor"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar lugar de producción: " + e.getMessage());
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

        return lugar;
    }

    // Obtener ID REAL del lugar de producción por nombre (autorrellenable)
    public String obtenerIdPorNombre(String nombreLugarProd) {
        String idLugarProd = null;
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamar a la función que devuelve un VARCHAR
            String sql = "{ ? = call fun_obtenerIdLugarProduccion(?) }";
            cs = con.prepareCall(sql);

            // Registrar parámetro de salida
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);

            // Parámetro de entrada
            cs.setString(2, nombreLugarProd);

            // Ejecutar
            cs.execute();

            // Obtener resultado
            idLugarProd = cs.getString(1);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al obtener ID del lugar de producción: " + e.getMessage()
            );
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

        return idLugarProd;
    }
    


}
