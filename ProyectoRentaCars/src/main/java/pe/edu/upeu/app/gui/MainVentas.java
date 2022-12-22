/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pe.edu.upeu.app.gui;

import static com.sun.source.util.Trees.instance;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import static net.sf.jasperreports.engine.xml.JRXmlTemplateDigesterFactory.instance;
import static net.sf.jasperreports.engine.xml.XmlValueHandlerUtils.instance;
import net.sf.jasperreports.view.JasperViewer;
import pe.com.syscenterlife.autocomp.AutoCompleteTextField;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.com.syscenterlife.jtablecomp.ButtonsEditor;
import pe.com.syscenterlife.jtablecomp.ButtonsPanel;
import pe.com.syscenterlife.jtablecomp.ButtonsRenderer;
import pe.edu.upeu.app.dao.CarritoDAO;
import pe.edu.upeu.app.dao.CarritoDaoI;
import pe.edu.upeu.app.dao.ClienteDAO;
import pe.edu.upeu.app.dao.ClienteDaoI;
import pe.edu.upeu.app.dao.ProductoDAO;
import pe.edu.upeu.app.dao.ProductoDaoI;
import pe.edu.upeu.app.dao.VentaDAO;
import pe.edu.upeu.app.dao.VentaDaoI;
import pe.edu.upeu.app.dao.conx.Conn;
import pe.edu.upeu.app.modelo.CarritoTO;
import pe.edu.upeu.app.modelo.ClienteTO;
import pe.edu.upeu.app.modelo.VentaDetalleTO;
import pe.edu.upeu.app.modelo.VentaTO;

/**
 *
 * @author ACER ASPIRE
 */
public class MainVentas extends javax.swing.JPanel {

    /**
     * Creates new form MainVentas
     */
    double cantidad = 0;
    ClienteDaoI daoC;
    CarritoDaoI daoCA;
    ProductoDaoI daoP;
    VentaDaoI daoV;
    List<ModeloDataAutocomplet> items;
    List<ModeloDataAutocomplet> itemsP;
    Connection connection;

