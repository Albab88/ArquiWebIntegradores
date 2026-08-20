package dao.MySQL;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionManager {

  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String URI = "jdbc:mysql://localhost:3306/demodao";

  private static Connection conn;

  public static synchronized Connection getConnection() {
    try {
      if (conn == null || conn.isClosed()) {
        Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        conn = DriverManager.getConnection(URI, "root", "");
        conn.setAutoCommit(false);
      }
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException |
        ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return conn;
  }

  public static synchronized void closeConnection() {
    try {
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
