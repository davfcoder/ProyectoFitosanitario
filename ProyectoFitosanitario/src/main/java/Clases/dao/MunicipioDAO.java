/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Municipio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MunicipioDAO {

    private CConexion conexion;

    public MunicipioDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar rol usando procedimiento almacenado
    public boolean insertar(Municipio municipio) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incMunicipio(?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, municipio.getCodigoDane());
            cs.setString(2, municipio.getNombre());
            cs.setString(3, municipio.getIdDepartamento());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar municipio: " + e.getMessage());
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

// 🔹 READ - Listar todos los municipios usando FUNCTION almacenada
    public List<Municipio> listarTodos() {
        List<Municipio> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // ✅ Llamada a la función almacenada que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call fun_listarMunicipios() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar el parámetro de salida (el cursor devuelto por la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            // ✅ Recorrer resultados
            while (rs.next()) {
                Municipio m = new Municipio();
                m.setIdMunicipio(rs.getString("id_municipio"));
                m.setCodigoDane(rs.getString("codigo_dane"));
                m.setNombre(rs.getString("nombre_municipio"));
                m.setNombreDepartamento(rs.getString("nombre_departamento"));
                lista.add(m);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar municipios: " + e.getMessage());
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

    // UPDATE - Actualizar municipio
    public boolean actualizar(Municipio municipio) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actMunicipio(?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, municipio.getIdMunicipio());
            cs.setString(2, municipio.getCodigoDane());
            cs.setString(3, municipio.getNombre());
            cs.setString(4, municipio.getIdDepartamento());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar municipio: " + e.getMessage());
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

    // DELETE - Eliminar municipio
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

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
            JOptionPane.showMessageDialog(null, "Error al eliminar municipio: " + e.getMessage());
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

    // READ - Buscar municipio por ID
    public Municipio buscarPorId(String id) {
        Municipio municipio = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call fun_buscarMunicipioPorId(?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, id);
            cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();
            rs = (ResultSet) cs.getObject(2);

            if (rs.next()) {
                municipio = new Municipio();
                municipio.setIdMunicipio(rs.getString("id_municipio"));
                municipio.setCodigoDane(rs.getString("codigo_dane"));
                municipio.setNombre(rs.getString("nombre"));
                municipio.setNombre(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar municipio: " + e.getMessage());
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

        return municipio;
    }

    //  Obtener ID para que me traiga el nombre
    public String obtenerIdPorNombre(String nombreMunicipio) {
        String idMunicipio = null;
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamar a la función que devuelve un valor
            String sql = "{? = call fun_obtenerIdMunicipio(?)}";
            cs = con.prepareCall(sql);

            // Registrar el parámetro de salida (valor devuelto por la función)
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);

            // Asignar el parámetro de entrada
            cs.setString(2, nombreMunicipio);

            // Ejecutar
            cs.execute();

            // Obtener el valor retornado por la función
            idMunicipio = cs.getString(1);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID de municipio: " + e.getMessage());
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

        return idMunicipio;
    }

}
