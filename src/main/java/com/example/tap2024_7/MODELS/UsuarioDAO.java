package com.example.tap2024_7.MODELS;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioDAO {

    // Propiedades
    private int idUsuario;
    private StringProperty nombreUsuario = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty tipoUsuario = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty telefono = new SimpleStringProperty();

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }

    public StringProperty nombreUsuarioProperty() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario.set(nombreUsuario);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getTipoUsuario() {
        return tipoUsuario.get();
    }

    public StringProperty tipoUsuarioProperty() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario.set(tipoUsuario);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    // Métodos CRUD

    public ObservableList<UsuarioDAO> SELECTALL() {
        ObservableList<UsuarioDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM Usuarios";
        try {
            Statement stmt = Conexion.getConnection().createStatement(); // Usar getConnection()
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                UsuarioDAO usuario = new UsuarioDAO();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                lista.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean validarUsuario(String usuario, String password) {
        String query = "SELECT * FROM Usuarios WHERE nombreUsuario = '" + usuario + "' AND password = '" + password + "'";
        try {
            Statement stmt = Conexion.getConnection().createStatement(); // Usar getConnection()
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                this.idUsuario = rs.getInt("idUsuario");
                this.nombreUsuario.set(rs.getString("nombreUsuario"));
                this.tipoUsuario.set(rs.getString("tipoUsuario"));
                this.email.set(rs.getString("email"));
                this.telefono.set(rs.getString("telefono"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void INSERT() {
        String query = "INSERT INTO Usuarios (nombreUsuario, password, tipoUsuario, email, telefono) VALUES " +
                "('" + getNombreUsuario() + "', '" + getPassword() + "', '" + getTipoUsuario() + "', '" + getEmail() + "', '" + getTelefono() + "')";
        try {
            Statement stmt = Conexion.getConnection().createStatement(); // Usar getConnection()
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE Usuarios SET nombreUsuario = '" + getNombreUsuario() +
                "', password = '" + getPassword() + "', tipoUsuario = '" + getTipoUsuario() +
                "', email = '" + getEmail() + "', telefono = '" + getTelefono() +
                "' WHERE idUsuario = " + getIdUsuario();
        try {
            Statement stmt = Conexion.getConnection().createStatement(); // Usar getConnection()
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Usuarios WHERE idUsuario = " + getIdUsuario();
        try {
            Statement stmt = Conexion.getConnection().createStatement(); // Usar getConnection()
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
