package org.cy.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBasedeDatos {

    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "Wabi16Cy";
    private static Connection connection;


    public static Connection getInstance() throws SQLException {
        if (connection == null){
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
}
