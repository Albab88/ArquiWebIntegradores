package dao.MySQL;

import dao.ProductoDAO;
import entities.Producto;
import factories.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class MySQLProductoDAO implements ProductoDAO {

    @Override
    public void crear(Producto producto) {
        if(Objects.isNull(producto)) return;

        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Collection<Producto> productos) {
        if(Objects.isNull(productos) || productos.isEmpty()) return;

        String placeholders = productos.stream()
            .map(p -> "(?, ?, ?)")
            .collect(Collectors.joining(", "));
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES " + placeholders;

        try (Connection conn = MySQLConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            for (Producto p : productos) {
                ps.setInt(i++, p.getIdProducto());
                ps.setString(i++, p.getNombre());
                ps.setFloat(i++, p.getValor());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Producto getProductoMasVendido() {
        return null; // TODO
    }

}
