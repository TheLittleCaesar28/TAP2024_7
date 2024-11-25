package com.example.tap2024_7.MODELS;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumDAO {

    private final IntegerProperty idAlbum = new SimpleIntegerProperty();
    private final StringProperty nombreAlbum = new SimpleStringProperty();
    private final IntegerProperty idArtista = new SimpleIntegerProperty();
    private final StringProperty nombreArtista = new SimpleStringProperty();
    private String rutaImagen; // Variable para almacenar la ruta de la imagen

    // Getters y Setters
    public int getIdAlbum() {
        return idAlbum.get();
    }

    public IntegerProperty idAlbumProperty() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum.set(idAlbum);
    }

    public String getNombreAlbum() {
        return nombreAlbum.get();
    }

    public StringProperty nombreAlbumProperty() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum.set(nombreAlbum);
    }

    public int getIdArtista() {
        return idArtista.get();
    }

    public IntegerProperty idArtistaProperty() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista.set(idArtista);
    }

    public String getNombreArtista() {
        return nombreArtista.get();
    }

    public StringProperty nombreArtistaProperty() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista.set(nombreArtista);
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    // Método para insertar un álbum con solo el nombre del archivo de la imagen
    public void insertarAlbum(String nombreAlbum, String rutaCompletaImagen, int idArtista) {
        String nombreImagen = Paths.get(rutaCompletaImagen).getFileName().toString();

        String query = "INSERT INTO albumes (nombreAlbum, imagen, idArtista) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreAlbum);
            stmt.setString(2, nombreImagen); // Guardar solo el nombre del archivo
            stmt.setInt(3, idArtista);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Álbum insertado correctamente con imagen: " + nombreImagen);
            }
        } catch (Exception e) {
            System.err.println("Error al insertar el álbum: " + e.getMessage());
        }
    }

    // Método SELECTALL para obtener todos los álbumes
    public ObservableList<AlbumDAO> SELECTALL() {
        ObservableList<AlbumDAO> lista = FXCollections.observableArrayList();
        String query = """
            SELECT a.idAlbum, a.nombreAlbum, a.idArtista, ar.nombreArtista, a.imagen
            FROM Albumes a
            JOIN Artistas ar ON a.idArtista = ar.idArtista
        """;

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setIdAlbum(rs.getInt("idAlbum"));
                album.setNombreAlbum(rs.getString("nombreAlbum"));
                album.setIdArtista(rs.getInt("idArtista"));
                album.setNombreArtista(rs.getString("nombreArtista"));
                album.setRutaImagen(rs.getString("imagen")); // Asignar la ruta de la imagen
                lista.add(album);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los álbumes: " + e.getMessage());
        }
        return lista;
    }

    // Método INSERT con uso de PreparedStatement para evitar inyecciones SQL
    public void INSERT() {
        String nombreImagen = Paths.get(getRutaImagen()).getFileName().toString(); // Extraer solo el nombre del archivo

        String query = "INSERT INTO Albumes (nombreAlbum, idArtista, imagen) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreAlbum());
            stmt.setInt(2, getIdArtista());
            stmt.setString(3, nombreImagen); // Guardar solo el nombre del archivo

            stmt.executeUpdate();
            System.out.println("Álbum insertado correctamente con imagen: " + nombreImagen);
        } catch (Exception e) {
            System.err.println("Error en INSERT: " + e.getMessage());
        }
    }


    // Método UPDATE
    public void UPDATE() {
        String query = "UPDATE Albumes SET nombreAlbum = ?, idArtista = ?, imagen = ? WHERE idAlbum = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreAlbum());
            stmt.setInt(2, getIdArtista());
            stmt.setString(3, getRutaImagen());
            stmt.setInt(4, getIdAlbum());

            stmt.executeUpdate();
            System.out.println("Álbum actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("Error en UPDATE: " + e.getMessage());
        }
    }

    // Método DELETE
    public void DELETE() {
        String query = "DELETE FROM Albumes WHERE idAlbum = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdAlbum());
            stmt.executeUpdate();
            System.out.println("Álbum eliminado correctamente.");
        } catch (Exception e) {
            System.err.println("Error en DELETE: " + e.getMessage());
        }
    }
}
