package com.example.tap2024_7.MODELS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/Spotify";
    private static final String USER = "admin";
    private static final String PASSWORD = "123";
    private static Connection connection = null;

    public static void CrearConexion() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida exitosamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                CrearConexion();
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada exitosamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
