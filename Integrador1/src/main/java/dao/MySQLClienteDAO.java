package dao;

import entities.Cliente;

import java.util.List;

public class MySQLClienteDAO implements ClienteDAO {

    @Override
    public List<Cliente> getClientesOrdenadosPorFacturacion() {
        return List.of(); //TODO
    }
}
