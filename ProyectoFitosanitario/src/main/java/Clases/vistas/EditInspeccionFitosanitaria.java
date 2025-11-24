/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Clases.vistas;

import Clases.modelo.InspeccionFitosanitaria;
import Clases.dao.InspeccionFitosanitariaDAO;
import Clases.dao.UsuarioDAO;
import Clases.dao.LugarProduccionDAO;
import Clases.dao.LoteDAO;
import Clases.libreria.Dashboard;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ricar
 */
public class EditInspeccionFitosanitaria extends javax.swing.JPanel {

    private InspeccionFitosanitaria inspeccionfitosanitariaActual;

    public EditInspeccionFitosanitaria(InspeccionFitosanitaria inspeccionfitosanitaria) {
        this.inspeccionfitosanitariaActual = inspeccionfitosanitaria;
        initComponents();
        configurarCamposEditables();
        cargarUsuarios();
        configurarLoteInactiva();
        configurarEventoLugarProd();
        cargarEstados();
        cargarLugarProduccion();
        configurarPlaceholderFecha();
        setCamposInspeccion();
    }
    
    private void guardarInspeccion(){
        if (inspeccionfitosanitariaActual == null) {
            JOptionPane.showMessageDialog(this, "No hay una inspección cargada.");
            return;
        }
        // =======================
            // 1️⃣ CONVERTIR CANTIDAD DE PLANTAS
            // =======================
            int cantidadPlantas;
            try {
                cantidadPlantas = Integer.parseInt(txtCantidadPlantas.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "La cantidad de plantas debe ser numérica.");
                return;
            }

            // =======================
            // 2️⃣ OBTENER ID ASISTENTE
            // =======================
            String nombreAsistente = (String) jBoxAsistente.getSelectedItem();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String idUsuario = usuarioDAO.obtenerIdUsuarioPorNombre(nombreAsistente);

            if (idUsuario == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el asistente técnico.");
                return;
            }

            // =======================
            // 3️⃣ OBTENER ID LOTE
            // =======================
            String numeroLote = (String) jBoxNumeroLote.getSelectedItem();
            LoteDAO loteDAO = new LoteDAO();
            String idLote = loteDAO.obtenerIdPorNombre(numeroLote);

            if (idLote == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el lote seleccionado.");
                return;
            }

            // =======================
            // 4️⃣ CONVERTIR FECHA
            // =======================
            Date fechaInspeccion;
            try {
                fechaInspeccion = Date.valueOf(txtFecInspeccion.getText().trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "La fecha debe estar en formato YYYY-MM-DD.");
                return;
            }

            // =======================
            // 5️⃣ ACTUALIZAR OBJETO
            // =======================
            inspeccionfitosanitariaActual.setCantidadPlantas(cantidadPlantas);
            inspeccionfitosanitariaActual.setFecInspeccion(fechaInspeccion);
            inspeccionfitosanitariaActual.setObservaciones(txtObservaciones.getText().trim());

            // Estado fenológico viene del combo
            inspeccionfitosanitariaActual.setEstadoFenologico((String) jBoxEstado.getSelectedItem());
            inspeccionfitosanitariaActual.setIdUsuarioAsistente(idUsuario);
            inspeccionfitosanitariaActual.setIdLote(idLote);
    }
    
