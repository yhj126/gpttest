package es.uji.ei1027.totfest.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class TipoEntrada {
    private String tipo;
    private float precio;
    private String tipoDuracion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia;
    private float descuento;
    private String nombreFestival;

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoDuracion() {
        return tipoDuracion;
    }

    public void setTipoDuracion(String tipoDuracion) {
        this.tipoDuracion = tipoDuracion;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public String getNombreFestival() {
        return nombreFestival;
    }

    public void setNombreFestival(String nombreFestival) {
        this.nombreFestival = nombreFestival;
    }

    @Override
    public String toString() {
        return "TipoEntrada{" +
                "precio=" + precio +
                ", tipo='" + tipo + '\'' +
                ", tipoDuracion='" + tipoDuracion + '\'' +
                ", dia=" + dia +
                ", descuento=" + descuento +
                ", nombreFestival='" + nombreFestival + '\'' +
                '}';
    }
}
