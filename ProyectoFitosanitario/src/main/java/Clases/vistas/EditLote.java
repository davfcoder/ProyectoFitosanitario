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
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ricar
 */
public class EditLote extends javax.swing.JPanel {

    private Lote loteActual;

    public EditLote() {
        initComponents();
        configurarCamposEditables();
        cargarEspecies();
        configurarVariedadInactiva();
        configurarEventoNomComun();
        cargarLugarProduccion();
    }

    private void configurarCamposEditables() {
        txtNumero.setEditable(true);
        txtAreaTotal.setEditable(true);
        txtFecSiembra.setEditable(true);
        txtFecEliminacion.setEditable(true);
    }

    // Cargar datos del lote seleccionada
// Cargar datos del lote seleccionado (versión segura)
    public void setLote(Lote lote) {

        this.loteActual = lote;

        // ============================
        // 1️⃣ CAMPOS DIRECTOS
        // ============================
        txtNumero.setText(lote.getNumero() != null ? lote.getNumero() : "");
        txtAreaTotal.setText(lote.getAreaTotal() != null ? lote.getAreaTotal().toString() : "");
        txtFecSiembra.setText(lote.getFecSiembra() != null ? lote.getFecSiembra().toString() : "");
        txtFecEliminacion.setText(lote.getFecEliminacion() != null ? lote.getFecEliminacion().toString() : "");

        // ============================
        // 2️⃣ SELECCIONAR ESPECIE
        // ============================
        if (lote.getNombreEspecie() != null) {
            jBoxNomComun.setSelectedItem(lote.getNombreEspecie());
        } else {
            jBoxNomComun.setSelectedIndex(0); // Seleccionar "Seleccione una especie"
        }

        // ============================
        // 3️⃣ SELECCIONAR VARIEDAD
        // ============================
        if (lote.getNombreVariedad() != null) {
            jBoxVariedad.setSelectedItem(lote.getNombreVariedad());
        } else {
            jBoxVariedad.setSelectedIndex(0);
        }

        // ============================
        // 4️⃣ SELECCIONAR LUGAR PRODUCCIÓN
        // ============================
        if (lote.getNombreLugarProduccion() != null) {
            jBoxLugarProd.setSelectedItem(lote.getNombreLugarProduccion());
        } else {
            jBoxLugarProd.setSelectedIndex(0);
        }
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
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAreaTotal = new javax.swing.JTextField();
        jBoxNomComun = new javax.swing.JComboBox<>();
        jBoxVariedad = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jBoxLugarProd = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Editar Lote: ");

        jLabel2.setText("Numero");

        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });

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

        jButton2.setBackground(new java.awt.Color(51, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Actualizar lote");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Area Total");

        jLabel4.setText("Fecha de Siembra");

        jLabel5.setText("Fecha de Eliminación");

        jLabel6.setText("Nombre Comun");

        jLabel7.setText("Variedad");

        jBoxNomComun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jBoxNomComun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBoxNomComunActionPerformed(evt);
            }
        });

        jBoxVariedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Lugar de producción");

        jBoxLugarProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBoxNomComun, 0, 248, Short.MAX_VALUE)
                            .addComponent(jBoxLugarProd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBoxVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(txtFecSiembra, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(jLabel3)
                                .addComponent(txtAreaTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(txtFecEliminacion)))))
                .addContainerGap(46, Short.MAX_VALUE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAreaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFecSiembra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecEliminacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBoxNomComun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBoxVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(jButton2)
                    .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(167, 167, 167))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFecSiembraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFecSiembraActionPerformed

    }//GEN-LAST:event_txtFecSiembraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
        Dashboard.ShowJPanel(new GestionLotes());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (loteActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un lote cargado.");
            return;
        }

        // Validación rápida
        if (txtNumero.getText().trim().isEmpty()
                || txtAreaTotal.getText().trim().isEmpty()
                || jBoxVariedad.getSelectedIndex() <= 0
                || jBoxLugarProd.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
            return;
        }

        try {
            // Obtener IDs reales
            String idVariedad = new VariedadEspecieDAO()
                    .obtenerIdPorNombre((String) jBoxVariedad.getSelectedItem());

            String idLugar = new LugarProduccionDAO()
                    .obtenerIdPorNombre((String) jBoxLugarProd.getSelectedItem());

            if (idVariedad == null || idLugar == null) {
                JOptionPane.showMessageDialog(this, "Error obteniendo datos asociados.");
                return;
            }

            // =======================
            //  ACTUALIZAR OBJETO
            // =======================
            loteActual.setNumero(txtNumero.getText().trim());
            loteActual.setAreaTotal(Float.valueOf(txtAreaTotal.getText().trim()));
            loteActual.setFecSiembra(Date.valueOf(txtFecSiembra.getText().trim()));
            loteActual.setFecEliminacion(Date.valueOf(txtFecEliminacion.getText().trim()));
            loteActual.setIdVariedad(idVariedad);
            loteActual.setIdLugarProduccion(idLugar);

            // =======================
            //  ACTUALIZAR BD
            // =======================
            boolean ok = new LoteDAO().actualizar(loteActual);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Lote actualizado correctamente.");
                Dashboard.ShowJPanel(new GestionLotes());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el lote.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void jBoxNomComunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBoxNomComunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBoxNomComunActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> jBoxLugarProd;
    private javax.swing.JComboBox<String> jBoxNomComun;
    private javax.swing.JComboBox<String> jBoxVariedad;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAreaTotal;
    private javax.swing.JTextField txtFecEliminacion;
    private javax.swing.JTextField txtFecSiembra;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
