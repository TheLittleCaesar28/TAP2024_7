package com.example.tap2024_7.MODELS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/Spotify"; // Cambia 'Spotify' al nombre correcto de tu base de datos
    private static final String USER = "admin"; // Tu usuario de MySQL
    private static final String PASSWORD = "123"; // Tu contraseña de MySQL
    private static Connection connection = null;

    // Crear la conexión inicial
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

    // Obtener la conexión
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                CrearConexion(); // Reintenta conectar si la conexión estaba cerrada o nula
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Cerrar la conexión si es necesario
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
