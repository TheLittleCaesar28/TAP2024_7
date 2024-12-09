package com.example.tap2024_7.MODELS;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GeneroDAO {

    private final IntegerProperty idGenero = new SimpleIntegerProperty();
    private final StringProperty nombreGenero = new SimpleStringProperty();

    public int getIdGenero() {
        return idGenero.get();
    }

    public IntegerProperty idGeneroProperty() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero.set(idGenero);
    }

    public String getNombreGenero() {
        return nombreGenero.get();
    }

    public StringProperty nombreGeneroProperty() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero.set(nombreGenero);
    }

    public String toString() {
        return this.getNombreGenero();
    }

    public ObservableList<GeneroDAO> SELECTALL() {
        ObservableList<GeneroDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM Genero";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                GeneroDAO genero = new GeneroDAO();
                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombreGenero(rs.getString("nombreGenero"));
                lista.add(genero);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de géneros: " + e.getMessage());
        }

        return lista;
    }

    public void INSERT() {
        String query = "INSERT INTO Genero (nombreGenero) VALUES (?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreGenero());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al insertar el género: " + e.getMessage());
        }
    }

    public void UPDATE() {
        String query = "UPDATE Genero SET nombreGenero = ? WHERE idGenero = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreGenero());
            stmt.setInt(2, getIdGenero());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al actualizar el género: " + e.getMessage());
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Genero WHERE idGenero = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdGenero());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al eliminar el género: " + e.getMessage());
        }
    }
}
