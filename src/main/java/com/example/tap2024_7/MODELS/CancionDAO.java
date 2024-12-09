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
    private final IntegerProperty idGenero = new SimpleIntegerProperty();
    private final StringProperty generoNombre = new SimpleStringProperty();
    private final IntegerProperty idIdioma = new SimpleIntegerProperty();
    private final StringProperty idiomaNombre = new SimpleStringProperty();
    private String albumImagenRuta;

    // Getters y Setters para idGenero e idIdioma
    public int getIdGenero() {
        return idGenero.get();
    }

    public IntegerProperty idGeneroProperty() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero.set(idGenero);
    }

    public String getGeneroNombre() {
        return generoNombre.get();
    }

    public StringProperty generoNombreProperty() {
        return generoNombre;
    }

    public void setGeneroNombre(String generoNombre) {
        this.generoNombre.set(generoNombre);
    }

    public int getIdIdioma() {
        return idIdioma.get();
    }

    public IntegerProperty idIdiomaProperty() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma.set(idIdioma);
    }

    public String getIdiomaNombre() {
        return idiomaNombre.get();
    }

    public StringProperty idiomaNombreProperty() {
        return idiomaNombre;
    }

    public void setIdiomaNombre(String idiomaNombre) {
        this.idiomaNombre.set(idiomaNombre);
    }

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

    // Método INSERT
    public void INSERT() {
        String insertarQuery = "INSERT INTO canciones (nombreCancion, precio, idAlbum, idGenero, idIdioma) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement insertarStmt = conn.prepareStatement(insertarQuery)) {

            insertarStmt.setString(1, getNombreCancion());
            insertarStmt.setDouble(2, getPrecio());
            insertarStmt.setInt(3, getIdAlbum());
            insertarStmt.setInt(4, getIdGenero());
            insertarStmt.setInt(5, getIdIdioma());
            insertarStmt.executeUpdate();

            System.out.println("Canción insertada correctamente.");
        } catch (Exception e) {
            System.err.println("Error en INSERT: " + e.getMessage());
        }
    }
    public static ObservableList<CancionDAO> SELECTALL() {
        return SELECTALL(null, null); // Llama a la versión con parámetros, pero sin filtros.
    }


    // Método SELECTALL para incluir género e idioma
    public static ObservableList<CancionDAO> SELECTALL(String filtroIdioma, String filtroGenero) {
        ObservableList<CancionDAO> lista = FXCollections.observableArrayList();
        String query = """
            SELECT c.idCancion, c.nombreCancion, c.precio, a.nombreAlbum, a.imagen, 
                   g.nombreGenero, i.nombreIdioma 
            FROM Canciones c
            JOIN Albumes a ON c.idAlbum = a.idAlbum
            JOIN Genero g ON c.idGenero = g.idGenero
            JOIN Idiomas i ON c.idIdioma = i.idIdioma
            
            
            
            
            
        """;

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CancionDAO cancion = new CancionDAO();
                cancion.setIdCancion(rs.getInt("idCancion"));
                cancion.setNombreCancion(rs.getString("nombreCancion"));
                cancion.setPrecio(rs.getDouble("precio"));
                cancion.setAlbumNombre(rs.getString("nombreAlbum"));
                cancion.setGeneroNombre(rs.getString("nombreGenero"));
                cancion.setIdiomaNombre(rs.getString("nombreIdioma"));
                lista.add(cancion);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar las canciones: " + e.getMessage());
        }

        return lista;
    }

    // Método UPDATE
    public void UPDATE() {
        String query = "UPDATE Canciones SET nombreCancion = ?, precio = ?, idAlbum = ?, idGenero = ?, idIdioma = ? WHERE idCancion = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreCancion());
            stmt.setDouble(2, getPrecio());
            stmt.setInt(3, getIdAlbum());
            stmt.setInt(4, getIdGenero());
            stmt.setInt(5, getIdIdioma());
            stmt.setInt(6, getIdCancion());

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

    // Método DELETE
    public void DELETE() {
        String query = "DELETE FROM Canciones WHERE idCancion = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdCancion());
            stmt.executeUpdate();
            System.out.println("Canción eliminada correctamente.");
        } catch (Exception e) {
            System.err.println("Error en DELETE: " + e.getMessage());
        }
    }
}
