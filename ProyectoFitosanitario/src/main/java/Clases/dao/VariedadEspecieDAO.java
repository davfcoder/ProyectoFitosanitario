/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.VariedadEspecie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VariedadEspecieDAO {

    private CConexion conexion;

    public VariedadEspecieDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar variedades de especies usando procedimiento almacenado
    public boolean insertar(VariedadEspecie variedadespecie) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incVariedadEspecie(?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, variedadespecie.getNomVariedad());
            cs.setString(2, variedadespecie.getIdEspecie());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar variedades de especies: " + e.getMessage());
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

// 🔹 READ - Listar todos los variedad de especies usando FUNCTION almacenada
    public List<VariedadEspecie> listarTodos() {
        List<VariedadEspecie> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // ✅ Llamada a la función almacenada que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call fun_listarVariedadesEspecie() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar el parámetro de salida (el cursor devuelto por la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            // ✅ Recorrer resultados
            while (rs.next()) {
                VariedadEspecie m = new VariedadEspecie();
                m.setIdVariedad(rs.getString("id_variedad"));
                m.setNomVariedad(rs.getString("nom_variedad"));
                m.setNombreEspecie(rs.getString("nombre_especie"));
                lista.add(m);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar variedad de especies: " + e.getMessage());
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

    // UPDATE - Actualizar variedad de especies
    public boolean actualizar(VariedadEspecie variedadespecie) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actVariedadEspecie(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, variedadespecie.getIdVariedad());
            cs.setString(2, variedadespecie.getNomVariedad());
            cs.setString(3, variedadespecie.getIdEspecie());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar variedad de especie: " + e.getMessage());
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

    // DELETE - Eliminar variedad de especies
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimVariedadEspecie(?)}";
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
            JOptionPane.showMessageDialog(null, "Error al eliminar variedad de especie: " + e.getMessage());
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

// READ - Buscar variedad de especie por ID
    public VariedadEspecie buscarPorId(String id) {
        VariedadEspecie variedadespecie = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // ✅ Llamada correcta: función que retorna un cursor
            String sql = "{ ? = call fun_buscarVariedadEspeciePorId(?) }";
            cs = con.prepareCall(sql);

            // ✅ Registrar el parámetro de salida (el cursor que retorna)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Pasar el parámetro de entrada
            cs.setString(2, id);

            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                variedadespecie = new VariedadEspecie();
                variedadespecie.setIdVariedad(rs.getString("id_variedad"));
                variedadespecie.setNomVariedad(rs.getString("nom_variedad"));
                variedadespecie.setNombreEspecie(rs.getString("nombre_especie"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar variedad de especies: " + e.getMessage());
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

        return variedadespecie;
    }

    //  Obtener ID para que me traiga el nombre AUTORRELLENABLE
    public String obtenerIdPorNombre(String nombreVariedadEspecie) {
        String idVariedadEspecie = null;
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamar a la función que devuelve un valor
            String sql = "{? = call fun_obtenerIdVariedad(?)}";
            cs = con.prepareCall(sql);

            // Registrar el parámetro de salida (valor devuelto por la función)
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);

            // Asignar el parámetro de entrada
            cs.setString(2, nombreVariedadEspecie);

            // Ejecutar
            cs.execute();

            // Obtener el valor retornado por la función
            idVariedadEspecie = cs.getString(1);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID de variedad de especie: " + e.getMessage());
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

        return idVariedadEspecie;
    }

}
