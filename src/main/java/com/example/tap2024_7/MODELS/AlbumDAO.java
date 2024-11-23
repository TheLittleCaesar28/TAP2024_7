package com.example.tap2024_7.MODELS;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumDAO {

    private IntegerProperty idAlbum = new SimpleIntegerProperty();
    private StringProperty nombreAlbum = new SimpleStringProperty();
    private IntegerProperty idArtista = new SimpleIntegerProperty();
    private StringProperty nombreArtista = new SimpleStringProperty();
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

    // Métodos CRUD
    public ObservableList<AlbumDAO> SELECTALL() {
        ObservableList<AlbumDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT a.idAlbum, a.nombreAlbum, a.idArtista, ar.nombreArtista, a.imagen " +
                "FROM Albumes a " +
                "JOIN Artistas ar ON a.idArtista = ar.idArtista";

        try {
            Statement stmt = Conexion.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            e.printStackTrace();
        }
        return lista;
    }

    public void INSERT() {
        String query = "INSERT INTO Albumes (nombreAlbum, idArtista, imagen) " +
                "VALUES ('" + getNombreAlbum() + "', " + getIdArtista() + ", '" + getRutaImagen() + "')";
        try {
            Statement stmt = Conexion.getConnection().createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE Albumes SET nombreAlbum = '" + getNombreAlbum() +
                "', idArtista = " + getIdArtista() +
                ", imagen = '" + getRutaImagen() + "'" +
                " WHERE idAlbum = " + getIdAlbum();
        try {
            Statement stmt = Conexion.getConnection().createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Albumes WHERE idAlbum = " + getIdAlbum();
        try {
            Statement stmt = Conexion.getConnection().createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
