package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.Usuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    private final CConexion conexion;

    public UsuarioDAO() {
        this.conexion = new CConexion();
    }

// ✅ CREATE - Insertar usuario usando procedimiento almacenado
    public boolean insertar(Usuarios usuario) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call sp_insertar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            // Asignar parámetros (deben coincidir con el orden del procedimiento)
            cs.setString(1, usuario.getNumIdentificacion());
            cs.setString(2, usuario.getNombres());
            cs.setString(3, usuario.getApellidos());
            cs.setString(4, usuario.getDireccion());
            cs.setString(5, usuario.getTelefono());
            cs.setString(6, usuario.getCorreoElectronico());
            cs.setString(7, usuario.getIngresoUsuario());
            cs.setString(8, usuario.getIngresoContrasenia());
            cs.setString(9, usuario.getNroRegistroICA());
            cs.setString(10, usuario.getTarjetaProfesional());
            cs.setString(11, usuario.getIdCargo());

            // Ejecutar el procedimiento
            cs.execute();

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + e.getMessage());
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

// ✅ READ - Listar usuarios usando procedimiento almacenado
    public List<Usuarios> listarTodos() {
        List<Usuarios> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // Llamar al procedimiento con un parámetro OUT tipo cursor
            String sql = "{call sp_listar_usuarios(?)}";
            cs = con.prepareCall(sql);
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Importante: usar OracleTypes

            // Ejecutar el procedimiento
            cs.execute();

            // Recuperar el cursor
            rs = (ResultSet) cs.getObject(1);

            // Recorrer los resultados
            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setIdUsuario(rs.getString("id_usuario"));
                u.setNumIdentificacion(rs.getString("num_identificacion"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setDireccion(rs.getString("direccion"));
                u.setTelefono(rs.getString("telefono"));
                u.setCorreoElectronico(rs.getString("correo_electronico"));
                u.setIngresoUsuario(rs.getString("ingreso_usuario"));
                u.setIngresoContrasenia(rs.getString("ingreso_contrasenia"));
                u.setNroRegistroICA(rs.getString("nro_registro_ica"));
                u.setTarjetaProfesional(rs.getString("tarjeta_profesional"));
                u.setIdCargo(rs.getString("id_cargo"));
                lista.add(u);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar usuarios: " + e.getMessage());
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

// ✅ UPDATE - Actualizar usuario usando procedimiento almacenado
    public boolean actualizar(Usuarios usuario) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            // Asignar parámetros (deben coincidir con el orden del procedimiento)
            cs.setString(1, usuario.getIdUsuario());
            cs.setString(2, usuario.getNumIdentificacion());
            cs.setString(3, usuario.getNombres());
            cs.setString(4, usuario.getApellidos());
            cs.setString(5, usuario.getDireccion());
            cs.setString(6, usuario.getTelefono());
            cs.setString(7, usuario.getCorreoElectronico());
            cs.setString(8, usuario.getIngresoUsuario());
            cs.setString(9, usuario.getIngresoContrasenia());
            cs.setString(10, usuario.getNroRegistroICA());
            cs.setString(11, usuario.getTarjetaProfesional());
            cs.setString(12, usuario.getIdCargo());

            // Ejecutar el procedimiento
            cs.execute();

            return true; // si no lanza error, fue exitoso

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage());
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

// ✅ DELETE - Eliminar usuario usando procedimiento almacenado
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call sp_eliminar_usuario(?)}";
            cs = con.prepareCall(sql);

            // Parámetro de entrada
            cs.setString(1, id);

            // Ejecutar el procedimiento
            cs.execute();

            return true; // Eliminación exitosa

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
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

// ✅ OBTENER UN USUARIO POR SU ID
    public Usuarios getUserById(String idUsuario) {
        Usuarios usuario = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            // Llamada al procedimiento almacenado
            String sql = "{call sp_buscar_usuario_por_id(?, ?)}";
            cs = con.prepareCall(sql);

            // Parámetro de entrada
            cs.setString(1, idUsuario);

            // Parámetro de salida (el cursor)
            cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

            // Ejecutar
            cs.execute();

            // Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(2);

            // Procesar resultados
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setIdUsuario(rs.getString("id_usuario"));
                usuario.setNumIdentificacion(rs.getString("num_identificacion"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCorreoElectronico(rs.getString("correo_electronico"));
                usuario.setIngresoUsuario(rs.getString("ingreso_usuario"));
                usuario.setIngresoContrasenia(rs.getString("ingreso_contrasenia"));
                usuario.setNroRegistroICA(rs.getString("nro_registro_ica"));
                usuario.setTarjetaProfesional(rs.getString("tarjeta_profesional"));
                usuario.setIdCargo(rs.getString("id_cargo"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuario: " + e.getMessage());
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

        return usuario;
    }

}
