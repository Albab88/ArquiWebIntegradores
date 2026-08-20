package utils;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.ProductoDAO;
import entities.Cliente;
import entities.Factura;
import entities.Producto;
import factories.AbstractFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CSVLoader {

  private static final CSVFormat FORMATO = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setTrim(true).build();

  private final AbstractFactory factory;

  public CSVLoader(AbstractFactory factory) {
    this.factory = factory;
  }

  public void cargarTodo(String csvClientes, String csvProductos, String csvFacturas, String csvDetalle) {
    try {
      Map<Integer, Cliente> clientes = leerClientes(csvClientes);
      Map<Integer, Producto> productos = leerProductos(csvProductos);
      Map<Integer, Factura> facturas = leerFacturas(csvFacturas, clientes);
      asociarDetalle(csvDetalle, facturas, productos);

      ClienteDAO clienteDAO = factory.getClienteDAO();
      ProductoDAO productoDAO = factory.getProductoDAO();
      FacturaDAO facturaDAO = factory.getFacturaDAO();


      clientes.values().forEach(clienteDAO::crear);
      productos.values().forEach(productoDAO::crear);
      facturas.values().forEach(facturaDAO::crear);

    } catch (IOException | IllegalStateException e) {
      e.printStackTrace();
    }
  }


  private Map<Integer, Cliente> leerClientes(String ruta) throws IOException {
    Map<Integer, Cliente> clientes = new LinkedHashMap<>();

    try (Reader reader = Files.newBufferedReader(Path.of(ruta), StandardCharsets.UTF_8);
        CSVParser parser = FORMATO.parse(reader)) {

      for (CSVRecord r : parser) {
        int id = Integer.parseInt(r.get("idCliente"));
        clientes.put(
            id,
            Cliente.builder()
                .idCliente(id)
                .nombre(r.get("nombre"))
                .email(r.get("email"))
                .build());
      }

    }
    return clientes;
  }

  private Map<Integer, Producto> leerProductos(String ruta) throws IOException {
    Map<Integer, Producto> productos = new LinkedHashMap<>();

    try (Reader reader = Files.newBufferedReader(Path.of(ruta), StandardCharsets.UTF_8);
        CSVParser parser = FORMATO.parse(reader)) {

      for (CSVRecord r : parser) {
        int id = Integer.parseInt(r.get("idProducto"));
        productos.put(
            id,
            Producto.builder()
                .idProducto(id)
                .nombre(r.get("nombre"))
                .valor(Float.parseFloat(r.get("valor")))
                .build());
      }

    }
    return productos;
  }

  private Map<Integer, Factura> leerFacturas(String ruta, Map<Integer, Cliente> clientes) throws IOException {
    Map<Integer, Factura> facturas = new LinkedHashMap<>();

    try (Reader reader = Files.newBufferedReader(Path.of(ruta), StandardCharsets.UTF_8);
        CSVParser parser = FORMATO.parse(reader)) {

      for (CSVRecord r : parser) {
        int idFactura = Integer.parseInt(r.get("idFactura"));
        int idCliente = Integer.parseInt(r.get("idCliente"));

        Cliente cliente = clientes.get(idCliente);

        if (Objects.isNull(cliente)) {
          throw new IllegalStateException("Factura " + idFactura + " referencia idCliente=" + idCliente + " inexistente");
        }

        facturas.put(
            idFactura,
            Factura.builder()
                .idFactura(idFactura)
                .cliente(cliente)
                .build());
      }

    }
    return facturas;
  }

  private void asociarDetalle(String ruta, Map<Integer, Factura> facturas, Map<Integer, Producto> productos) throws IOException {
    try (Reader in = Files.newBufferedReader(Path.of(ruta), StandardCharsets.UTF_8);
        CSVParser parser = FORMATO.parse(in)) {

      for (CSVRecord r : parser) {

        int idFactura = Integer.parseInt(r.get("idFactura"));
        int idProducto = Integer.parseInt(r.get("idProducto"));
        int cantidad = Integer.parseInt(r.get("cantidad"));

        Factura factura = facturas.get(idFactura);
        if (Objects.isNull(factura)) {
          throw new IllegalStateException("Detalle referencia idFactura=" + idFactura + " inexistente");
        }

        Producto producto = productos.get(idProducto);
        if (Objects.isNull(producto)) {
          throw new IllegalStateException("Detalle referencia idProducto=" + idProducto + " inexistente");
        }

        factura.getProductos().put(producto, cantidad);
      }

    }
  }

}
