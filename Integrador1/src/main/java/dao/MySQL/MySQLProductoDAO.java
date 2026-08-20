package dao.MySQL;

import dao.ProductoDAO;
import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLProductoDAO implements ProductoDAO {

    @Override
    public void crear(Producto producto) {
        Connection conn = MySQLConnectionManager.getConnection();
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());
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
