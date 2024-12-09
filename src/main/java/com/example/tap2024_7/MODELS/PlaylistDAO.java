package com.example.tap2024_7.MODELS;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlaylistDAO {

    private final IntegerProperty idPlaylist = new SimpleIntegerProperty();
    private final StringProperty nombrePlaylist = new SimpleStringProperty();
    private final IntegerProperty idUsuario = new SimpleIntegerProperty(); // Añadido el atributo idUsuario

    public int getIdPlaylist() {
        return idPlaylist.get();
    }

    public IntegerProperty idPlaylistProperty() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist.set(idPlaylist);
    }

    public String getNombrePlaylist() {
        return nombrePlaylist.get();
    }

    public StringProperty nombrePlaylistProperty() {
        return nombrePlaylist;
    }

    public void setNombrePlaylist(String nombrePlaylist) {
        this.nombrePlaylist.set(nombrePlaylist);
    }

    public int getIdUsuario() {
        return idUsuario.get();
    }

    public IntegerProperty idUsuarioProperty() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario.set(idUsuario);
    }

    @Override
    public String toString() {
        return getNombrePlaylist(); // Devuelve el nombre de la playlist
    }

    public void INSERT(int idUsuario) {
        String query = "INSERT INTO Playlist (nombrePlaylist, idUsuario) VALUES (?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombrePlaylist());
            stmt.setInt(2, idUsuario); // Inserta el idUsuario
            stmt.executeUpdate();
            System.out.println("Playlist insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la playlist: " + e.getMessage());
        }
    }

    public void UPDATE() {
        String query = "UPDATE Playlist SET nombrePlaylist = ? WHERE idPlaylist = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombrePlaylist());
            stmt.setInt(2, getIdPlaylist());
            stmt.executeUpdate();
            System.out.println("Playlist actualizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar la playlist: " + e.getMessage());
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Playlist WHERE idPlaylist = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdPlaylist());
            stmt.executeUpdate();
            System.out.println("Playlist eliminada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar la playlist: " + e.getMessage());
        }
    }

    public ObservableList<PlaylistDAO> SELECTALL() {
        ObservableList<PlaylistDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM Playlist";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                PlaylistDAO playlist = new PlaylistDAO();
                playlist.setIdPlaylist(rs.getInt("idPlaylist"));
                playlist.setNombrePlaylist(rs.getString("nombrePlaylist"));
                playlist.setIdUsuario(rs.getInt("idUsuario")); // Carga el idUsuario
                lista.add(playlist);
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar las playlist: " + e.getMessage());
        }

        return lista;
    }

    public void agregarCancionAPlaylist(int idCancion) {
        String query = "INSERT INTO cancionesplaylist (idPlaylist, idCancion) VALUES (?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdPlaylist());
            stmt.setInt(2, idCancion);
            stmt.executeUpdate();
            System.out.println("Canción agregada a la playlist correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al agregar canción a la playlist: " + e.getMessage());
        }
    }
}
