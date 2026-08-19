package factories;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.ProductoDAO;

public abstract class AbstractFactory {
    public abstract ClienteDAO getClienteDAO();
    public abstract FacturaDAO getFacturaDAO();
    public abstract ProductoDAO getProductoDAO();
    public static AbstractFactory getDAOFactory(DriverType whichFactory) {
         return switch (whichFactory) {
            case MYSQL_JDBC -> MySQLDAOFactory.getInstance();
        };
    }
    public enum DriverType {
        MYSQL_JDBC
    }
}
