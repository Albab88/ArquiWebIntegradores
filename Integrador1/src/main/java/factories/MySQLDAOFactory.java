package factories;

import dao.*;
import dao.MySQL.MySQLClienteDAO;
import dao.MySQL.MySQLFacturaDAO;
import dao.MySQL.MySQLProductoDAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class MySQLDAOFactory extends AbstractFactory{

    private static MySQLDAOFactory instance = null;

    public static synchronized MySQLDAOFactory getInstance() {
      if (instance == null) {
        instance = new MySQLDAOFactory();
      }
      return instance;
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new MySQLClienteDAO();
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return new MySQLFacturaDAO();
    }

    @Override
    public ProductoDAO getProductoDAO() {
        return new MySQLProductoDAO();
    }
}
