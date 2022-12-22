/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pe.edu.upeu.app.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import pe.com.syscenterlife.formvalid.ValidatorItem;
import pe.edu.upeu.app.dao.ClienteDAO;
import pe.edu.upeu.app.dao.ClienteDaoI;
import pe.edu.upeu.app.modelo.ClienteTO;
import pe.edu.upeu.app.util.ErrorLogger;
import pe.edu.upeu.app.util.MsgBox;

enum TIPOCLXIENTE {
    Quincenal, Mensual, Trimestral, UnAño, DosAños
};


public class MainCliente extends javax.swing.JPanel {

    ClienteDaoI cDao;
    DefaultTableModel modelo;
    TableRowSorter<TableModel> trsfiltro;
    MsgBox msg;
    static ErrorLogger log = new ErrorLogger(MainCliente.class.getName());

    public MainCliente() {
        initComponents();
        ListarClientes();
        for (TIPOCLXIENTE myVar : TIPOCLXIENTE.values()) {
            //cbxPlan.addItem(myVar.toString());
        }
    }

    public void ListarClientes() {
        cDao = new ClienteDAO();
        List<ClienteTO> listarclientes = cDao.listarClientes();
        jTable1.setAutoCreateRowSorter(true);
        modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        Object[] ob = new Object[7];
        for (int i = 0; i < listarclientes.size(); i++) {
            
            ob[0] = listarclientes.get(i).getDni();
            ob[1] = listarclientes.get(i).getNombres();
            ob[2] = listarclientes.get(i).getN_licencia();
            ob[3] = listarclientes.get(i).getFecha_entrega();
            ob[4] = listarclientes.get(i).getCliente_top();
            ob[5] = listarclientes.get(i).getFecha_cotrato();
            modelo.addRow(ob);
        }
        jTable1.setModel(modelo);
    }

    private void paintForm() {
        if (jTable1.getSelectedRow() != -1) {
            modelo = (DefaultTableModel) jTable1.getModel();
            int rowx = jTable1.getSelectedRow();
            Object valor = jTable1.getValueAt(rowx, 1);
            cDao = new ClienteDAO();
            ClienteTO d = cDao.buscarClientes(valor.toString());
            txtDNI.setText(d.getDni());
            txtNombre.setText(d.getNombres());
            txtlicencia.setText(d.getN_licencia());
            txtFE.setText(d.getFecha_entrega());
            txtClienteTop.setText(d.getCliente_top());
            txtFC.setText(d.getFecha_cotrato());

            txtDNI.setEditable(false);
            txtNombre.setEditable(false);
            //txtFI1.setEditable(false);
            txtFE.setEditable(false);
            txtClienteTop.setEditable(false);
            txtFC.setEditable(false);

            btnRegistrar.setText("MODIFICAR");
            
        } else {
            txtNombre.setEditable(true);
            txtlicencia.setEditable(true);
            //txtFI1.setEditable(true);
            txtFE .setEditable(true);
            txtClienteTop.setEditable(true);
            txtFC.setEditable(true);
            
        }

    }

