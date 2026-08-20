package dao.MySQL;

import dao.ClienteDAO;
import entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLClienteDAO implements ClienteDAO {

    @Override
    public void crear(Cliente cliente) {
        Connection conn = MySQLConnectionManager.getConnection();
        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySQLConnectionManager.closeConnection();
        }
    }

    @Override
    public List<Cliente> getClientesOrdenadosPorFacturacion() {
        return List.of(); //TODO
    }

}
