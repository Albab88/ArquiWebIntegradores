package dao.MySQL;

import dao.ClienteDAO;
import entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MySQLClienteDAO implements ClienteDAO {

    @Override
    public void crear(Cliente cliente) {
        if(Objects.isNull(cliente)) return;

        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Collection<Cliente> clientes) {
        if (Objects.isNull(clientes) || clientes.isEmpty()) return;

        String placeholders = clientes.stream()
            .map(c -> "(?, ?, ?)")
            .collect(Collectors.joining(", "));

        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES " + placeholders;

        try (Connection conn = MySQLConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            for (Cliente c : clientes) {
                ps.setInt(i++, c.getIdCliente());
                ps.setString(i++, c.getNombre());
                ps.setString(i++, c.getEmail());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> getClientesOrdenadosPorFacturacion() {
        return List.of(); //TODO
    }

}
