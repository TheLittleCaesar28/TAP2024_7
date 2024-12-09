package com.example.tap2024_7.VISTAS;

import java.time.LocalDate;
import java.time.LocalTime;

public class Tarea {
    private int noArchivo;
    private String nombreArchivo;
    private int numHojas;
    private LocalDate fecha;
    private LocalTime hora;

    public Tarea(int noArchivo, String nombreArchivo, int numHojas, LocalDate fecha, LocalTime hora) {
        this.noArchivo = noArchivo;
        this.nombreArchivo = nombreArchivo;
        this.numHojas = numHojas;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getNoArchivo() {
        return noArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public int getNumHojas() {
        return numHojas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }
}
