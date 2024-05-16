package es.uji.ei1027.totfest.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class Contrato {
    private int id;
    private float tasa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFirma;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIni;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private int numActuaciones;
    private String descripcion;
    private int idArtista;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTasa() {
        return tasa;
    }

    public void setTasa(float tasa) {
        this.tasa = tasa;
    }

    public LocalDate getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(LocalDate fechaFirma) {
        this.fechaFirma = fechaFirma;
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

    public int getNumActuaciones() {
        return numActuaciones;
    }

    public void setNumActuaciones(int numActuaciones) {
        this.numActuaciones = numActuaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id='" + id + '\'' +
                ", tasa=" + tasa +
                ", fechaFirma=" + fechaFirma +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                ", numActuaciones=" + numActuaciones +
                ", descripcion='" + descripcion + '\'' +
                ", idArtista='" + idArtista + '\'' +
                '}';
    }
}
