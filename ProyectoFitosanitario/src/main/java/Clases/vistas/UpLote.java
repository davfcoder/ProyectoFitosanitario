/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Clases.vistas;

import Clases.modelo.Lote;
import Clases.dao.LoteDAO;
import Clases.dao.VariedadEspecieDAO;
import Clases.dao.EspecieVegetalDAO;
import Clases.dao.LugarProduccionDAO;
import Clases.libreria.Dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ricar
 */
public class UpLote extends javax.swing.JPanel {

    /**
     * Creates new form Principal
     */
    private String nombreRol;

    public UpLote(String nombreRol) {
        this.nombreRol = nombreRol;
        initComponents();
        configurarCamposEditables(); // <-- NUEVO
        cargarEspecies();
        configurarVariedadInactiva();
        configurarPlaceholderFecha();  // <<--- AGREGA ESTA LÍNEA
        configurarEventoNomComun();
        cargarLugarProduccion();
    }

    private void configurarCamposEditables() {
        txtNumero.setEditable(true);
        txtAreaTotal.setEditable(true);
        txtFecSiembra.setEditable(true);
        txtFecEliminacion.setEditable(true);
    }

    private void configurarEventoNomComun() {
        jBoxNomComun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (jBoxNomComun.getSelectedIndex() <= 0) {
                    configurarVariedadInactiva();
                    return;
                }

                String nombreEspecie = (String) jBoxNomComun.getSelectedItem();

                try {
                    EspecieVegetalDAO espDAO = new EspecieVegetalDAO();
                    String idEspecie = espDAO.obtenerIdPorNombre(nombreEspecie);

                    if (idEspecie != null) {
                        cargarVariedadesPorEspecie(idEspecie);
                        jBoxVariedad.setEnabled(true);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al cargar variedades: " + ex.getMessage());
                }
            }
        });
    }

    // 🔹 Carga municipios filtrados por departamento
