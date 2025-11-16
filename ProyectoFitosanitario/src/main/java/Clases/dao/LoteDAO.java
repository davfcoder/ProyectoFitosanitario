/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Lote;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LoteDAO {

    private CConexion conexion;

    public LoteDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar lote usando procedimiento almacenado
    public boolean insertar(Lote lote) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incLote(?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, lote.getNumero());
            cs.setFloat(2, lote.getAreaTotal());
            cs.setDate(3, lote.getFecSiembra());
            cs.setDate(4, lote.getFecEliminacion());
            cs.setString(5, lote.getIdVariedad());
            cs.setString(6, lote.getIdLugarProduccion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar lote: " + e.getMessage());
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

//  READ - Listar todas las lotes (usando función almacenada)
    public List<Lote> listarTodos() {
        List<Lote> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            //  Llamada correcta a la función que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call fun_listarLotes() }";
            cs = con.prepareCall(sql);

            //  Registrar el parámetro de salida (el cursor devuelto por la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            //  Ejecutar la función
            cs.execute();

            //  Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            //  Recorrer resultados y llenar la lista
            while (rs.next()) {
                Lote v = new Lote();
                v.setIdLote(rs.getString("id_lote"));
                v.setNumero(rs.getString("numero"));
                v.setAreaTotal(rs.getFloat("area_total"));        
                v.setFecSiembra(rs.getDate("fec_siembra")); 
                v.setFecEliminacion(rs.getDate("fec_eliminacion")); 
                v.setNombreVariedad(rs.getString("nom_variedad")); 
                v.setNombreLugarProduccion(rs.getString("nom_lugar_produccion")); 

                lista.add(v);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar lotes: " + e.getMessage());
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

    // UPDATE - Actualizar lote
    public boolean actualizar(Lote lote) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actLote(?, ?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, lote.getIdLote());
            cs.setString(2, lote.getNumero());
            cs.setFloat(3, lote.getAreaTotal());
            cs.setDate(4, lote.getFecSiembra());
            cs.setDate(5, lote.getFecEliminacion());
            cs.setString(6, lote.getIdVariedad());
            cs.setString(7, lote.getIdLugarProduccion());
            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lote: " + e.getMessage());
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

    // DELETE - Eliminar lote
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimLote(?)}";
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
            JOptionPane.showMessageDialog(null, "Error al eliminar lote: " + e.getMessage());
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
// READ - Buscar lote por ID para abrir EditLote
    public Lote buscarPorId(String id) {
        Lote lote = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            //  Llamada correcta a la función (retorna un SYS_REFCURSOR)
            String sql = "{ ? = call fun_buscarLotePorId(?) }";
            cs = con.prepareCall(sql);

            //  Registrar parámetro de salida (cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            //  Asignar parámetro de entrada (ID)
            cs.setString(2, id);

            //  Ejecutar
            cs.execute();

            //  Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            //  Procesar resultado
            if (rs.next()) {
                lote = new Lote();
                lote.setIdLote(rs.getString("id_lote"));
                lote.setNumero(rs.getString("numero"));
                lote.setAreaTotal(rs.getFloat("area_total"));
                lote.setFecSiembra(rs.getDate("fec_siembra"));
                lote.setFecEliminacion(rs.getDate("fec_eliminacion"));
                lote.setNombreVariedad(rs.getString("nom_variedad"));
                lote.setNombreLugarProduccion(rs.getString("nom_lugar_produccion"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar lote: " + e.getMessage());
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

        return lote;
    }

    // Obtener ID de Lote por nombre (AUTORRELLENABLE)
    public String obtenerIdPorNombre(String nombreLote) {
        String idLote = null;
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            String sql = "{ ? = call fun_obtenerIdLote(?) }";
            cs = con.prepareCall(sql);

            // Parámetro de salida (retorno VARCHAR2)
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);

            // Parámetro de entrada
            cs.setString(2, nombreLote);

            // Ejecutar función
            cs.execute();

            // Obtener el ID retornado
            idLote = cs.getString(1);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al obtener ID de la lote: " + e.getMessage()
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

        return idLote;
    }

}
