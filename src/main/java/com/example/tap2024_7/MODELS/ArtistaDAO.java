package com.example.tap2024_7.MODELS;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ArtistaDAO {
    private int idArtista;
    private StringProperty nombreArtista = new SimpleStringProperty();

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
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

    @Override
    public String toString() {
        return this.getNombreArtista();
    }

    public ObservableList<ArtistaDAO> SELECTALL() {
        ObservableList<ArtistaDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM Artistas";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ArtistaDAO artista = new ArtistaDAO();
                artista.setIdArtista(rs.getInt("idArtista"));
                artista.setNombreArtista(rs.getString("nombreArtista"));
                lista.add(artista);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de artistas: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public void INSERT() {
        String query = "INSERT INTO Artistas (nombreArtista) VALUES (?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, this.getNombreArtista());
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al insertar el artista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE Artistas SET nombreArtista = ? WHERE idArtista = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, this.getNombreArtista());
            pstmt.setInt(2, this.getIdArtista());
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al actualizar el artista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Artistas WHERE idArtista = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, this.getIdArtista());
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al eliminar el artista: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
