package es.uji.ei1027.totfest.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Actuacion {
    private int id;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime inicioActuacion;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime finActuacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaActuacion;
    private String lugar;
    private float precioContrato;
    private String nombreFestival;
    private Integer idContrato;

    public Actuacion(){}
    public int getId() {
        return id;
    }

    public LocalTime getInicioActuacion() {
        return inicioActuacion;
    }

    public LocalTime getFinActuacion() {
        return finActuacion;
    }

    public LocalDate getFechaActuacion() {
        return fechaActuacion;
    }

    public String getLugar() {
        return lugar;
    }

    public float getPrecioContrato() {
        return precioContrato;
    }

    public String getNombreFestival() {
        return nombreFestival;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInicioActuacion(LocalTime inicioActuacion) {
        this.inicioActuacion = inicioActuacion;
    }

    public void setFinActuacion(LocalTime finActuacion) {
        this.finActuacion = finActuacion;
    }

    public void setFechaActuacion(LocalDate fechaActuacion) {
        this.fechaActuacion = fechaActuacion;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setPrecioContrato(float precioContrato) {
        this.precioContrato = precioContrato;
    }

    public void setNombreFestival(String nombreFestival) {
        this.nombreFestival = nombreFestival;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public String toString() {
        return "Actuacion{" +
                "id='" + id + '\'' +
                ", inicioActuacion=" + inicioActuacion +
                ", finActuacion=" + finActuacion +
                ", fechaActuacion=" + fechaActuacion +
                ", lugar='" + lugar + '\'' +
                ", contrato=" + precioContrato +
                ", nombreFestival='" + nombreFestival + '\'' +
                ", idContrato='" + idContrato + '\'' +
                '}';
    }
}