// 🔹 Carga variedades filtradas por especie
    private void cargarVariedadesPorEspecie(String idEspecie) {
        try {
            VariedadEspecieDAO dao = new VariedadEspecieDAO();

            jBoxVariedad.removeAllItems();
            jBoxVariedad.addItem("Seleccione una variedad");

            // Lista de variedades por especie
            List<String> variedades = dao.listarPorEspecie(idEspecie);

            // Agrega cada variedad al combo
            variedades.forEach(v -> jBoxVariedad.addItem(v));

            // Autocompletado
            AutoCompleteDecorator.decorate(jBoxVariedad);

            // Activar el combo porque ahora sí tiene elementos
            jBoxVariedad.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar variedades: " + e.getMessage());
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

    private void configurarVariedadInactiva() {
        jBoxVariedad.removeAllItems();
        jBoxVariedad.addItem("Seleccione una variedad");
        jBoxVariedad.setEnabled(false);
    }

    private void limpiarCampos() {

        txtNumero.setText("");
        txtAreaTotal.setText("");
        txtFecSiembra.setText("");
        txtFecEliminacion.setText("");

        jBoxNomComun.setSelectedIndex(0);

        jBoxVariedad.removeAllItems();
        jBoxVariedad.addItem("Seleccione una variedad");
        jBoxVariedad.setEnabled(false);

        jBoxLugarProd.setSelectedIndex(0);
    }

    // 🔹 Carga inicial de Nombres
    private void cargarEspecies() {
        try {
            EspecieVegetalDAO dao = new EspecieVegetalDAO();
            jBoxNomComun.removeAllItems();
            jBoxNomComun.addItem("Seleccione una especie vegetal");

            dao.listarTodos().forEach(espv -> jBoxNomComun.addItem(espv.getNombreComun()));

            // Activar autocompletado
            AutoCompleteDecorator.decorate(jBoxNomComun);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar especies: " + e.getMessage());
        }
    }

    private void configurarPlaceholderFecha() {

        agregarPlaceholder(txtFecSiembra, "YYYY-MM-DD");
        agregarPlaceholder(txtFecEliminacion, "YYYY-MM-DD");
    }

    private void agregarPlaceholder(javax.swing.JTextField campo, String texto) {

        campo.setText(texto);
        campo.setForeground(new java.awt.Color(150, 150, 150));

        campo.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(texto)) {
                    campo.setText("");
                    campo.setForeground(new java.awt.Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(texto);
                    campo.setForeground(new java.awt.Color(150, 150, 150));
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
        txtNumero = new javax.swing.JTextField();
        txtFecSiembra = new javax.swing.JTextField();
        txtFecEliminacion = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAreaTotal = new javax.swing.JTextField();
        jBoxNomComun = new javax.swing.JComboBox<>();
        jBoxVariedad = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jBoxLugarProd = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Nuevo Lote:");

        jLabel2.setText("Numero");

        txtFecSiembra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFecSiembraActionPerformed(evt);
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
        btnGuardar.setText("Guardar lote");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel3.setText("Area Total");

        jLabel4.setText("Fecha de Siembra");

        jLabel5.setText("Fecha de Eliminación");

        jLabel6.setText("Nombre Comun");

        jLabel7.setText("Variedad");

        jBoxNomComun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBoxVariedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Lugar de producción");

        jBoxLugarProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNumero)
                        .addGap(199, 199, 199)
                        .addComponent(txtAreaTotal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(138, 138, 138))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(204, 204, 204))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(161, 161, 161))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(155, 155, 155))
                                    .addComponent(txtFecSiembra)
                                    .addComponent(jBoxNomComun, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jBoxLugarProd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(196, 196, 196)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(206, 206, 206))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(75, 75, 75)
                                        .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jBoxVariedad, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(196, 196, 196))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(137, 137, 137))
                                            .addComponent(txtFecEliminacion))))))
                        .addGap(2, 2, 2)))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(txtFecEliminacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBoxVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnGuardar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAreaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4)
                        .addGap(6, 6, 6)
                        .addComponent(txtFecSiembra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(jBoxNomComun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(162, 162, 162))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Validación de campos requeridos
        if (txtNumero.getText().trim().isEmpty()
                || txtAreaTotal.getText().trim().isEmpty()
                || txtFecSiembra.getText().trim().isEmpty()
                || jBoxNomComun.getSelectedIndex() <= 0
                || jBoxVariedad.getSelectedIndex() <= 0
                || jBoxLugarProd.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // ============================
            // CONVERTIR CAMPOS NUMÉRICOS
            // ============================
            Float areaTotal;
            try {
                areaTotal = Float.parseFloat(txtAreaTotal.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "El área total debe ser un número válido.");
                return;
            }

            // ============================
            // CONVERTIR FECHAS
            // Formato esperado: YYYY-MM-DD
            // ============================
            java.sql.Date fecSiembra;
            java.sql.Date fecEliminacion;

            try {
                fecSiembra = java.sql.Date.valueOf(txtFecSiembra.getText().trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Fecha de siembra inválida. Formato correcto: YYYY-MM-DD");
                return;
            }

            try {
                fecEliminacion = java.sql.Date.valueOf(txtFecEliminacion.getText().trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Fecha de eliminación inválida. Formato correcto: YYYY-MM-DD");
                return;
            }

            // ==================================
            // OBTENER ID DE LA ESPECIE
            // ==================================
            String nombreEspecie = (String) jBoxNomComun.getSelectedItem();
            EspecieVegetalDAO espDAO = new EspecieVegetalDAO();
            String idEspecie = espDAO.obtenerIdPorNombre(nombreEspecie);

            if (idEspecie == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el ID de la especie seleccionada.");
                return;
            }

            // ==================================
            // OBTENER ID DE LA VARIEDAD
            // ==================================
            String nombreVariedad = (String) jBoxVariedad.getSelectedItem();
            VariedadEspecieDAO varDAO = new VariedadEspecieDAO();
            String idVariedad = varDAO.obtenerIdPorNombre(nombreVariedad);

            if (idVariedad == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el ID de la variedad seleccionada.");
                return;
            }

            // ==================================
            // OBTENER ID LUGAR PRODUCCIÓN
            // ==================================
            String nombreLugar = (String) jBoxLugarProd.getSelectedItem();
            LugarProduccionDAO lpDAO = new LugarProduccionDAO();
            String idLugar = lpDAO.obtenerIdPorNombre(nombreLugar);

            if (idLugar == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el ID del lugar de producción.");
                return;
            }

            // ==================================
            // CREAR OBJETO LOTE
            // ==================================
            Lote lote = new Lote();
            lote.setNumero(txtNumero.getText().trim());
            lote.setAreaTotal(areaTotal);
            lote.setFecSiembra(fecSiembra);
            lote.setFecEliminacion(fecEliminacion);
            lote.setIdVariedad(idVariedad);
            lote.setIdLugarProduccion(idLugar);

            // ==================================
            // GUARDAR EN BD
            // ==================================
            LoteDAO loteDAO = new LoteDAO();
            boolean guardado = loteDAO.insertar(lote);

            if (guardado) {
                JOptionPane.showMessageDialog(this,
                        "Lote guardado correctamente.");
                limpiarCampos();
                Dashboard.ShowJPanel(new GestionLotes(nombreRol));
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo guardar el lote.");
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
        Dashboard.ShowJPanel(new GestionLotes(nombreRol));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtFecSiembraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFecSiembraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFecSiembraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> jBoxLugarProd;
    private javax.swing.JComboBox<String> jBoxNomComun;
    private javax.swing.JComboBox<String> jBoxVariedad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAreaTotal;
    private javax.swing.JTextField txtFecEliminacion;
    private javax.swing.JTextField txtFecSiembra;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
