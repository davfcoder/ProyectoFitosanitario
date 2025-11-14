/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Clases.vistas;

import Clases.dao.PredioDAO;
import Clases.modelo.Predio;
import Clases.dao.MunicipioDAO;
import Clases.dao.DepartamentoDAO;
import Clases.dao.VeredaDAO;
import Clases.dao.UsuarioDAO;
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
public class EditPredio extends javax.swing.JPanel {

    private Predio predioActual;

    public EditPredio() {
        initComponents();
        cargarDepartamentos();
        configurarCombosInactivos();
        configurarEventoDepartamento();
        configurarEventoMunicipio();
        cargarUsuarios();
        cargarLugarProduccion();
    }

    // Cargar datos del predio seleccionado
    public void setPredio(Predio predio) {
        this.predioActual = predio;

        txtINumPredial.setText(predio.getNumPredial());
        txtNroRegistroICA.setText(predio.getNroRegistroICA());
        txtNombre.setText(predio.getNomPredio());
        txtDireccion.setText(predio.getDireccion());
        txtLongitud.setText(predio.getCx());
        txtLatitud.setText(predio.getCy());
        txtAreaTotal.setText(String.valueOf(predio.getAreaTotal()));

//      1️⃣ Seleccionar Departamento
        if (predio.getNombreDepartamento() != null) {
            jBoxDepartamento.setSelectedItem(predio.getNombreDepartamento());
        }

        DepartamentoDAO depDAO = new DepartamentoDAO();
        String idDep = depDAO.obtenerIdPorNombre(predio.getNombreDepartamento());
        if (idDep != null) {
            cargarMunicipiosPorDepartamento(idDep);
        }

        // 3️⃣ Seleccionar Municipio
        if (predio.getNombreMunicipio() != null) {
            jBoxMunicipio.setSelectedItem(predio.getNombreMunicipio());
        }

        // 4️⃣ Cargar Veredas del municipio seleccionado
        MunicipioDAO munDAO = new MunicipioDAO();
        String idMun = munDAO.obtenerIdPorNombre(predio.getNombreMunicipio());
        if (idMun != null) {
            cargarVeredasPorMunicipio(idMun);
        }

        // 5️⃣ Seleccionar Vereda
        if (predio.getNombreVereda() != null) {
            jBoxVereda.setSelectedItem(predio.getNombreVereda());
        }
    }

    private void limpiarCampos() {

        txtINumPredial.setText("");
        txtNroRegistroICA.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        jBoxDepartamento.setSelectedIndex(0);
        jBoxMunicipio.removeAllItems();
        jBoxMunicipio.addItem("Seleccione un municipio");
        jBoxMunicipio.setEnabled(false);
        jBoxVereda.removeAllItems();
        jBoxVereda.addItem("Seleccione una vereda");
        jBoxVereda.setEnabled(false);
        txtLongitud.setText("");
        txtLatitud.setText("");
        txtAreaTotal.setText("");
        jBoxPropietario.setSelectedIndex(0);
        jBoxLugarProd.removeAllItems();
        jBoxLugarProd.addItem("Seleccione un lugar");
        jBoxLugarProd.setEnabled(false);
    }

    // ---------------------------------------------------------
    // DESHABILITAR COMBOS AL INICIO
    // ---------------------------------------------------------
    private void configurarCombosInactivos() {
        jBoxMunicipio.removeAllItems();
        jBoxMunicipio.addItem("Seleccione un municipio");
        jBoxMunicipio.setEnabled(false);

        jBoxVereda.removeAllItems();
        jBoxVereda.addItem("Seleccione una vereda");
        jBoxVereda.setEnabled(false);

        jBoxLugarProd.setEnabled(false);
    }

    // ---------------------------------------------------------
    // CARGAR DEPARTAMENTOS
    // ---------------------------------------------------------
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

