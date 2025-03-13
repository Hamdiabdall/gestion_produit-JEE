package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {

private static Connection connection;
private SingletonConnection() {
try {
Class.forName("com.mysql.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionproduits", "root", "root123*");
}catch (ClassNotFoundException  e) {
e.printStackTrace();
}catch (SQLException e) {
e.printStackTrace();
}
}

public static Connection getInstance()
  {
      if(connection==null)
          new SingletonConnection();
      return connection;
  }
}

