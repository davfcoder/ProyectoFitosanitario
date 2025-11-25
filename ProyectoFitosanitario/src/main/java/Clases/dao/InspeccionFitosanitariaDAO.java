/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.dao;

import Clases.db.CConexion;
import Clases.modelo.InspeccionFitosanitaria;
import Clases.modelo.InspeccionReporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class InspeccionFitosanitariaDAO {

    private CConexion conexion;

    public InspeccionFitosanitariaDAO() {
        this.conexion = new CConexion();
    }

// CREATE - Insertar inspeccionfitosanitaria usando procedimiento almacenado
    public boolean insertar(InspeccionFitosanitaria inspeccionfitosanitaria) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_incInspeccionFitosanitaria(?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setInt(1, inspeccionfitosanitaria.getCantidadPlantas());
            cs.setString(2, inspeccionfitosanitaria.getEstadoFenologico());
            cs.setDate(3, inspeccionfitosanitaria.getFecInspeccion());
            cs.setString(4, inspeccionfitosanitaria.getObservaciones());
            cs.setString(5, inspeccionfitosanitaria.getIdUsuarioAsistente());
            cs.setString(6, inspeccionfitosanitaria.getIdLote());

            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar inspeccion fitosanitaria: " + e.getMessage());
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
    
    public List<InspeccionReporte> listarInspeccionesReporte(
        java.util.Date fechaInicio, java.util.Date fechaFin, String idLugar, 
        String idLote, String idEspecie) {

        List<InspeccionReporte> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion(); // Usando tu método de conexión

            // 1. Llamada a la Function de Reporte
            String sql = "{ ? = call FUN_LISTAR_INSPECCIONES_REPOR(?, ?, ?, ?, ?) }";
            cs = con.prepareCall(sql);

            // 2. Registrar el parámetro de salida (Cursor)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            if (fechaInicio != null) {
                cs.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            } else {
                cs.setNull(2, java.sql.Types.DATE); // Si es null, pasamos NULL a la BD
            }

            if (fechaFin != null) {
                cs.setDate(3, new java.sql.Date(fechaFin.getTime())); //
            } else {
                cs.setNull(3, java.sql.Types.DATE); // Si es null, pasamos NULL a la BD
            }
            // Manejo de valores NULL para filtros opcionales (Oracle necesita Types.VARCHAR)
            if (idLugar == null || idLugar.isEmpty()) cs.setNull(4, java.sql.Types.VARCHAR);
            else cs.setString(4, idLugar);

            if (idLote == null || idLote.isEmpty()) cs.setNull(5, java.sql.Types.VARCHAR);
            else cs.setString(5, idLote);

            if (idEspecie == null || idEspecie.isEmpty()) cs.setNull(6, java.sql.Types.VARCHAR);
            else cs.setString(6, idEspecie);

            // 4. Ejecutar y obtener el cursor
            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            // 5. Mapear resultados
            while (rs.next()) {
                InspeccionReporte dto = new InspeccionReporte();
                dto.setIdInspeccion(rs.getString("ID_INSPECCION"));
                dto.setFecInspeccion(rs.getDate("FEC_INSPECCION"));
                dto.setCantidadPlantas(rs.getInt("CANTIDAD_PLANTAS"));
                dto.setEstadoFenologico(rs.getString("ESTADO_FENOLOGICO"));
                dto.setAsistenteTecnico(rs.getString("ASISTENTE_TECNICO"));
                dto.setNombreLugarProduccion(rs.getString("NOM_LUGAR_PRODUCCION")); // Nuevo campo
                dto.setNumeroLote(rs.getString("NRO_LOTE"));
                dto.setNombreEspecie(rs.getString("NOMBRE_ESPECIE"));
                dto.setObservaciones(rs.getString("OBSERVACIONES"));
                dto.setTotalPlagas(rs.getInt("TOTAL_PLAGAS")); // Campo calculado
                
                lista.add(dto);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar el Reporte: " + e.getMessage());
        } finally {
            // Cierre seguro de recursos
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
    
//  READ - Listar todas las inspeccion fitosanitarias (usando función almacenada)
    public List<InspeccionFitosanitaria> listarTodos() {
        List<InspeccionFitosanitaria> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            //  Llamada correcta a la función que devuelve un SYS_REFCURSOR
            String sql = "{ ? = call fun_listarInspecciones() }";
            cs = con.prepareCall(sql);

            //  Registrar el parámetro de salida (el cursor devuelto por la función)
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            //  Ejecutar la función
            cs.execute();

            //  Obtener el cursor como ResultSet
            rs = (ResultSet) cs.getObject(1);

            //  Recorrer resultados y llenar la lista
            while (rs.next()) {
                InspeccionFitosanitaria v = new InspeccionFitosanitaria();
                v.setIdInspeccion(rs.getString("id_inspeccion"));
                v.setCantidadPlantas(rs.getInt("cantidad_plantas"));
                v.setEstadoFenologico(rs.getString("estado_fenologico"));        
                v.setFecInspeccion(rs.getDate("fec_inspeccion"));
                v.setObservaciones(rs.getString("observaciones"));
                v.setNombreUsuario(rs.getString("nombre_asistente"));
                v.setNombreLugarProduccion(rs.getString("nom_lugar_produccion"));
                v.setNumeroLote(rs.getString("numero_lote")); 

                lista.add(v);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar inspeccion fitosanitaria: " + e.getMessage());
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

    // UPDATE - Actualizar inspeccionfitosanitaria
    public boolean actualizar(InspeccionFitosanitaria inspeccionfitosanitaria) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            String sql = "{call pro_actInspeccionFitosanitaria(?, ?, ?, ?, ?, ?, ?)}";
            cs = con.prepareCall(sql);

            cs.setString(1, inspeccionfitosanitaria.getIdInspeccion());
            cs.setInt(2, inspeccionfitosanitaria.getCantidadPlantas());
            cs.setString(3, inspeccionfitosanitaria.getEstadoFenologico());
            cs.setDate(4, inspeccionfitosanitaria.getFecInspeccion());
            cs.setString(5, inspeccionfitosanitaria.getObservaciones());
            cs.setString(6, inspeccionfitosanitaria.getIdUsuarioAsistente());
            cs.setString(7, inspeccionfitosanitaria.getIdLote());
            cs.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar inspeccion fitosanitaria: " + e.getMessage());
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

    // DELETE - Eliminar inspeccionfitosanitaria
    public boolean eliminar(String id) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexion.estableceConexion();
            con.setAutoCommit(false);

            String sql = "{call pro_elimInspFitosanitaria(?)}";
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
            JOptionPane.showMessageDialog(null, "Error al eliminar inspeccion fitosanitaria: " + e.getMessage());
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
// READ - Buscar inspeccionfitosanitaria por ID para abrir EditInspeccionFitosanitaria
    public InspeccionFitosanitaria buscarPorId(String id) {
        InspeccionFitosanitaria inspeccionfitosanitaria = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = conexion.estableceConexion();

            //  Llamada correcta a la función (retorna un SYS_REFCURSOR)
            String sql = "{ ? = call fun_buscarInspeccionPorId(?) }";
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
                inspeccionfitosanitaria = new InspeccionFitosanitaria();
                inspeccionfitosanitaria.setIdInspeccion(rs.getString("id_inspeccion"));
                inspeccionfitosanitaria.setCantidadPlantas(rs.getInt("cantidad_plantas"));
                inspeccionfitosanitaria.setEstadoFenologico(rs.getString("estado_fenologico"));
                inspeccionfitosanitaria.setFecInspeccion(rs.getDate("fec_inspeccion"));
                inspeccionfitosanitaria.setObservaciones(rs.getString("observaciones"));
                inspeccionfitosanitaria.setNombreUsuario(rs.getString("nombre_asistente"));
                inspeccionfitosanitaria.setNumeroLote(rs.getString("numero_lote"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar inspeccion fitosanitaria: " + e.getMessage());
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

        return inspeccionfitosanitaria;
    }

}
