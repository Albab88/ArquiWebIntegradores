package dao;

import entities.Cliente;

import java.util.List;

public interface ClienteDAO {
    List<Cliente> getClientesOrdenadosPorFacturacion();
}
