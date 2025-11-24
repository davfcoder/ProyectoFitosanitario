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

    public EditLugarProduccion(LugarProduccion lugarproduccion) {
        initComponents();
        cargarUsuarios();
        this.lugarproduccionActual = lugarproduccion;
        setCamposLugarProduccion();  
    }

    private void setCamposLugarProduccion() {
        if (lugarproduccionActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un Lugar de produccion cargado para actualizar.");
            return;
        }
        txtNombre.setText(lugarproduccionActual.getNomLugarProduccion());
        txtRegistroICA.setText(lugarproduccionActual.getNroRegistroICA());

        // Seleccionar el usuario correspondiente en el combo
        if (lugarproduccionActual.getNombreUsuario() != null) {
            jBoxUsuarioProductor.setSelectedItem(lugarproduccionActual.getNombreUsuario());
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

    private boolean actualizarLugar(){
         // Crear DAO de usuarios para obtener el ID según el nombre seleccionado
            UsuarioDAO usuDAO = new UsuarioDAO();
            String nombreUsuario = jBoxUsuarioProductor.getSelectedItem().toString();
            String idUsu = usuDAO.obtenerIdUsuarioPorNombre(nombreUsuario); // método que debes tener en tu DAO
            // Actualizar valores del lugar de produccion
            lugarproduccionActual.setNomLugarProduccion(txtNombre.getText());
            lugarproduccionActual.setNroRegistroICA(txtRegistroICA.getText());
            lugarproduccionActual.setIdUsuarioProductor(idUsu); // 👈 ahora se actualiza correctamente

            // Llamar al DAO para actualizar en BD
            LugarProduccionDAO lugarproduccionDAO = new LugarProduccionDAO();
            boolean actualizado = lugarproduccionDAO.actualizar(lugarproduccionActual);
            return actualizado;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtRegistroICA = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnActualizarLugar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBoxUsuarioProductor = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        btnVincularPredios = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnDesvincularPredios = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnRegistrarProyeccion = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnModificarProyeccion = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(770, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Editar Lugar de Producción: ");

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

        btnActualizarLugar.setBackground(new java.awt.Color(51, 153, 0));
        btnActualizarLugar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizarLugar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarLugar.setText("Actualizar Lugar de producción");
        btnActualizarLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarLugarActionPerformed(evt);
            }
        });

        jLabel4.setText("Numero Registro ICA");

        jLabel3.setText("Productor");

        jBoxUsuarioProductor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnVincularPredios.setBackground(new java.awt.Color(102, 204, 255));
        btnVincularPredios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVincularPredios.setText("Vincular predios");
        btnVincularPredios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVincularPrediosActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 102, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Para vincular o desvincular los predio que conforman el lugar de producción:");

        btnDesvincularPredios.setBackground(new java.awt.Color(255, 204, 0));
        btnDesvincularPredios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDesvincularPredios.setText("Desvincular predios");
        btnDesvincularPredios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesvincularPrediosActionPerformed(evt);
            }
        });

        btnRegistrarProyeccion.setBackground(new java.awt.Color(102, 204, 255));
        btnRegistrarProyeccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrarProyeccion.setText("Registrar proyección");
        btnRegistrarProyeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarProyeccionActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 102, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Parar modificar o registrar nueva información de la proyección de producción del lugar de producción:");

        btnModificarProyeccion.setBackground(new java.awt.Color(255, 204, 0));
        btnModificarProyeccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnModificarProyeccion.setText("Modificar proyección");
        btnModificarProyeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProyeccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRegistroICA, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)))
            .addComponent(jBoxUsuarioProductor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizarLugar))
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnDesvincularPredios, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVincularPredios, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(btnRegistrarProyeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(120, 120, 120))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRegistroICA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBoxUsuarioProductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnActualizarLugar))
                .addGap(24, 24, 24)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesvincularPredios)
                    .addComponent(btnVincularPredios))
                .addGap(32, 32, 32)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarProyeccion)
                    .addComponent(btnModificarProyeccion))
                .addContainerGap(14, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
        Dashboard.ShowJPanel(new GestionLugaresProduccion());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnActualizarLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarLugarActionPerformed
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
            boolean actualizado = actualizarLugar();
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

    }//GEN-LAST:event_btnActualizarLugarActionPerformed

    private void txtRegistroICAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegistroICAActionPerformed

    }//GEN-LAST:event_txtRegistroICAActionPerformed

    private void btnVincularPrediosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVincularPrediosActionPerformed
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
            boolean actualizado = actualizarLugar();
            if (actualizado) {
                // Crear el panel EditLugarProduccion y pasarle el lugar de produccion
                UpLugarProdPredios panelEditar = new UpLugarProdPredios(lugarproduccionActual);
                JOptionPane.showMessageDialog(this, "Lugar de producción actualizado correctamente. Ahora procede a seleccionar los predios que conforman el lugar");
                Dashboard.ShowJPanel(panelEditar);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el Lugar de produccion.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnVincularPrediosActionPerformed

    private void btnDesvincularPrediosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesvincularPrediosActionPerformed
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
            boolean actualizado = actualizarLugar();
            if (actualizado) {
                // Crear el panel EditLugarProduccion y pasarle el lugar de produccion
                UpLugarProdEliminarPredios panelEditar = new UpLugarProdEliminarPredios(lugarproduccionActual);
                JOptionPane.showMessageDialog(this, "Lugar de producción actualizado correctamente. Ahora procede a seleccionar los predios que quieres desvincular del lugar");
                Dashboard.ShowJPanel(panelEditar);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el Lugar de Produccion.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDesvincularPrediosActionPerformed

    private void btnRegistrarProyeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarProyeccionActionPerformed
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
            boolean actualizado = actualizarLugar();
            if (actualizado) {
                // Crear el panel EditLugarProduccion y pasarle el lugar de produccion
                UpLugarProdRegistrarProyeccion panelEditar = new UpLugarProdRegistrarProyeccion(lugarproduccionActual);
                JOptionPane.showMessageDialog(this, "Lugar de producción actualizado correctamente. Ahora procede a registrar los datos de proyección de producción");
                Dashboard.ShowJPanel(panelEditar);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el Lugar de produccion.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnRegistrarProyeccionActionPerformed

    private void btnModificarProyeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProyeccionActionPerformed
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
            boolean actualizado = actualizarLugar();
            if (actualizado) {
                // Crear el panel EditLugarProduccion y pasarle el lugar de produccion
                UpLugarProdModificarProyeccion panelEditar = new UpLugarProdModificarProyeccion(lugarproduccionActual);
                JOptionPane.showMessageDialog(this, "Lugar de producción actualizado correctamente. Ahora procede a modificar/eliminar los datos de proyección de producción");
                Dashboard.ShowJPanel(panelEditar);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el Lugar de produccion.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnModificarProyeccionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarLugar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesvincularPredios;
    private javax.swing.JButton btnModificarProyeccion;
    private javax.swing.JButton btnRegistrarProyeccion;
    private javax.swing.JButton btnVincularPredios;
    private javax.swing.JComboBox<String> jBoxUsuarioProductor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRegistroICA;
    // End of variables declaration//GEN-END:variables
}