    private void configurarCamposEditables() {
        txtCantidadPlantas.setEditable(true);
        txtFecInspeccion.setEditable(true);
        txtObservaciones.setEditable(true);
    }

// Cargar datos de la inspección seleccionada (versión segura)
    private void setCamposInspeccion() {
        // ============================
        // 1️⃣ CAMPOS DIRECTOS
        // ============================
        if (inspeccionfitosanitariaActual.getEstadoFenologico() != null) {
            jBoxEstado.setSelectedItem(inspeccionfitosanitariaActual.getEstadoFenologico());
        } else {
            jBoxEstado.setSelectedIndex(0);
        }
        txtCantidadPlantas.setText(String.valueOf(inspeccionfitosanitariaActual.getCantidadPlantas()));

        txtFecInspeccion.setText(
                inspeccionfitosanitariaActual.getFecInspeccion() != null
                ? inspeccionfitosanitariaActual.getFecInspeccion().toString()
                : ""
        );

        txtObservaciones.setText(
                inspeccionfitosanitariaActual.getObservaciones() != null
                ? inspeccionfitosanitariaActual.getObservaciones()
                : ""
        );

        // ============================
        // 3️⃣ LUGAR DE PRODUCCIÓN (SOLO VISUALIZAR)
        // ============================
        if (inspeccionfitosanitariaActual.getNombreLugarProduccion() != null) {
            jBoxLugarProd.setSelectedItem(inspeccionfitosanitariaActual.getNombreLugarProduccion());
        } else {
            jBoxLugarProd.setSelectedIndex(0);
        }

        // ============================
        // 4️⃣ LOTE SEGÚN LUGAR
        // ============================
        if (inspeccionfitosanitariaActual.getNumeroLote() != null) {
            jBoxNumeroLote.setSelectedItem(inspeccionfitosanitariaActual.getNumeroLote());
        } else {
            jBoxNumeroLote.setSelectedIndex(0);
        }

        // ============================
        // 5️⃣ ASISTENTE TÉCNICO
        // ============================
        if (inspeccionfitosanitariaActual.getNombreUsuario() != null) {
            jBoxAsistente.setSelectedItem(inspeccionfitosanitariaActual.getNombreUsuario());
        } else {
            jBoxAsistente.setSelectedIndex(0);
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
        jBoxEstado.addItem("Vegetativo ");

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

    private void configurarLoteInactiva() {
        jBoxNumeroLote.removeAllItems();
        jBoxNumeroLote.addItem("Seleccione un numero de lote");
        jBoxNumeroLote.setEnabled(false);
    }

    private void limpiarCampos() {

        txtCantidadPlantas.setText("");
        txtFecInspeccion.setText("");
        txtObservaciones.setText("");

        jBoxEstado.setSelectedIndex(0);

        jBoxAsistente.setSelectedIndex(0);

        jBoxLugarProd.setSelectedIndex(0);

        jBoxNumeroLote.removeAllItems();
        jBoxNumeroLote.addItem("Seleccione un lote");
        jBoxNumeroLote.setEnabled(false);

    }

    // 🔹 Carga inicial de Nombres
    private void cargarUsuarios() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            jBoxAsistente.removeAllItems();
            jBoxAsistente.addItem("Seleccione un asistente");

            dao.listarAsistentes()
                    .forEach(usu -> jBoxAsistente.addItem(usu.getNombreCompleto()));

            AutoCompleteDecorator.decorate(jBoxAsistente);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar asistentes: " + e.getMessage());
        }
    }

    private void configurarPlaceholderFecha() {

        agregarPlaceholder(txtFecInspeccion, "YYYY-MM-DD");
    }

