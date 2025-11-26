/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Clases.vistas;
import Clases.libreria.ExcelExporter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import Clases.dao.EspecieVegetalDAO;
import Clases.dao.InspeccionFitosanitariaDAO;
import Clases.dao.InspeccionPlagaDAO;
import Clases.dao.LoteDAO;
import Clases.dao.LugarProduccionDAO;
import Clases.modelo.InspeccionPlaga;
import Clases.modelo.InspeccionReporte;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestionReportesInspeccionesF extends javax.swing.JPanel {

    private String nombreRol;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public GestionReportesInspeccionesF(String nombreRol) {
        this.nombreRol = nombreRol;
        initComponents();
        configurarLoteInactiva();
        configurarEventoLugarProd();
        cargarEstados();
        cargarEspecies();
        cargarLugarProduccion();
        configurarEnlaceTablas();
        tblInspecciones.getColumnModel().getColumn(0).setMinWidth(0);
        tblInspecciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tblInspecciones.getColumnModel().getColumn(0).setWidth(0);
    }
    
    private void configurarEnlaceTablas() {
        // Escucha eventos de selección en la tabla maestra (tblInspecciones)
        tblInspecciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // valueIsAdjusting es true mientras el usuario arrastra la selección
                if (!e.getValueIsAdjusting()) { 

                    int filaSeleccionada = tblInspecciones.getSelectedRow();

                    // Asegurar que hay una fila válida seleccionada
                    if (filaSeleccionada != -1) {
                        try {
                            // El ID de la Inspección está en la columna OCULTA (índice 0) de la tabla maestra
                            String idInspeccion = tblInspecciones.getValueAt(filaSeleccionada, 0).toString();

                            // Llamar al método para cargar la tabla detalle
                            cargarPlagasPorInspeccion(idInspeccion);

                        } catch (Exception ex) {
                            System.err.println("Error al obtener ID de la fila: " + ex.getMessage());
                        }
                    } else {
                        // Si no hay nada seleccionado, limpiar la tabla detalle
                        DefaultTableModel modelDetalle = (DefaultTableModel) tblPlagas.getModel();
                        modelDetalle.setRowCount(0);
                    }
                }
            }
        });
    }
    
    private void llenarTablaInspecciones(List<InspeccionReporte> lista) {
        try {
            // Asume que tblInspecciones es tu JTable para la tabla maestra
            DefaultTableModel model = (DefaultTableModel) tblInspecciones.getModel();
            model.setRowCount(0); // Limpia la tabla
            
            // 1. Recorrer la lista y añadir las filas
            for (InspeccionReporte dto : lista) {
                
                // Formatear la fecha para mostrarla limpiamente
                String fechaFormateada = dto.getFecInspeccion() != null 
                        ? dateFormat.format(dto.getFecInspeccion()) 
                        : "N/A";

                model.addRow(new Object[]{
                    dto.getIdInspeccion(), 
                    fechaFormateada,
                    dto.getCantidadPlantas(),
                    dto.getEstadoFenologico(),
                    dto.getAsistenteTecnico(),
                    dto.getNombreLugarProduccion(),
                    dto.getNumeroLote(),
                    dto.getNombreEspecie(),
                    dto.getTotalPlagas(),
                    dto.getObservaciones()
                });
            }
            
        } catch (Exception e) {
             System.out.println("Error en llenarTablaInspecciones: " + e.getMessage());
             // Es mejor usar un JOptionPane para notificar al usuario en una vista
             JOptionPane.showMessageDialog(this, "Error al llenar la tabla de Inspecciones: " + e.getMessage(), "Error de Vista", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarPlagasPorInspeccion(String idInspeccion) {
        try {
            // 1. Obtener la lista de plagas
            InspeccionPlagaDAO inspDAO = new InspeccionPlagaDAO();
            // Usamos la lista de InspeccionPlaga que ya definiste
            List<InspeccionPlaga> listaPlagas = inspDAO.listarPlagasInspeccion(idInspeccion); 

            // 2. Preparar el modelo de la tabla detalle
            DefaultTableModel model = (DefaultTableModel) tblPlagas.getModel();
            model.setRowCount(0); // Limpia la tabla detalle

            // 3. Llenar la tabla
            for (InspeccionPlaga plaga : listaPlagas) {
                model.addRow(new Object[]{
                    // Columna 0 (Oculta): ID_PLAGA
                    plaga.getIdPlaga(), 
                    // Columna 1: Nombre Plaga
                    plaga.getNomEspecie(),
                    // Columna 2: Plantas Infestadas
                    plaga.getCantidadPlantasInfestadas(),
                    // Columna 3: % Infestación
                    plaga.getPorcentajeInfestacion()
                });
            }

            // 4. Ocultar la columna del ID de la Plaga (columna 0)
            if (tblPlagas.getColumnModel().getColumnCount() > 0) {
                tblPlagas.getColumnModel().getColumn(0).setMinWidth(0);
                tblPlagas.getColumnModel().getColumn(0).setMaxWidth(0);
                tblPlagas.getColumnModel().getColumn(0).setWidth(0);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar la tabla de plagas por selección: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al cargar detalle de plagas.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarLoteInactiva() {
        jBoxNumeroLote.removeAllItems();
        jBoxNumeroLote.addItem("Seleccione un numero de lote");
        jBoxNumeroLote.setEnabled(false);
    }
    
    private void configurarEventoLugarProd() {
        jBoxLugarProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (jBoxLugarProd.getSelectedIndex() <= 0) {
                    configurarLoteInactiva();
                    return;
                }

                // Nombre del lugar seleccionado
                String nombreLugar = (String) jBoxLugarProd.getSelectedItem();

                try {
                    // Obtener ID del lugar de producción
                    LugarProduccionDAO lpDAO = new LugarProduccionDAO();
                    String idLugar = lpDAO.obtenerIdPorNombre(nombreLugar);

                    if (idLugar != null) {
                        // Cargar lotes asociados al lugar
                        cargarLotesPorLugarProduccion(idLugar);
                        jBoxNumeroLote.setEnabled(true);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al cargar lotes: " + ex.getMessage());
                }
            }
        });
    }

// 🔹 Carga lotes filtradas por lugar de produciones
    private void cargarLotesPorLugarProduccion(String idLugarProduccion) {
        try {
            LoteDAO dao = new LoteDAO();

            jBoxNumeroLote.removeAllItems();
            jBoxNumeroLote.addItem("Seleccione el numero de lote");

            // Lista de lote por lugar de produccion
            List<String> lotes = dao.listarLotePorLugarProduccion(idLugarProduccion);

            // Agrega cada lotes al combo
            lotes.forEach(v -> jBoxNumeroLote.addItem(v));

            // Autocompletado
            AutoCompleteDecorator.decorate(jBoxNumeroLote);

            // Activar el combo porque ahora sí tiene elementos
            jBoxNumeroLote.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar lotes: " + e.getMessage());
        }
    }

    private void cargarLugarProduccion() {
        try {
            LugarProduccionDAO dao = new LugarProduccionDAO();
            jBoxLugarProd.removeAllItems();
            jBoxLugarProd.addItem("Seleccione un lugar");

            dao.listarTodos().forEach(lp -> jBoxLugarProd.addItem(lp.getNomLugarProduccion()));

            AutoCompleteDecorator.decorate(jBoxLugarProd);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar lugar de producción: " + e.getMessage());
        }
    }
    
    private void cargarEspecies() {
        try {
            EspecieVegetalDAO dao = new EspecieVegetalDAO();
            jBoxNomEspecie.removeAllItems();
            jBoxNomEspecie.addItem("Seleccione una especie vegetal");

            dao.listarTodos().forEach(espv -> jBoxNomEspecie.addItem(espv.getNombreComun()));

            // Activar autocompletado
            AutoCompleteDecorator.decorate(jBoxNomEspecie);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar especies: " + e.getMessage());
        }
    }
    
    private void cargarEstados() {

        jBoxEstado.removeAllItems();
        jBoxEstado.addItem("Seleccione estado fenológico");
        jBoxEstado.addItem("Germinación");
        jBoxEstado.addItem("Emergencia");
        jBoxEstado.addItem("Crecimiento vegetativo");
        jBoxEstado.addItem("Formación de brotes");
        jBoxEstado.addItem("Floración inicial");
        jBoxEstado.addItem("Floración plena");
        jBoxEstado.addItem("Fructificación");
        jBoxEstado.addItem("Maduración");
        jBoxEstado.addItem("Senescencia");

    }

    public GestionReportesInspeccionesF() {
        this("Consulta"); // Rol por defecto si no envían nombreRol
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLocaleChooser1 = new com.toedter.components.JLocaleChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInspecciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPlagas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jBoxLugarProd = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jBoxNumeroLote = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBoxNomEspecie = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jBoxEstado = new javax.swing.JComboBox<>();
        btnAplicarFiltros = new javax.swing.JButton();
        btnLimpiarFiltros = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        dateChooserDesde = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        dateChooserHasta = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        btnExportarDetalles = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Reportes de las Inspecciones Fitosanitarias:");

        tblInspecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_inspeccion", "Fecha de Inspección", "Cantidad Plantas", "Estado Fenologico", "Asistente tecnico", "Lugar de Producción", "Nro. Lote", "Cultivo (especie vegetal)", "Nro. plagas evidenciadas", "Observaciones"
            }
        ));
        jScrollPane1.setViewportView(tblInspecciones);
        if (tblInspecciones.getColumnModel().getColumnCount() > 0) {
            tblInspecciones.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        tblPlagas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_plaga", "Nombre plaga", "Plantas infestadas", "% infestación"
            }
        ));
        jScrollPane2.setViewportView(tblPlagas);
        if (tblPlagas.getColumnModel().getColumnCount() > 0) {
            tblPlagas.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 0));
        jLabel4.setText("Lugar de producción:");

        jBoxLugarProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 0));
        jLabel5.setText("Número de lote:");

        jBoxNumeroLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Tabla de inspecciones fitosanitarias:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Tabla de las plagas asociadas a la inspección fitosanitaria seleccionada:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 0));
        jLabel7.setText("Cultivo (especie):");

        jBoxNomEspecie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 0));
        jLabel8.setText("Estado fenológico:");

        jBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAplicarFiltros.setBackground(new java.awt.Color(153, 255, 153));
        btnAplicarFiltros.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAplicarFiltros.setText("Aplicar Filtros");
        btnAplicarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarFiltrosActionPerformed(evt);
            }
        });

        btnLimpiarFiltros.setBackground(new java.awt.Color(255, 204, 153));
        btnLimpiarFiltros.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiarFiltros.setText("Limpiar Filtros");
        btnLimpiarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarFiltrosActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 0));
        jLabel9.setText("Fecha Desde:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 0));
        jLabel10.setText("Fecha Hasta:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 51));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/filtro.png"))); // NOI18N
        jLabel6.setText("Filtrar por:");

        jButton3.setBackground(new java.awt.Color(153, 153, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Exportar datos inspección");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnExportarDetalles.setBackground(new java.awt.Color(153, 153, 153));
        btnExportarDetalles.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportarDetalles.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarDetalles.setText("Exportar inspección y plagas");
        btnExportarDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarDetallesActionPerformed(evt);
            }
        });

        jLabel2.setText("Selecciona UNA fila de la tabla superior, a continuación, presiona el botón \"Aplicar\" para mostrar las plagas asociadas a dicha inspección.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBoxLugarProd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBoxNomEspecie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBoxNumeroLote, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBoxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateChooserHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(btnAplicarFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(164, 164, 164)
                        .addComponent(btnLimpiarFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(200, 200, 200))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(400, 400, 400))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExportarDetalles)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(315, 315, 315)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel6)
                    .addGap(0, 680, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateChooserDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBoxNumeroLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel5)
                        .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jBoxNomEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(dateChooserHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAplicarFiltros)
                    .addComponent(btnLimpiarFiltros))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportarDetalles)
                .addGap(59, 59, 59))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(jLabel6)
                    .addContainerGap(582, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 752, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarFiltrosActionPerformed
        try {
            jBoxLugarProd.setSelectedIndex(0); 
            jBoxNomEspecie.setSelectedIndex(0);
            jBoxEstado.setSelectedIndex(0);
            configurarLoteInactiva();
            dateChooserDesde.setDate(null);
            dateChooserHasta.setDate(null);
            // Tabla Maestra (tblInspecciones)
            DefaultTableModel modelMaestra = (DefaultTableModel) tblInspecciones.getModel();
            modelMaestra.setRowCount(0);

            // Tabla Detalle (tblPlagas)
            DefaultTableModel modelDetalle = (DefaultTableModel) tblPlagas.getModel();
            modelDetalle.setRowCount(0);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnLimpiarFiltrosActionPerformed

    private void btnAplicarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarFiltrosActionPerformed
    try {
        java.util.Date fechaInicio = dateChooserDesde.getDate();
        java.util.Date fechaFin = dateChooserHasta.getDate();
        String idLugar = new LugarProduccionDAO().obtenerIdPorNombre((String) jBoxLugarProd.getSelectedItem());
        String idLote = new LoteDAO().obtenerIdPorNombre((String) jBoxNumeroLote.getSelectedItem()); 
        String idEspecie = new EspecieVegetalDAO().obtenerIdPorNombre((String) jBoxNomEspecie.getSelectedItem());
        String estadoF;
        if (jBoxEstado.getSelectedIndex()==0) estadoF = null;
        else estadoF = (String) jBoxEstado.getSelectedItem(); 
            
        // 3. Llamar al DAO principal
        InspeccionFitosanitariaDAO inspDAO = new InspeccionFitosanitariaDAO();
        List<InspeccionReporte> resultados = inspDAO.listarInspeccionesReporte(
                fechaInicio, fechaFin, idLugar, idLote, idEspecie, estadoF);

        // 4. Llenar la Tabla Maestra (tblInspecciones)
        llenarTablaInspecciones(resultados);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al aplicar filtros: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAplicarFiltrosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (tblInspecciones.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.", "Tabla Vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte General");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("Reporte_Inspecciones.xlsx")); // Nombre sugerido

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Asegurar extensión .xlsx
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            // Llamar al exportador
            ExcelExporter exporter = new ExcelExporter();
            boolean exito = exporter.exportarTablaGeneral(tblInspecciones, fileToSave);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Reporte General exportado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo. Verifique que no esté abierto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnExportarDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarDetallesActionPerformed
        int filaSeleccionada = tblInspecciones.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una Inspección de la tabla superior primero.", "Sin Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Opcional: Verificar si la tabla de plagas tiene datos, aunque podría exportarse vacía si no hubo plagas
        /* if (tblPlagas.getRowCount() == 0) { ... } */

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte Detallado");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        // Sugerir nombre con el ID de la inspección (asumiendo col 0 es ID)
        String idInsp = tblInspecciones.getValueAt(filaSeleccionada, 0).toString();
        fileChooser.setSelectedFile(new File("Reporte_Detalle_" + idInsp + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            ExcelExporter exporter = new ExcelExporter();
            // Le pasamos ambas tablas y el índice de la fila maestra
            boolean exito = exporter.exportarReporteIndividual(tblInspecciones, filaSeleccionada, tblPlagas, fileToSave);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Reporte Detallado exportado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExportarDetallesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicarFiltros;
    private javax.swing.JButton btnExportarDetalles;
    private javax.swing.JButton btnLimpiarFiltros;
    private com.toedter.calendar.JDateChooser dateChooserDesde;
    private com.toedter.calendar.JDateChooser dateChooserHasta;
    private javax.swing.JComboBox<String> jBoxEstado;
    private javax.swing.JComboBox<String> jBoxLugarProd;
    private javax.swing.JComboBox<String> jBoxNomEspecie;
    private javax.swing.JComboBox<String> jBoxNumeroLote;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.toedter.components.JLocaleChooser jLocaleChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblInspecciones;
    private javax.swing.JTable tblPlagas;
    // End of variables declaration//GEN-END:variables
}
