/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Roles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RolesDAO {

    private CConexion conexion;

    public RolesDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar rol usando procedimiento almacenado
    public boolean insertar(Roles rol) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call pro_incRoles(?, ?)}";
            cs = con.prepareCall(sql);

            // Asignar parámetros (en el mismo orden que en el procedimiento)
            cs.setString(1, rol.getNomRol());
            cs.setString(2, rol.getDescripcion());

            // Ejecutar el procedimiento
            cs.execute();

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar rol: " + e.getMessage());
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

// 🔹 READ - Listar todos los roles usando FUNCTION almacenada
    public List<Roles> listarTodos() {
        List<Roles> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // ✅ Llamada a la función que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call fun_listarRoles() }";
            cs = con.prepareCall(sql);

            // ✅ Registrar el parámetro de salida (el valor que devuelve la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            // ✅ Ejecutar la función
            cs.execute();

            // ✅ Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            // ✅ Recorrer resultados
            while (rs.next()) {
                Roles rol = new Roles();
                rol.setIdRol(rs.getString("id_rol"));
                rol.setNomRol(rs.getString("nom_rol"));
                rol.setDescripcion(rs.getString("descripcion"));
                lista.add(rol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar roles: " + e.getMessage());
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

// UPDATE - Actualizar rol usando procedimiento almacenado
    public boolean actualizar(Roles rol) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call pro_actRoles(?, ?, ?)}";
            cs = con.prepareCall(sql);

            // Asignar parámetros (en el mismo orden del procedimiento)
            cs.setString(1, rol.getIdRol());
            cs.setString(2, rol.getNomRol());
            cs.setString(3, rol.getDescripcion());

            // Ejecutar el procedimiento
            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar rol: " + e.getMessage());
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

// DELETE - Eliminar rol usando procedimiento almacenado
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false); // 🔹 Control manual de transacciones

            String sql = "{call pro_elimRoles(?)}";
            cs = con.prepareCall(sql);

            // Intentar enviar como número si es posible
            try {
                int idNum = Integer.parseInt(id);
                cs.setInt(1, idNum);
            } catch (NumberFormatException nfe) {
                cs.setString(1, id);
            }

            cs.execute();

            con.commit(); // ✅ Confirmar la eliminación dentro de la sesión JDBC
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // 🔹 Si hay error, deshacer cambios
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar rol: " + e.getMessage());
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

// READ - Buscar rol por ID usando funcion para que visualice en la pantalla de bienvenida e inicia
    public Roles buscarPorId(String id) {
        Roles rol = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada correcta a la función
            String sql = "{ ? = call fun_buscarRolPorId(?) }";
            cs = con.prepareCall(sql);

            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // valor retornado
            cs.setString(2, id); // parámetro de entrada

            cs.execute();

            rs = (ResultSet) cs.getObject(1); // obtener el cursor

            if (rs.next()) {
                rol = new Roles();
                rol.setIdRol(rs.getString("id_rol"));
                rol.setNomRol(rs.getString("nom_rol"));
                rol.setDescripcion(rs.getString("descripcion"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar rol: " + e.getMessage());
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

        return rol;
    }

}