    public MainVentas() {
        initComponents();
        /*ESTO HACE QUE SE LISTE*/
        //ID,NAME, OTHER
        buscarCliente();
        txtAutoCompDNI.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
                        && AutoCompleteTextField.dataGetReturnet != null) {
                    txtNombreCliente.setText(AutoCompleteTextField.dataGetReturnet.getNombreDysplay());
                    listarCarrito(txtAutoCompDNI.getText());
                    /*if (ModeloDataAutocomplet.TIPE_DISPLAY.equals("ID") && txtAutoCompDNI.getText().equals(AutoCompleteTextField.dataGetReturnet.getIdx())) {
                        txtNombreCliente.setText(AutoCompleteTextField.dataGetReturnet.getNombreDysplay());
                    } else if (ModeloDataAutocomplet.TIPE_DISPLAY.equals("NAME")
                            && txtAutoCompDNI.getText().equals(AutoCompleteTextField.dataGetReturnet.getNombreDysplay())) {
                        txtNombreCliente.setText(AutoCompleteTextField.dataGetReturnet.getIdx());
                    } else if (ModeloDataAutocomplet.TIPE_DISPLAY.equals("OTHER")
                            && txtAutoCompDNI.getText().equals(AutoCompleteTextField.dataGetReturnet.getOtherData())) {
                        System.out.println("Valor:" + txtAutoCompDNI.getText());
                        System.out.println("Valor:" + AutoCompleteTextField.dataGetReturnet.getIdx() + "\tContenido:"
                                + AutoCompleteTextField.dataGetReturnet.getNombreDysplay());
                        txtNombreCliente.setText(AutoCompleteTextField.dataGetReturnet.getIdx());
                    } else {
                        System.out.println("Valor:" + txtAutoCompDNI.getText());
                        txtNombreCliente.setText("");
                    }*/
                }
            }
        });
        buscarProducto();
        txtProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
                        && AutoCompleteTextField.dataGetReturnet != null) {
                    txtCodigo.setText(AutoCompleteTextField.dataGetReturnet.getNombreDysplay());
                    String[] dataX = AutoCompleteTextField.dataGetReturnet.getOtherData().split(":");
                    txtPU.setText(dataX[0]);
                    txtStock.setText(dataX[1]);
                }
            }
        });
        txtCantidad.addKeyListener(new KeyAdapter() {
            double pu = 0, cant = 0;

            @Override
            public void keyReleased(KeyEvent e) {

                pu = Double.parseDouble(String.valueOf(
                        txtPU.getText().equals("") ? "0" : txtPU.getText()));
                cant = Double.parseDouble(String.valueOf(
                        txtCantidad.getText().equals("") ? "0" : txtCantidad.getText()));
                txtPrecioTotal.setText(String.valueOf(pu * cant));
            }

        });
    }
    DefaultTableModel modelo;

    public List<CarritoTO> listarCarrito(String dni) {
        daoCA = new CarritoDAO();
        List<CarritoTO> listarClientes = daoCA.lista(dni);
        jTable1.setAutoCreateRowSorter(true);
        modelo = (DefaultTableModel) jTable1.getModel();
        ButtonsPanel.metaDataButtons = new String[][]{{"", "del-icon.png"}};
        jTable1.setRowHeight(40);
        TableColumn column = jTable1.getColumnModel().getColumn(8);
        column.setCellRenderer(new ButtonsRenderer());
        ButtonsEditor be = new ButtonsEditor(jTable1);
        column.setCellEditor(be);
        modelo.setNumRows(0);
        Object[] ob = new Object[9];
        double impoTotal = 0, igv = 0;
        for (int i = 0; i < listarClientes.size(); i++) {
            int x = -1;
            ob[++x] = listarClientes.get(i).getIdCarrito();
            ob[++x] = listarClientes.get(i).getDniruc();
            ob[++x] = listarClientes.get(i).getIdProducto();
            ob[++x] = listarClientes.get(i).getNombreProducto();
            ob[++x] = listarClientes.get(i).getCantidad();
            ob[++x] = listarClientes.get(i).getPunitario();
            ob[++x] = listarClientes.get(i).getPtotal();
            ob[++x] = listarClientes.get(i).getEstado();
            ob[++x] = "";
            impoTotal += Double.parseDouble(String.valueOf(listarClientes.get(i).getPtotal()));

            cantidad = Double.parseDouble(String.valueOf(listarClientes.get(i).getCantidad()));

            modelo.addRow(ob);
        }
        JButton btnDel = be.getCellEditorValue().buttons.get(0);
        btnDel.addActionListener((ActionEvent e) -> {
            System.out.println("VERRRRRR:");
            int row
                    = jTable1.convertRowIndexToModel(jTable1.getEditingRow());
            Object o = jTable1.getModel().getValueAt(row, 0);
            daoCA = new CarritoDAO();
            try {
                daoCA.delete(Integer.parseInt(String.valueOf(o)));
                listarCarrito(dni);
            } catch (Exception ex) {
                System.err.println("Error:" + ex.getMessage());
            }
            System.out.println("AAAA:" + String.valueOf(o));
            JOptionPane.showMessageDialog(this, "Has Eliminado ID: " + o);
        });
        jTable1.setModel(modelo);

        //CONDICIONAL
        if (cantidad > 3) {
            double totalDscTop = impoTotal * 0.20;
            double dt = impoTotal - totalDscTop;
            txtImporteTotal.setText(String.valueOf(dt));
            double pv = impoTotal / 1.18;
            txtPrecioB.setText(String.valueOf(Math.round(pv * 100.0) / 100.0));
            txtIgv.setText(String.valueOf(Math.round((pv * 0.18) * 100.0) / 100.0));
            txtDescuentoTop.setText(String.valueOf(Math.round(pv * 0.10) * 100.0 / 100.0));
        } else {
            txtImporteTotal.setText(String.valueOf(impoTotal));
            double pv = impoTotal / 1.18;
            txtPrecioB.setText(String.valueOf(Math.round(pv * 100.0) / 100.0));
            txtIgv.setText(String.valueOf(Math.round((pv * 0.18) * 100.0) / 100.0));
            return listarClientes;
        }
        return listarClientes;

    }

    public void buscarCliente() {
        daoC = new ClienteDAO();
        items = daoC.listAutoComplet("");
        AutoCompleteTextField.setupAutoComplete(txtAutoCompDNI, items, "ID");
    }

    public void buscarProducto() {
        daoP = new ProductoDAO();
        itemsP = daoP.listAutoComplet("");
        AutoCompleteTextField.setupAutoComplete(txtProducto, itemsP, "ID");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPU = new javax.swing.JTextField();
        txtPrecioTotal = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtPrecioB = new javax.swing.JTextField();
        txtDescuentoTop = new javax.swing.JTextField();
        txtIgv = new javax.swing.JTextField();
        txtImporteTotal = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtAutoCompDNI = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setDoubleBuffered(false);

        jLabel4.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Codigo");

        jLabel5.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Modelo de Auto");

        jLabel6.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("P.Total");

        jLabel7.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Stock");

        jLabel8.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("dias");

        jLabel9.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("P.Unitario");

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 204, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus.png"))); // NOI18N
        jButton2.setText("Añadir");
        jButton2.setToolTipText("");
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtPU, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 950, 80));

        jPanel4.setBackground(new java.awt.Color(0, 102, 153));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("IGV");

        jLabel11.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Precio Alquiler");

        jLabel12.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("P.Total S/");

        jLabel13.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Descuento");

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(153, 204, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/document-edit-icon.png"))); // NOI18N
        jButton3.setText("Alquilar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel10))
                        .addComponent(txtDescuentoTop, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPrecioB, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtPrecioB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescuentoTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 220, 230, 290));

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setForeground(new java.awt.Color(255, 204, 102));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nº licencia", "Id modelo", "Modelo", "Cantidad", "P.Unit S/", "P.Total S/", "Estado", "Opc"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 700, 270));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 18)); // NOI18N
        jLabel2.setText("Nº Licencia");

        jLabel3.setFont(new java.awt.Font("Alegreya Sans ExtraBold", 1, 18)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/electric-car.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(txtAutoCompDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 413, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAutoCompDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40))
        );

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 930, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/789.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 520));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        registarVenta();

    }//GEN-LAST:event_jButton3ActionPerformed

    public void limpiarCarrito() {
        daoCA = new CarritoDAO();
        daoCA.deleteCarAll(txtAutoCompDNI.getText());
        listarCarrito(txtAutoCompDNI.getText());
    }

   private void runReport1(int idventa) {
        try {
            connection = Conn.connectSQLite();
            HashMap param = new HashMap();
            String imgen = getFile("Cronos.jpg").getAbsolutePath(); 
            param.put("idventa", idventa);
            param.put("imagen", imgen);                       
            JasperDesign jdesign = JRXmlLoader.load(getFile("Comprobante.jrxml"));
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, param,
                   connection);
            JasperViewer.viewReport(jprint, false);
        } catch (JRException ex) {
            System.out.println("Error:\n" + ex.getLocalizedMessage());
        }
    }

    public File getFile(String filex) {
        File newFolder = new File("jasper");
        String ruta = newFolder.getAbsolutePath();
//CAMINO = Paths.get(ruta+"/"+"reporte1.jrxml");
        Path CAMINO = Paths.get(ruta + "/" + filex);
        System.out.println("Llegasss Ruta 2:" + CAMINO.toFile().getAbsolutePath());
        return CAMINO.toFile();
    }

    public void registarVenta() {
        daoV = new VentaDAO();

        List<CarritoTO> lista = listarCarrito(txtAutoCompDNI.getText());
        VentaTO tov = new VentaTO();
        tov.setDniruc(txtAutoCompDNI.getText());
        tov.setIgv(Double.parseDouble(txtIgv.getText()));
        tov.setDescuento_top(Double.parseDouble(txtDescuentoTop.getText()));
        tov.setPrecioBase(Double.parseDouble(txtPrecioB.getText()));
        tov.setPrecioTotal(Double.parseDouble(txtImporteTotal.getText()));
        int idx = daoV.createVenta(tov);
        if (idx != 0) {
            for (CarritoTO carritoTO : lista) {
                daoV = new VentaDAO();
                VentaDetalleTO vd = new VentaDetalleTO();
                vd.setIdVenta(idx);
                vd.setIdProducto(carritoTO.getIdProducto());
                vd.setCantidad(carritoTO.getCantidad());
                vd.setPu(carritoTO.getPunitario());
                vd.setSubTotal(carritoTO.getPtotal());
                vd.setDescuento(0);
                daoV.createVentaDetalle(vd);

                ProductoDaoI pst = new ProductoDAO();
                pst.actualizarStock(vd.getCantidad(), vd.getIdProducto());
            }
        }
        limpiarCarrito();
        runReport1(idx);

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        daoCA = new CarritoDAO();
        CarritoTO to = new CarritoTO();
        to.setDniruc(txtAutoCompDNI.getText());
        to.setIdProducto(Integer.parseInt(txtCodigo.getText()));
        to.setNombreProducto(txtProducto.getText());
        to.setCantidad(Double.parseDouble(txtCantidad.getText()));
        to.setPunitario(Double.parseDouble(txtPU.getText()));
        to.setPtotal(Double.parseDouble(txtPrecioTotal.getText()));
        to.setEstado(1);
        daoCA.crear(to);
        listarCarrito(txtAutoCompDNI.getText());
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAutoCompDNI;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescuentoTop;
    private javax.swing.JTextField txtIgv;
    private javax.swing.JTextField txtImporteTotal;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtPU;
    private javax.swing.JTextField txtPrecioB;
    private javax.swing.JTextField txtPrecioTotal;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
