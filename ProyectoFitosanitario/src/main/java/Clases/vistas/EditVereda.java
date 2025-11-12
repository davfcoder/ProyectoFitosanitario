/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Clases.vistas;

import Clases.dao.VeredaDAO;
import Clases.modelo.Vereda;
import Clases.dao.MunicipioDAO;
import Clases.dao.DepartamentoDAO;
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
public class EditVereda extends javax.swing.JPanel {

    private Vereda veredaActual;

    public EditVereda() {
        initComponents();
        configurarMunicipioInactivo();
        cargarDepartamentos();
        configurarEventoDepartamento();
    }

    // ✅ Cargar datos de la vereda seleccionada
    public void setVereda(Vereda vereda) {
        this.veredaActual = vereda;

        txtCodigoDane.setText(vereda.getCodigoDane());
        txtNombre.setText(vereda.getNombre());

        // Mostrar los valores asociados
        if (vereda.getNombreMunicipio() != null) {
            jBoxMunicipio.setSelectedItem(vereda.getNombreMunicipio());
        }
    }

    private void limpiarCampos() {
        txtCodigoDane.setText("");
        txtNombre.setText("");
        jBoxDepartamento.setSelectedIndex(0);
        configurarMunicipioInactivo();
    }

    // ✅ Dejar municipio inactivo hasta elegir departamento
    private void configurarMunicipioInactivo() {
        jBoxMunicipio.removeAllItems();
        jBoxMunicipio.addItem("Seleccione un municipio");
        jBoxMunicipio.setEnabled(false);
    }

    // ✅ Cargar departamentos (solo vista)
    private void cargarDepartamentos() {
        try {
            DepartamentoDAO dao = new DepartamentoDAO();
            jBoxDepartamento.removeAllItems();
            jBoxDepartamento.addItem("Seleccione un departamento");

            dao.listarTodos().forEach(dep -> jBoxDepartamento.addItem(dep.getNombre()));
            AutoCompleteDecorator.decorate(jBoxDepartamento);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar departamentos: " + e.getMessage());
        }
    }

    // ✅ Cargar municipios del departamento seleccionado
    private void cargarMunicipiosPorDepartamento(String idDepto) {
        try {
            MunicipioDAO dao = new MunicipioDAO();
            jBoxMunicipio.removeAllItems();
            jBoxMunicipio.addItem("Seleccione un municipio");

            List<String> municipios = dao.listarPorDepartamento(idDepto);
            municipios.forEach(m -> jBoxMunicipio.addItem(m));

            jBoxMunicipio.setEnabled(true);
            AutoCompleteDecorator.decorate(jBoxMunicipio);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar municipios: " + e.getMessage());
        }
    }

    // ✅ Vincular evento del combo de departamentos
    private void configurarEventoDepartamento() {
        jBoxDepartamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jBoxDepartamento.getSelectedIndex() > 0) {
                    String nombreDepto = (String) jBoxDepartamento.getSelectedItem();
                    try {
                        DepartamentoDAO depDAO = new DepartamentoDAO();
                        String idDepto = depDAO.obtenerIdPorNombre(nombreDepto);
                        if (idDepto != null) {
                            cargarMunicipiosPorDepartamento(idDepto);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar municipios: " + ex.getMessage());
                    }
                } else {
                    configurarMunicipioInactivo();
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
        txtCodigoDane = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBoxMunicipio = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jBoxDepartamento = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Editar Vereda: ");

        jLabel2.setText("Codigo Dane");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
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
        jButton2.setText("Actualizar Vereda");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombre");

        jLabel3.setText("Municipio");

        jBoxMunicipio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Departamento");

        jBoxDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jBoxDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBoxDepartamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBoxDepartamento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigoDane, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBoxMunicipio, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addGap(460, 460, 460)
                                .addComponent(jButton2)))
                        .addGap(60, 60, 60))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txtCodigoDane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(jButton2))
                .addContainerGap(110, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
        Dashboard.ShowJPanel(new GestionMunicipios());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (veredaActual == null) {
            JOptionPane.showMessageDialog(this, "No hay una Vereda cargada para actualizar.");
            return;
        }

        if (txtCodigoDane.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || jBoxMunicipio.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos antes de actualizar.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String nombreMunpo = (String) jBoxMunicipio.getSelectedItem();
            MunicipioDAO munDAO = new MunicipioDAO();
            String idMunpo = munDAO.obtenerIdPorNombre(nombreMunpo);

            if (idMunpo == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el municipio seleccionado.");
                return;
            }

            // Actualizar objeto
            veredaActual.setCodigoDane(txtCodigoDane.getText());
            veredaActual.setNombre(txtNombre.getText());
            veredaActual.setIdMunicipio(idMunpo);

            VeredaDAO veredaDAO = new VeredaDAO();
            boolean actualizado = veredaDAO.actualizar(veredaActual);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Vereda actualizada correctamente.");
                Dashboard.ShowJPanel(new GestionVeredas());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la Vereda.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void jBoxDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBoxDepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBoxDepartamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> jBoxDepartamento;
    private javax.swing.JComboBox<String> jBoxMunicipio;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCodigoDane;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
