/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class GUIMain extends JFrame {

    JMenuBar menuBar;
    JMenu menu1;
    JMenuItem jmI1;
    JMenuItem jmI2; 
    JTabbedPane jtpane;
    JPanel jp;
    JScrollPane scrollPane;

    public GUIMain() {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension(screenSize.width-860, (screenSize.height)-460 ));
        setLocationRelativeTo(null);
        menuBar = new JMenuBar();
        menu1 = new JMenu("Archivo");
        jmI1 = new JMenuItem("Abrir");
        jmI2 = new JMenuItem("🕵️‍♀️ Administración");

        menu1.add(jmI1);
        menu1.add(jmI2);
        menuBar.add(menu1);
        menu1 = new JMenu("Ver");
        menuBar.add(menu1);
        this.add(menuBar);
        jtpane = new JTabbedPane();
        MenuItemListener menuItemListener = new MenuItemListener();
        jmI1.addActionListener(menuItemListener);
        jmI2.addActionListener(menuItemListener);

        this.getContentPane().add(BorderLayout.NORTH, menuBar);
    }

    class MenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Container contai = GUIMain.this.getContentPane();
            if (e.getSource() == jmI2) {
                jtpane.removeAll();
                MainCliente mc= new MainCliente();
                scrollPane = new JScrollPane(mc);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                jtpane.add("👦 Clientes",scrollPane);  
                
                MainVentas mv= new MainVentas();
                scrollPane = new JScrollPane(mv);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                jtpane.add("🚖 Alquiler",scrollPane);    
                ReporteVenta rv= new ReporteVenta();
                
                jtpane.add("⬇ ️Reportes",rv);   
               
                contai.add(BorderLayout.CENTER, jtpane);
                contai.invalidate();
                contai.validate();

            }

        }
    }

}
