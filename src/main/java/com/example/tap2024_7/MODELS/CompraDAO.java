package com.example.tap2024_7.MODELS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CompraDAO {
    private Integer idCompra;
    private Integer idUsuario;
    private String fechaCompra;
    private Double totalCompra;
    private ObservableList<String> cancionesCompradas;

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public ObservableList<String> getCancionesCompradas() {
        return cancionesCompradas;
    }

    public void setCancionesCompradas(ObservableList<String> cancionesCompradas) {
        this.cancionesCompradas = cancionesCompradas;
    }

    public String getCancionesCompradasTexto() {
        if (cancionesCompradas == null || cancionesCompradas.isEmpty()) {
            return "Sin canciones"; // Mostrar un texto predeterminado si no hay canciones
        }
        return String.join(", ", cancionesCompradas); // Concatenar las canciones con comas
    }

    public ObservableList<CompraDAO> SELECTALL(int idUsuario) {
        ObservableList<CompraDAO> lista = FXCollections.observableArrayList();
        String query = """
            SELECT c.idCompra, c.fechaCompra, 
                   SUM(can.precio) AS totalCompra,
                   GROUP_CONCAT(can.nombreCancion SEPARATOR ', ') AS cancionesCompradas
            FROM Compras c
            LEFT JOIN CompraDetalle cd ON c.idCompra = cd.idCompra
            LEFT JOIN Canciones can ON cd.idCancion = can.idCancion
            WHERE c.idUsuario = ?
            GROUP BY c.idCompra
        """;

        try (PreparedStatement stmt = Conexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CompraDAO compra = new CompraDAO();
                compra.setIdCompra(rs.getInt("idCompra"));
                compra.setFechaCompra(rs.getString("fechaCompra"));
                compra.setTotalCompra(rs.getDouble("totalCompra"));

                String canciones = rs.getString("cancionesCompradas");
                if (canciones != null && !canciones.trim().isEmpty()) {
                    compra.setCancionesCompradas(
                            FXCollections.observableArrayList(canciones.split(", "))
                    );
                } else {
                    compra.setCancionesCompradas(FXCollections.observableArrayList());
                }

                lista.add(compra);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el historial de compras: " + e.getMessage());
        }
        return lista;
    }

    public Map<String, Integer> obtenerVentasPorMes() {
        Map<String, Integer> ventasPorMes = new HashMap<>();
        String query = """
            SELECT MONTH(fechaCompra) AS mes, COUNT(*) AS cantidad
            FROM Compras
            GROUP BY MONTH(fechaCompra)
        """;

        try (Statement stmt = Conexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String mes = convertirMes(rs.getInt("mes")); // Convertir número del mes a texto
                int cantidad = rs.getInt("cantidad");
                ventasPorMes.put(mes, cantidad);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener ventas por mes: " + e.getMessage());
        }

        return ventasPorMes;
    }

    private String convertirMes(int mes) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes - 1];
    }

    public int INSERT(int idUsuario) {
        String query = "INSERT INTO Compras (idUsuario, fechaCompra) VALUES (?, NOW())";
        try (PreparedStatement stmt = Conexion.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error al insertar la compra: " + e.getMessage());
        }
        return -1;
    }

    public void insertDetalle(int idCompra, int idCancion) {
        String query = "INSERT INTO CompraDetalle (idCompra, idCancion) VALUES (?, ?)";
        try (PreparedStatement stmt = Conexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, idCompra);
            stmt.setInt(2, idCancion);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al insertar el detalle de la compra: " + e.getMessage());
        }
    }
    public void eliminarComprasSinCanciones() {
        String query = """
        DELETE FROM Compras
        WHERE idCompra IN (
            SELECT c.idCompra
            FROM Compras c
            LEFT JOIN CompraDetalle cd ON c.idCompra = cd.idCompra
            WHERE cd.idCancion IS NULL
        );
    """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            int filasEliminadas = stmt.executeUpdate();
            System.out.println(filasEliminadas + " compras sin canciones eliminadas.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar compras sin canciones: " + e.getMessage());
        }
    }

}
