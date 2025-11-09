/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Municipio;
import Clases.modelo.Departamento;
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

    // READ - Listar todos los municipios
    public List<Municipio> listarTodos() {
        List<Municipio> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_listarMunicipios(?)}";
            cs = con.prepareCall(sql);
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Municipio m = new Municipio();
                m.setIdMunicipio(rs.getString("id_municipio"));
                m.setCodigoDane(rs.getString("codigo_dane"));
                m.setNombre(rs.getString("nombre"));
                m.setNombre(rs.getString("nombre"));
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
            String sql = "{call pro_buscarMunicipioPorId(?, ?)}";
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
                municipio.setNombreDepartamento(rs.getString("nombre_departamento"));
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

}