    // ---------------------------------------------------------
    // EVENTO: CUANDO SE SELECCIONA DEPARTAMENTO → CARGAR MUNICIPIOS
    // ---------------------------------------------------------
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
                            jBoxMunicipio.setEnabled(true);  // ✔ Activar
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar municipios: " + ex.getMessage());
                    }
                } else {
                    configurarCombosInactivos();
                }
            }
        });
    }

    // ---------------------------------------------------------
    // CARGAR MUNICIPIOS FILTRADOS
    // ---------------------------------------------------------
    private void cargarMunicipiosPorDepartamento(String idDepto) {
        try {
            MunicipioDAO dao = new MunicipioDAO();
            jBoxMunicipio.removeAllItems();
            jBoxMunicipio.addItem("Seleccione un municipio");

            dao.listarPorDepartamento(idDepto)
                    .forEach(mun -> jBoxMunicipio.addItem(mun));

            AutoCompleteDecorator.decorate(jBoxMunicipio);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar municipios: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // EVENTO: CUANDO SE SELECCIONA MUNICIPIO → CARGAR VEREDAS
    // ---------------------------------------------------------
    private void configurarEventoMunicipio() {

        jBoxMunicipio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (jBoxMunicipio.getSelectedIndex() > 0) {

                    String nombreMunicipio = (String) jBoxMunicipio.getSelectedItem();

                    try {
                        MunicipioDAO munDAO = new MunicipioDAO();
                        String idMunicipio = munDAO.obtenerIdPorNombre(nombreMunicipio);

                        if (idMunicipio != null) {
                            cargarVeredasPorMunicipio(idMunicipio);
                            jBoxVereda.setEnabled(true);   // ✔ Activar Vereda
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar veredas: " + ex.getMessage());
                    }

                } else {
                    jBoxVereda.removeAllItems();
                    jBoxVereda.addItem("Seleccione una vereda");
                    jBoxVereda.setEnabled(false);
                }
            }
        });
    }

    // ---------------------------------------------------------
    // CARGAR VEREDAS FILTRADAS
    // ---------------------------------------------------------
    private void cargarVeredasPorMunicipio(String idMunicipio) {
        try {
            VeredaDAO dao = new VeredaDAO();
            jBoxVereda.removeAllItems();
            jBoxVereda.addItem("Seleccione una vereda");

            dao.listarVeredasPorMunicipio(idMunicipio)
                    .forEach(ver -> jBoxVereda.addItem(ver)); // ver es String

            AutoCompleteDecorator.decorate(jBoxVereda);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar veredas: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // CARGAR PROPIETARIOS
    // ---------------------------------------------------------
    private void cargarUsuarios() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            jBoxPropietario.removeAllItems();
            jBoxPropietario.addItem("Seleccione un usuario");

            dao.listarTodos().forEach(usu -> jBoxPropietario.addItem(usu.getNombreCompleto()));

            AutoCompleteDecorator.decorate(jBoxPropietario);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // CARGAR LUGAR PRODUCCION
    // ---------------------------------------------------------
    private void cargarLugarProduccion() {
        try {
            LugarProduccionDAO dao = new LugarProduccionDAO();
            jBoxLugarProd.removeAllItems();
            jBoxLugarProd.addItem("Seleccione un lugar produccion");

            dao.listarTodos().forEach(lgr -> jBoxLugarProd.addItem(lgr.getNomLugarProduccion()));

            AutoCompleteDecorator.decorate(jBoxLugarProd);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar lugar produccion: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtINumPredial = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtLongitud = new javax.swing.JTextField();
        txtAreaTotal = new javax.swing.JTextField();
        txtLatitud = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNroRegistroICA = new javax.swing.JTextField();
        jBoxDepartamento = new javax.swing.JComboBox<>();
        jBoxMunicipio = new javax.swing.JComboBox<>();
        jBoxVereda = new javax.swing.JComboBox<>();
        jBoxPropietario = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jBoxLugarProd = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(750, 430));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Editar Predio: ");

        jLabel2.setText("Numero Predial");

        txtINumPredial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtINumPredialActionPerformed(evt);
            }
        });

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
        jButton2.setText("Actualizar predio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Nro Registro ICA ");

        jLabel4.setText("Nombre");

        jLabel5.setText("Dirección");

        jLabel6.setText("Departamento");

        jLabel7.setText("Municipio");

        jLabel8.setText("Vereda");

        jLabel9.setText("Longitud");

        jLabel10.setText("Area Total");

        jLabel11.setText("Latitud");

        jLabel12.setText("Propietario");

        jBoxDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jBoxDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBoxDepartamentoActionPerformed(evt);
            }
        });

        jBoxMunicipio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBoxVereda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBoxPropietario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setText("Lugar de Produccion");

        jBoxLugarProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(308, 308, 308)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtINumPredial, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(txtNroRegistroICA, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(348, 348, 348)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(315, 315, 315)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBoxDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
                        .addComponent(jBoxMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jBoxVereda, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtAreaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(332, 332, 332)
                        .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBoxPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(145, 145, 145)
                        .addComponent(btnCancelar)
                        .addGap(41, 41, 41)
                        .addComponent(jButton2)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtINumPredial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNroRegistroICA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBoxMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8)
                        .addGap(4, 4, 4)
                        .addComponent(jBoxVereda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel11)))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(4, 4, 4)
                        .addComponent(txtAreaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel13)))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel12))
                    .addComponent(jBoxLugarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelar)
                            .addComponent(jButton2)))))
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

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
        Dashboard.ShowJPanel(new GestionPredios());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (predioActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un Predio cargado para actualizar.");
            return;
        }

        // VALIDACIÓN SOLO DE LOS CAMPOS REALES A ACTUALIZAR
        if (txtINumPredial.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtNroRegistroICA.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()
                || txtLongitud.getText().trim().isEmpty()
                || txtLatitud.getText().trim().isEmpty()
                || txtAreaTotal.getText().trim().isEmpty()
                || jBoxVereda.getSelectedIndex() <= 0
                || jBoxPropietario.getSelectedIndex() <= 0
                || jBoxLugarProd.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // -------------------------------
            // OBTENER ID VEREDA
            // -------------------------------
            String nombreVereda = (String) jBoxVereda.getSelectedItem();
            VeredaDAO verDAO = new VeredaDAO();
            String idVereda = verDAO.obtenerIdPorNombre(nombreVereda);

            if (idVereda == null) {
                JOptionPane.showMessageDialog(this, "No se encontró la Vereda seleccionada.");
                return;
            }

            // -------------------------------
            // OBTENER ID PROPIETARIO
            // -------------------------------
            String nombreProp = (String) jBoxPropietario.getSelectedItem();
            UsuarioDAO usuDAO = new UsuarioDAO();
            String idProp = usuDAO.obtenerIdUsuarioPorNombre(nombreProp);

            if (idProp == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el Propietario seleccionado.");
                return;
            }

            // -------------------------------
            // OBTENER ID LUGAR DE PRODUCCIÓN
            // -------------------------------
            String nombreLugar = (String) jBoxLugarProd.getSelectedItem();
            LugarProduccionDAO lpDAO = new LugarProduccionDAO();
            String idLugar = lpDAO.obtenerIdPorNombre(nombreLugar);

            if (idLugar == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el Lugar de Producción seleccionado.");
                return;
            }

            // =============================
            // ACTUALIZAR OBJETO predioActual
            // =============================
            predioActual.setNumPredial(txtINumPredial.getText());
            predioActual.setNroRegistroICA(txtNroRegistroICA.getText());
            predioActual.setNomPredio(txtNombre.getText());
            predioActual.setDireccion(txtDireccion.getText());
            predioActual.setCx(txtLongitud.getText());
            predioActual.setCy(txtLatitud.getText());
            predioActual.setAreaTotal(Float.parseFloat(txtAreaTotal.getText()));
            predioActual.setIdVereda(idVereda);
            predioActual.setIdUsuarioPropietario(idProp);
            predioActual.setIdLugarProduccion(idLugar);

            // Actualizar BD
            PredioDAO predioDAO = new PredioDAO();
            boolean actualizado = predioDAO.actualizar(predioActual);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Predio actualizado correctamente.");
                Dashboard.ShowJPanel(new GestionPredios());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el Predio.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtINumPredialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtINumPredialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtINumPredialActionPerformed

    private void jBoxDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBoxDepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBoxDepartamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> jBoxDepartamento;
    private javax.swing.JComboBox<String> jBoxLugarProd;
    private javax.swing.JComboBox<String> jBoxMunicipio;
    private javax.swing.JComboBox<String> jBoxPropietario;
    private javax.swing.JComboBox<String> jBoxVereda;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAreaTotal;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtINumPredial;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNroRegistroICA;
    // End of variables declaration//GEN-END:variables
}
