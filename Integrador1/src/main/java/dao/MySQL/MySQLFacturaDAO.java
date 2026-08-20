package dao.MySQL;

import dao.FacturaDAO;
import entities.Factura;
import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class MySQLFacturaDAO implements FacturaDAO {

  @Override
  public void crear(Factura factura) {
    Connection conn = MySQLConnectionManager.getConnection();

    String sqlFactura = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
    String sqlDetalle = "INSERT INTO detalle_factura (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

    try (PreparedStatement psFactura = conn.prepareStatement(sqlFactura); PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {

      psFactura.setInt(1, factura.getIdFactura());
      psFactura.setInt(2, factura.getCliente().getIdCliente());
      psFactura.executeUpdate();

      for (Map.Entry<Producto, Integer> entry : factura.getProductos().entrySet()) {
        psDetalle.setInt(1, factura.getIdFactura());
        psDetalle.setInt(2, entry.getKey().getIdProducto());
        psDetalle.setInt(3, entry.getValue());
        psDetalle.addBatch();
      }
      psDetalle.executeBatch();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