    public void resetForm() {
        txtDNI.setText("");
        txtNombre.setText("");
        //cbxPlan.setSelectedIndex(0);
        txtlicencia.setText("");
        //txtFI1.setText("");
        txtFE.setText("");
        txtClienteTop.setText("");
        txtFC.setText("");
  
        txtDNI.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtNombres = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtDNI = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtlicencia = new javax.swing.JTextField();
        txtClienteTop = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFE = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(31, 167, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombres.setBackground(new java.awt.Color(21, 109, 147));
        txtNombres.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNombres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombresMouseClicked(evt);
            }
        });
        txtNombres.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(18, 62, 94));
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel5.setForeground(new java.awt.Color(204, 204, 0));

        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(0, 102, 153));
        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(0, 102, 153));
        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(0, 102, 153));
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar)
                .addGap(118, 118, 118))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtNombres.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 550, 40));
        txtNombres.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, -420, -1, -1));

        jPanel3.setBackground(new java.awt.Color(31, 167, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIActionPerformed(evt);
            }
        });
        jPanel3.add(txtDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 112, -1));

        jLabel2.setFont(new java.awt.Font("Alegreya Sans Black", 3, 14)); // NOI18N
        jLabel2.setText("DNI");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 155, -1));

        jLabel3.setFont(new java.awt.Font("Alegreya Sans Black", 3, 14)); // NOI18N
        jLabel3.setText("Nombres");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, 30));

        jLabel6.setFont(new java.awt.Font("Alegreya Sans Black", 3, 14)); // NOI18N
        jLabel6.setText("Nº Licencia");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, 30));

        txtFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFCActionPerformed(evt);
            }
        });
        jPanel3.add(txtFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 90, -1));

        jLabel4.setFont(new java.awt.Font("Alegreya Sans Black", 3, 14)); // NOI18N
        jLabel4.setText("Fecha_Contrato");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, 20));

        txtlicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlicenciaActionPerformed(evt);
            }
        });
        jPanel3.add(txtlicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 90, -1));

        txtClienteTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteTopActionPerformed(evt);
            }
        });
        jPanel3.add(txtClienteTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 90, -1));

        jLabel8.setFont(new java.awt.Font("Alegreya Sans Black", 3, 14)); // NOI18N
        jLabel8.setText("ClienteTOP");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, 20));

        jLabel7.setFont(new java.awt.Font("Alegreya Sans Black", 3, 14)); // NOI18N
        jLabel7.setText("Fecha Entrega");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, 20));
        jPanel3.add(txtFE, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 90, -1));

        txtNombres.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 820, 100));

        jPanel4.add(txtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 990, 170));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombres", "Nº Licencia", "Fecha Entrega", "Cliente Top", "Fecha Contrato"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(90);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, 760, 170));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Lista de Clientes");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 170, 30));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer-service.png"))); // NOI18N
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-11, 0, 1170, 420));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1030, 420));

        jPanel1.setBackground(new java.awt.Color(18, 62, 94));

        txtFiltro.setBackground(new java.awt.Color(242, 242, 242));
        txtFiltro.setFont(new java.awt.Font("Segoe UI Semibold", 3, 12)); // NOI18N
        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(21, 109, 147));
        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN DE TRÁMITE");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 90));
    }// </editor-fold>//GEN-END:initComponents

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        txtFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String cadena = (txtFiltro.getText());
                System.out.println("v:" + cadena);
                txtFiltro.setText(cadena);
                repaint();
                trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText()
                )
            );
        }
        });
        System.out.println("llego");
        trsfiltro = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);     // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void txtNombresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresMouseClicked

    private void txtClienteTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteTopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteTopActionPerformed

    private void txtlicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlicenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlicenciaActionPerformed

    private void txtFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFCActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDNIActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        paintForm();
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        cDao = new ClienteDAO();
        if (jTable1.getSelectedRowCount() > 0) {
            try {
                modelo = (DefaultTableModel) jTable1.getModel();
                int rowx = jTable1.getSelectedRow();
                Object valor = jTable1.getValueAt(rowx, 1);
                msg = new MsgBox();
                if (msg.showConfirmCustom("Esta seguro de eliminar este registro DNI: " + valor + "?", "Mensaje de Confirmación", "") == 0) {
                    modelo.removeRow(rowx);
                    cDao.delete(valor.toString());
                    resetForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un item");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        List<ValidatorItem> vals = new ArrayList<>();
        vals.add(new ValidatorItem("required|number|min:8|max:8", txtDNI, "DNI"));
        vals.add(new ValidatorItem("required", txtNombres, "Nombres"));
        //vals.add(new ValidatorItem("required", cbxPlan, "Tipo"));
        vals.add(new ValidatorItem("required|Número de licencia", txtlicencia, "Licencia"));
        vals.add(new ValidatorItem("required|Fecha entrega", txtFE, "Fecha_Entrefa"));

        vals.add(new ValidatorItem("required|Si o No ", txtClienteTop, "ClienteTop"));
        vals.add(new ValidatorItem("required|Si o No", txtFC, "Fecha-Contrato"));

        cDao = new ClienteDAO();
        ClienteTO to = new ClienteTO();

        to.setDni(txtDNI.getText());
        to.setNombres(txtNombre.getText());
        to.setN_licencia(txtlicencia.getText());
        to.setFecha_entrega(txtFE.getText());
        to.setCliente_top(txtClienteTop.getText());
        to.setFecha_cotrato(txtFC.getText());

        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            try {
                int resultado = cDao.update(to);
                if (resultado != 0) {
                    modelo = (DefaultTableModel) jTable1.getModel();
                    Object nuevo[] = {fila + 1, to.getDni(), to.getNombres(), to.getN_licencia(), to.getFecha_entrega(), to.getCliente_top(), to.getFecha_cotrato()};
                    modelo.removeRow(fila);
                    modelo.insertRow(fila, nuevo);
                    resetForm();
                    JOptionPane.showMessageDialog(this, "Re registro");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            try {
                msg = new MsgBox();
                if (msg.showConfirmCustom("Continuar con la creación de Usuario:?", "¡Advertencia!", "") == 0) {
                    if (cDao.create(to) != 0) {
                        /*modelo = (DefaultTableModel) jTable1.getModel();
                        Object nuevo[] = {modelo.getRowCount() + 1, to.getDniruc(), to.getNombres(), to.getPlan() , to.getTiempo(), to.getFecha_inicio(), to.getFecha_final(), to.getCliente_top(), to.getDescuento()};
                        modelo.addRow(nuevo);*/
                        resetForm();
                        ListarClientes();
                        JOptionPane.showMessageDialog(this, "Registrado");
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        resetForm();
        btnRegistrar.setText("REGISTRAR");

        txtDNI.setEditable(true);
        txtNombre.setEditable(true);
        txtlicencia.setEditable(true);
        //cbxPlan.setEditable(true);
        //txtFI1.setEditable(true);
        txtFE.setEditable(true);
        txtClienteTop.setEditable(true);
        txtFC.setEditable(true);

        jTable1.getSelectionModel().clearSelection();
    }//GEN-LAST:event_btnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtClienteTop;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtFC;
    private javax.swing.JTextField txtFE;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPanel txtNombres;
    private javax.swing.JTextField txtlicencia;
    // End of variables declaration//GEN-END:variables
}
