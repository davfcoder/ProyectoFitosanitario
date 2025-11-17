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
public class UpInspeccionFitosanitaria extends javax.swing.JPanel {

    /**
     * Creates new form Principal
     */
    public UpInspeccionFitosanitaria() {
        initComponents();
        configurarCamposEditables();
        ///OK
        cargarUsuarios();
        configurarLoteInactiva();
        configurarEventoLugarProd();
        cargarEstados();
        cargarLugarProduccion();
        configurarPlaceholderFecha();
    }

    private void configurarCamposEditables() {
        txtCantidadPlantas.setEditable(true);
        txtFecInspeccion.setEditable(true);
        txtObservaciones.setEditable(true);
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
    // ---------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCantidadPlantas = new javax.swing.JTextField();
        txtFecInspeccion = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBoxLugarProd = new javax.swing.JComboBox<>();
        jBoxNumeroLote = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jBoxEstado = new javax.swing.JComboBox<>();
        txtObservaciones = new javax.swing.JTextField();
        jBoxAsistente = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Nueva Inspección:");

        jLabel2.setText("Cantidad de plantas");

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

        btnGuardar.setBackground(new java.awt.Color(51, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar inspección");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel3.setText("Estado Fenologico");

        jLabel4.setText("Fecha de Inspección");

        jLabel5.setText("Asistente Tecnico");

        jLabel6.setText("Lugar de Producción");

        jLabel7.setText("Numero de lote");

        jBoxLugarProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBoxNumeroLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Observaciones");

        jBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBoxAsistente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtFecInspeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jBoxAsistente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBoxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtObservaciones)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(285, 285, 285)
                                .addComponent(jLabel3))
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnCancelar)
                                    .addGap(423, 423, 423)
                                    .addComponent(btnGuardar))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(141, 141, 141)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jBoxNumeroLote, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(32, 32, 32))
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
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBoxNumeroLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    // ============================
    // VALIDACIÓN DE CAMPOS
    // ============================
    if (txtCantidadPlantas.getText().trim().isEmpty()
            || txtFecInspeccion.getText().trim().isEmpty()
            || jBoxEstado.getSelectedIndex() <= 0
            || jBoxAsistente.getSelectedIndex() <= 0
            || jBoxNumeroLote.getSelectedIndex() <= 0) {

        JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos obligatorios.",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {

        // ============================
        // NUMÉRICOS
        // ============================
        int cantidadPlantas;
        try {
            cantidadPlantas = Integer.parseInt(txtCantidadPlantas.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad de plantas debe ser un número entero válido.");
            return;
        }

        // ============================
        // FECHAS
        // ============================
        java.sql.Date fechaInspeccion;

        try {
            fechaInspeccion = java.sql.Date.valueOf(txtFecInspeccion.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Fecha inválida. Formato correcto: YYYY-MM-DD");
            return;
        }

        // ==================================
        // OBTENER ID ASISTENTE
        // ==================================
        String nombreAsistente = (String) jBoxAsistente.getSelectedItem();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String idUsuario = usuarioDAO.obtenerIdUsuarioPorNombre(nombreAsistente);

        if (idUsuario == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el ID del asistente técnico.");
            return;
        }

        // ==================================
        // OBTENER ID LOTE
        // ==================================
        String numeroLote = (String) jBoxNumeroLote.getSelectedItem();
        LoteDAO loteDAO = new LoteDAO();
        String idLote = loteDAO.obtenerIdPorNombre(numeroLote);

        if (idLote == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el ID del lote seleccionado.");
            return;
        }

        // ==================================
        // CREAR OBJETO INSPECCIÓN
        // ==================================
        InspeccionFitosanitaria insp = new InspeccionFitosanitaria();

        insp.setCantidadPlantas(cantidadPlantas);
        insp.setEstadoFenologico((String) jBoxEstado.getSelectedItem());
        insp.setFecInspeccion(fechaInspeccion);
        insp.setObservaciones(txtObservaciones.getText().trim());
        insp.setIdUsuarioAsistente(idUsuario);
        insp.setIdLote(idLote);

        // ==================================
        // GUARDAR EN BD
        // ==================================
        InspeccionFitosanitariaDAO inspDAO = new InspeccionFitosanitariaDAO();
        boolean guardado = inspDAO.insertar(insp);

        if (guardado) {
            JOptionPane.showMessageDialog(this,
                    "Inspección guardada correctamente.");
            limpiarCampos();
            Dashboard.ShowJPanel(new GestionInspeccionesFitosanitarias());
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar la inspección.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error al guardar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
        Dashboard.ShowJPanel(new GestionInspeccionesFitosanitarias());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtFecInspeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFecInspeccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFecInspeccionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> jBoxAsistente;
    private javax.swing.JComboBox<String> jBoxEstado;
    private javax.swing.JComboBox<String> jBoxLugarProd;
    private javax.swing.JComboBox<String> jBoxNumeroLote;
    private javax.swing.JLabel jLabel1;
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
