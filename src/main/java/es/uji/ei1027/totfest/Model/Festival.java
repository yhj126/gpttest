package es.uji.ei1027.totfest.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Festival {
    private String nombre;
    private String localizacion;
    private String tipoFestival;
    private String descripcion;
    private String audiencia;
    private int capacidad;
    private int minimoEdad;
    private boolean enVenta;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIni;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private String promotor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getTipoFestival() {
        return tipoFestival;
    }

    public void setTipoFestival(String tipoFestival) {
        this.tipoFestival = tipoFestival;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAudiencia() {
        return audiencia;
    }

    public void setAudiencia(String audiencia) {
        this.audiencia = audiencia;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getMinimoEdad() {
        return minimoEdad;
    }

    public void setMinimoEdad(int minimoEdad) {
        this.minimoEdad = minimoEdad;
    }

    public boolean isEnVenta() {
        return enVenta;
    }

    public void setEnVenta(boolean enVenta) {
        this.enVenta = enVenta;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPromotor() {
        return promotor;
    }

    public void setPromotor(String promotor) {
        this.promotor = promotor;
    }

    @Override
    public String toString() {
        return "Festival{" +
                "nombre='" + nombre + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", tipoFestival='" + tipoFestival + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", audiencia='" + audiencia + '\'' +
                ", capacidad=" + capacidad +
                ", minimoEdad=" + minimoEdad +
                ", enVenta=" + enVenta +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                ", promotor='" + promotor + '\'' +
                '}';
    }
}
