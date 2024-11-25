package com.example.tap2024_7.MODELS;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CancionDAO {

    private final IntegerProperty idCancion = new SimpleIntegerProperty();
    private final StringProperty nombreCancion = new SimpleStringProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final IntegerProperty idAlbum = new SimpleIntegerProperty();
    private final StringProperty albumNombre = new SimpleStringProperty();
    private String albumImagenRuta;

    public int getIdCancion() {
        return idCancion.get();
    }

    public IntegerProperty idCancionProperty() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion.set(idCancion);
    }

    public String getNombreCancion() {
        return nombreCancion.get();
    }

    public StringProperty nombreCancionProperty() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion.set(nombreCancion);
    }

    public double getPrecio() {
        return precio.get();
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public int getIdAlbum() {
        return idAlbum.get();
    }

    public IntegerProperty idAlbumProperty() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum.set(idAlbum);
    }

    public String getAlbumNombre() {
        return albumNombre.get();
    }

    public StringProperty albumNombreProperty() {
        return albumNombre;
    }

    public void setAlbumNombre(String albumNombre) {
        this.albumNombre.set(albumNombre);
    }

    public String getAlbumImagenRuta() {
        return albumImagenRuta;
    }

    public void setAlbumImagenRuta(String albumImagenRuta) {
        this.albumImagenRuta = albumImagenRuta;
    }

    // Métodos CRUD
    public void INSERT() {
        // Verificar si el idAlbum existe en la tabla 'albumes'
        String verificarQuery = "SELECT COUNT(*) FROM albumes WHERE idAlbum = ?";
        String insertarQuery = "INSERT INTO canciones (nombreCancion, precio, idAlbum) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarQuery);
             PreparedStatement insertarStmt = conn.prepareStatement(insertarQuery)) {

            // Validar si el idAlbum existe
            verificarStmt.setInt(1, getIdAlbum());
            ResultSet rs = verificarStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.err.println("Error: idAlbum no existe en la tabla 'albumes'.");
                return; // Salir si el idAlbum no es válido
            }

            // Si es válido, proceder a insertar la canción
            insertarStmt.setString(1, getNombreCancion());
            insertarStmt.setDouble(2, getPrecio());
            insertarStmt.setInt(3, getIdAlbum());
            insertarStmt.executeUpdate();

            System.out.println("Canción insertada correctamente.");
        } catch (Exception e) {
            System.err.println("Error en INSERT: " + e.getMessage());
        }
    }



    public static ObservableList<CancionDAO> SELECTALL() {
        ObservableList<CancionDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT c.idCancion, c.nombreCancion, c.precio, a.nombreAlbum, a.imagen " +
                "FROM Canciones c " +
                "JOIN Albumes a ON c.idAlbum = a.idAlbum";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CancionDAO cancion = new CancionDAO();
                cancion.setIdCancion(rs.getInt("idCancion"));
                cancion.setNombreCancion(rs.getString("nombreCancion"));
                cancion.setPrecio(rs.getDouble("precio"));
                cancion.setAlbumNombre(rs.getString("nombreAlbum"));
                cancion.setAlbumImagenRuta(rs.getString("imagen"));
                lista.add(cancion);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar las canciones: " + e.getMessage());
        }

        return lista;
    }

    public void UPDATE() {
        String query = "UPDATE Canciones SET nombreCancion = ?, precio = ?, idAlbum = ? WHERE idCancion = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreCancion());
            stmt.setDouble(2, getPrecio());
            stmt.setInt(3, getIdAlbum());
            stmt.setInt(4, getIdCancion());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Canción actualizada correctamente.");
            } else {
                System.err.println("Error al actualizar la canción.");
            }
        } catch (Exception e) {
            System.err.println("Error en UPDATE: " + e.getMessage());
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Canciones WHERE idCancion = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdCancion());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Canción eliminada correctamente.");
            } else {
                System.err.println("Error al eliminar la canción.");
            }
        } catch (Exception e) {
            System.err.println("Error en DELETE: " + e.getMessage());
        }
    }
}