    private void agregarPlaceholder(JTextField campo, String placeholder) {

        // Si ya tiene valor real (caso EDITAR)
        if (!campo.getText().trim().isEmpty() && !campo.getText().equals(placeholder)) {
            campo.setForeground(Color.BLACK);
            return; // no aplicar placeholder
        }

        // Aplicar placeholder si está vacío
        campo.setText(placeholder);
        campo.setForeground(new Color(150, 150, 150));

        campo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                // Al entrar al campo → poner negro SIEMPRE
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                }
                campo.setForeground(Color.BLACK); // ← aquí la solución definitiva
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Si lo deja vacío → volver a placeholder
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(150, 150, 150));
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCantidadPlantas = new javax.swing.JTextField();
        txtFecInspeccion = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBoxLugarProd = new javax.swing.JComboBox<>();
        jBoxNumeroLote = new javax.swing.JComboBox<>();
        jBoxEstado = new javax.swing.JComboBox<>();
        txtObservaciones = new javax.swing.JTextField();
        jBoxAsistente = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnAsociarInspeccionPlagas = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnModificarInspeccionPlaga = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(770, 490));
        setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Editar Inspección: ");

        jLabel2.setText("Cantidad de plantas");

        txtCantidadPlantas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadPlantasActionPerformed(evt);
            }
        });

        txtFecInspeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFecInspeccionActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 102, 102));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(51, 153, 0));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar inspección");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel3.setText("Estado Fenologico");

        jLabel4.setText("Fecha de Inspección");

        jLabel5.setText("Asistente Tecnico");

        jLabel6.setText("Lugar de Producción");

        jLabel7.setText("Numero de lote");

        jBoxLugarProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jBoxLugarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBoxLugarProdActionPerformed(evt);
            }
        });

        jBoxNumeroLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jBoxNumeroLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBoxNumeroLoteActionPerformed(evt);
            }
        });

        jBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBoxAsistente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Si deseas guardar los cambios, y además, indicar las plagas que se evidenciaron durante la inspección, da click en en el siguiente botón:");

        btnAsociarInspeccionPlagas.setBackground(new java.awt.Color(102, 204, 255));
        btnAsociarInspeccionPlagas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAsociarInspeccionPlagas.setText("Asociar las plagas que se evidenciaron durante la inspección");
        btnAsociarInspeccionPlagas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsociarInspeccionPlagasActionPerformed(evt);
            }
        });

        jLabel14.setText("Observaciones");

        jLabel8.setText("Si deseas guardar los cambios y eliminar/modificar la información de las plagas asociadas a la inspección, haz click en el siguiente botón:");

        btnModificarInspeccionPlaga.setBackground(new java.awt.Color(255, 204, 0));
        btnModificarInspeccionPlaga.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnModificarInspeccionPlaga.setText("Modificar/eliminar las plagas evidenciadas durante la inspección");
        btnModificarInspeccionPlaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarInspeccionPlagaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtFecInspeccion, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jBoxLugarProd, 0, 248, Short.MAX_VALUE)
                    .addComponent(txtCantidadPlantas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jBoxNumeroLote, 0, 250, Short.MAX_VALUE)
                    .addComponent(jBoxAsistente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBoxEstado, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizar))
            .addComponent(txtObservaciones, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(btnModificarInspeccionPlaga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAsociarInspeccionPlagas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1))
                .addGap(0, 609, Short.MAX_VALUE))
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidadPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFecInspeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBoxAsistente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBoxNumeroLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnActualizar))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsociarInspeccionPlagas)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarInspeccionPlaga)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsociarInspeccionPlagasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsociarInspeccionPlagasActionPerformed
        if (txtCantidadPlantas.getText().trim().isEmpty()
            || txtFecInspeccion.getText().trim().isEmpty()
            || jBoxEstado.getSelectedIndex() <= 0
            || jBoxAsistente.getSelectedIndex() <= 0
            || jBoxNumeroLote.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this,
                "Complete todos los campos obligatorios.");
            return;
        }
        try {
            guardarInspeccion();
            // =======================
            // 6️⃣ ACTUALIZAR BD
            // =======================
            boolean ok = new InspeccionFitosanitariaDAO().actualizar(inspeccionfitosanitariaActual);

            if (ok) {
                JOptionPane.showMessageDialog(this,"Inspección actualizada correctamente. A continuación, asocia las plagas a dicha inspección");
                Dashboard.ShowJPanel(new UpInspeccionPlaga(inspeccionfitosanitariaActual));
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar la inspección.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAsociarInspeccionPlagasActionPerformed

    private void jBoxLugarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBoxLugarProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBoxLugarProdActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (txtCantidadPlantas.getText().trim().isEmpty()
            || txtFecInspeccion.getText().trim().isEmpty()
            || jBoxEstado.getSelectedIndex() <= 0
            || jBoxAsistente.getSelectedIndex() <= 0
            || jBoxNumeroLote.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this,
                "Complete todos los campos obligatorios.");
            return;
        }
        try {
            guardarInspeccion();
            // =======================
            // 6️⃣ ACTUALIZAR BD
            // =======================
            boolean ok = new InspeccionFitosanitariaDAO().actualizar(inspeccionfitosanitariaActual);

            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Inspección actualizada correctamente.");
                Dashboard.ShowJPanel(new GestionInspeccionesFitosanitarias());
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar la inspección.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
        Dashboard.ShowJPanel(new GestionInspeccionesFitosanitarias());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtFecInspeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFecInspeccionActionPerformed

    }//GEN-LAST:event_txtFecInspeccionActionPerformed

    private void txtCantidadPlantasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadPlantasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadPlantasActionPerformed

    private void jBoxNumeroLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBoxNumeroLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBoxNumeroLoteActionPerformed

    private void btnModificarInspeccionPlagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarInspeccionPlagaActionPerformed
        if (txtCantidadPlantas.getText().trim().isEmpty()
            || txtFecInspeccion.getText().trim().isEmpty()
            || jBoxEstado.getSelectedIndex() <= 0
            || jBoxAsistente.getSelectedIndex() <= 0
            || jBoxNumeroLote.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this,
                "Complete todos los campos obligatorios.");
            return;
        }
        try {
            guardarInspeccion();
            // =======================
            // 6️⃣ ACTUALIZAR BD
            // =======================
            boolean ok = new InspeccionFitosanitariaDAO().actualizar(inspeccionfitosanitariaActual);

            if (ok) {
                JOptionPane.showMessageDialog(this,"Inspección actualizada correctamente. A continuación, puedes editar las plagas que están asociadas a la inspección");
                Dashboard.ShowJPanel(new UpInspeccionPlagaUpdate(inspeccionfitosanitariaActual));
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar la inspección.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnModificarInspeccionPlagaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAsociarInspeccionPlagas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnModificarInspeccionPlaga;
    private javax.swing.JComboBox<String> jBoxAsistente;
    private javax.swing.JComboBox<String> jBoxEstado;
    private javax.swing.JComboBox<String> jBoxLugarProd;
    private javax.swing.JComboBox<String> jBoxNumeroLote;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCantidadPlantas;
    private javax.swing.JTextField txtFecInspeccion;
    private javax.swing.JTextField txtObservaciones;
    // End of variables declaration//GEN-END:variables
}
