package com.example.tap2024_7.MODELS;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class IdiomaDAO {

    private final IntegerProperty idIdioma = new SimpleIntegerProperty();
    private final StringProperty nombreIdioma = new SimpleStringProperty();

    // Getters y Setters
    public int getIdIdioma() {
        return idIdioma.get();
    }

    public IntegerProperty idIdiomaProperty() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma.set(idIdioma);
    }

    public String getNombreIdioma() {
        return nombreIdioma.get();
    }

    public StringProperty nombreIdiomaProperty() {
        return nombreIdioma;
    }

    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma.set(nombreIdioma);
    }

    public String toString() {
        return this.getNombreIdioma();
    }

    // Método INSERT
    public void INSERT() {
        String query = "INSERT INTO Idiomas (nombreIdioma) VALUES (?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreIdioma());
            stmt.executeUpdate();
            System.out.println("Idioma insertado correctamente.");
        } catch (Exception e) {
            System.err.println("Error en INSERT: " + e.getMessage());
        }
    }

    // Método SELECTALL
    public static ObservableList<IdiomaDAO> SELECTALL() {
        ObservableList<IdiomaDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT idIdioma, nombreIdioma FROM Idiomas";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                IdiomaDAO idioma = new IdiomaDAO();
                idioma.setIdIdioma(rs.getInt("idIdioma"));
                idioma.setNombreIdioma(rs.getString("nombreIdioma"));
                lista.add(idioma);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los idiomas: " + e.getMessage());
        }

        return lista;
    }

    // Método UPDATE
    public void UPDATE() {
        String query = "UPDATE Idiomas SET nombreIdioma = ? WHERE idIdioma = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getNombreIdioma());
            stmt.setInt(2, getIdIdioma());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Idioma actualizado correctamente.");
            } else {
                System.err.println("Error al actualizar el idioma.");
            }
        } catch (Exception e) {
            System.err.println("Error en UPDATE: " + e.getMessage());
        }
    }

    // Método DELETE
    public void DELETE() {
        String query = "DELETE FROM Idiomas WHERE idIdioma = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getIdIdioma());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Idioma eliminado correctamente.");
            } else {
                System.err.println("Error al eliminar el idioma.");
            }
        } catch (Exception e) {
            System.err.println("Error en DELETE: " + e.getMessage());
        }
    }
}
