package dao.MySQL;

import dao.FacturaDAO;
import entities.Factura;
import entities.Producto;
import factories.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MySQLFacturaDAO implements FacturaDAO {

  @Override
  public void crear(Factura factura) {
    if (Objects.isNull(factura)) return;

    String sqlFactura = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
    String sqlDetalle = "INSERT INTO detalle_factura (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

    try (Connection conn = MySQLConnectionManager.getConnection();
        PreparedStatement psFactura = conn.prepareStatement(sqlFactura);
        PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {

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

  @Override
  public void crear(Collection<Factura> facturas) {
    if (Objects.isNull(facturas) || facturas.isEmpty()) return;

    try (Connection conn = MySQLConnectionManager.getConnection()) {
      insertarFacturas(conn, facturas);
      insertarDetalles(conn, facturas);
    } catch (SQLException e){
      e.printStackTrace();
    }
  }

  private void insertarFacturas(Connection conn, Collection<Factura> facturas) throws SQLException {
    String placeholders = facturas.stream()
        .map(f -> "(?, ?)")
        .collect(Collectors.joining(", "));
    String sql = "INSERT INTO factura (idFactura, idCliente) VALUES " + placeholders;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      int i = 1;
      for (Factura f : facturas) {
        ps.setInt(i++, f.getIdFactura());
        ps.setInt(i++, f.getCliente().getIdCliente());
      }
      ps.executeUpdate();
    }
  }

  private void insertarDetalles(Connection conn, Collection<Factura> facturas) throws SQLException {
    int totalDetalles = facturas.stream().mapToInt(f -> f.getProductos().size()).sum();
    if (totalDetalles == 0) return;

    String placeholders = String.join(", ", Collections.nCopies(totalDetalles, "(?, ?, ?)"));
    String sql = "INSERT INTO detalle_factura (idFactura, idProducto, cantidad) VALUES " + placeholders;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      int i = 1;
      for (Factura f : facturas) {
        for (Map.Entry<Producto, Integer> entry : f.getProductos().entrySet()) {
          ps.setInt(i++, f.getIdFactura());
          ps.setInt(i++, entry.getKey().getIdProducto());
          ps.setInt(i++, entry.getValue());
        }
      }
      ps.executeUpdate();
    }
  }

}
