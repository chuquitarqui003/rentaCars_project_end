/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import pe.edu.upeu.app.dao.conx.Conn;
import pe.edu.upeu.app.modelo.ClienteTO;
import pe.edu.upeu.app.util.ErrorLogger;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;


/**
 *
 * @author ACER ASPIRE
 */
public class ClienteDAO implements ClienteDaoI {

    Statement stmt = null;
    Vector columnNames;
    Vector visitdata;
    Connection connection;
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ClienteDAO.class.getName());
    ResultSet rs = null;

    public ClienteDAO() {
        columnNames = new Vector();
        visitdata = new Vector();
        connection = Conn.connectSQLite();
    }

    @Override
    public int create(ClienteTO d) {
        int rsId = 0;
        String sql = "INSERT INTO cliente(dni, nombres, n_licencia, fecha_entrega, cliente_top, fecha_contrato) "
                + "VALUES(?,?,?,?,?,?)";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getDni());
            ps.setString(++i, d.getNombres());
            ps.setString(++i, d.getN_licencia());
            ps.setString(++i, d.getFecha_entrega());
            ps.setString(++i, d.getCliente_top());
            ps.setString(++i, d.getFecha_cotrato());

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {
//System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    @Override
    public int update(ClienteTO d) {

        System.out.println("actualizar d.getDniruc: " + d.getDni());
        System.out.println("actualizar d.getNombres: " + d.getNombres());
  
        int comit = 0;
        String sql = "UPDATE cliente SET "
               
                + "nombres=?, "
                + "n_licencia=?, "
                + "fecha_entrega=?, "
                + "cliente_top=?, "
                + "fecha_contrato=?, ";          
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);

            ps.setString(++i, d.getNombres());
            ps.setString(++i, d.getN_licencia());
            ps.setString(++i, d.getFecha_entrega());
            ps.setString(++i, d.getCliente_top());
            ps.setString(++i, d.getFecha_cotrato());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM cliente WHERE dni = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    @Override
    public List<ClienteTO> listCmb(String filter) {

        List<ClienteTO> ls = new ArrayList();
        ls.add(new ClienteTO());
        ls.addAll(listarClientes());
        return ls;
    }

    @Override
    public List<ClienteTO> listarClientes() {
        List<ClienteTO> listarclientes = new ArrayList();
        String sql = "SELECT * FROM cliente";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ClienteTO cli = new ClienteTO();
                cli.setDni(rs.getString("dni"));
                cli.setNombres(rs.getString("nombres"));
                cli.setN_licencia(rs.getString("n_licencia"));
                cli.setFecha_entrega(rs.getString("fecha_entrega"));
                cli.setCliente_top(rs.getString("cliente_top"));
                cli.setFecha_cotrato(rs.getString("fecha_contrato"));

                listarclientes.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarclientes;
    }

    @Override
    public ClienteTO buscarClientes(String dni) {
        ClienteTO cliente = new ClienteTO();
        String sql = "SELECT * FROM cliente WHERE dni = ?";
        try {
            //connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setDni(rs.getString("dni"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setN_licencia(rs.getString("n_licencia"));
                cliente.setFecha_entrega(rs.getString("fecha_entrega"));
                cliente.setCliente_top(rs.getString("cliente_top"));
                cliente.setFecha_cotrato(rs.getString("fecha_contrato"));

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cliente;
    }

    @Override
    public void reportarCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoComplet(String filter) {
        List<ModeloDataAutocomplet> listarclientes = new ArrayList();
        String sql = "SELECT * FROM cliente WHERE nombres like ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, filter + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                //ModeloDataAutocomplet.TIPE_DISPLAY = "ID";
                data.setIdx(rs.getString("dni"));
                data.setNombreDysplay(rs.getString("nombres"));
                listarclientes.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarclientes;
    }

}
