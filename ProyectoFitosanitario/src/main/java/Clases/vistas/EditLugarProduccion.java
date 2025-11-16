/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Clases.vistas;

import Clases.dao.LugarProduccionDAO;
import Clases.modelo.LugarProduccion;
import Clases.dao.UsuarioDAO;
import Clases.libreria.Dashboard;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ricar
 */
public class EditLugarProduccion extends javax.swing.JPanel {

    private LugarProduccion lugarproduccionActual;

    public EditLugarProduccion() {
        initComponents();
        cargarUsuarios();
    }

    public void setLugarProduccion(LugarProduccion lugarproduccion) {
        this.lugarproduccionActual = lugarproduccion;

        // Rellenar los campos de texto
        txtNombre.setText(lugarproduccion.getNomLugarProduccion());
        txtRegistroICA.setText(lugarproduccion.getNroRegistroICA());

        // Seleccionar el usuario correspondiente en el combo
        if (lugarproduccion.getNombreUsuario() != null) {
            jBoxUsuarioProductor.setSelectedItem(lugarproduccion.getNombreUsuario());
        } else {
            jBoxUsuarioProductor.setSelectedIndex(0); // Sel
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtRegistroICA.setText("");
        jBoxUsuarioProductor.setSelectedIndex(0);
    }

    private void cargarUsuarios() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            jBoxUsuarioProductor.removeAllItems();
            jBoxUsuarioProductor.addItem("Seleccione un usuario");

            dao.listarTodos().forEach(usu -> jBoxUsuarioProductor.addItem(usu.getNombreCompleto()));

            // ?Habilitar autocompletado
            AutoCompleteDecorator.decorate(jBoxUsuarioProductor);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtRegistroICA = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBoxUsuarioProductor = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Editar Municipio: ");

        jLabel2.setText("Nombre");

        txtRegistroICA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRegistroICAActionPerformed(evt);
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
        jButton2.setText("Actualizar Lugar de producción");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Numero Registro ICA");

        jLabel3.setText("Productor");

        jBoxUsuarioProductor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                    .addComponent(txtRegistroICA)
                    .addComponent(jBoxUsuarioProductor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRegistroICA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(jBoxUsuarioProductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(btnCancelar))
                .addContainerGap(180, Short.MAX_VALUE))
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
        Dashboard.ShowJPanel(new GestionLugaresProduccion());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (lugarproduccionActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un Lugar de produccion cargado para actualizar.");
            return;
        }

        if (txtNombre.getText().trim().isEmpty()
                || txtRegistroICA.getText().trim().isEmpty()
                || jBoxUsuarioProductor.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos antes de actualizar.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Crear DAO de usuarios para obtener el ID según el nombre seleccionado
            UsuarioDAO usuDAO = new UsuarioDAO();
            String nombreUsuario = jBoxUsuarioProductor.getSelectedItem().toString();
            String idUsu = usuDAO.obtenerIdUsuarioPorNombre(nombreUsuario); // método que debes tener en tu DAO

            if (idUsu == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el usuario seleccionado.");
                return;
            }

            // Actualizar valores del lugar de produccion
            lugarproduccionActual.setNomLugarProduccion(txtNombre.getText());
            lugarproduccionActual.setNroRegistroICA(txtRegistroICA.getText());
            lugarproduccionActual.setIdUsuarioProductor(idUsu); // 👈 ahora se actualiza correctamente

            // Llamar al DAO para actualizar en BD
            LugarProduccionDAO lugarproduccionDAO = new LugarProduccionDAO();
            boolean actualizado = lugarproduccionDAO.actualizar(lugarproduccionActual);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Lugar de producción actualizado correctamente.");
                Dashboard.ShowJPanel(new GestionMunicipios());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el Lugar de produccion.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtRegistroICAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegistroICAActionPerformed

    }//GEN-LAST:event_txtRegistroICAActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> jBoxUsuarioProductor;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRegistroICA;
    // End of variables declaration//GEN-END:variables
}
