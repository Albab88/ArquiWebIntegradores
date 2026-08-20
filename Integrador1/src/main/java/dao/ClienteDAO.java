package dao;

import entities.Cliente;

import java.util.List;

public interface ClienteDAO extends EntityDAO<Cliente>{
    List<Cliente> getClientesOrdenadosPorFacturacion();
}
