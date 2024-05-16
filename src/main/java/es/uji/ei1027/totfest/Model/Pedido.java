package es.uji.ei1027.totfest.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class Pedido {
    private int idPedido;
    private String correo;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.nnnnnnnnn")
    private LocalDateTime fecha;
    private int cantidadEntradas;
    private Integer idUsuario;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getFecha() {return fecha;}

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido='" + idPedido + '\'' +
                ", correo='" + correo + '\'' +
                ", fecha=" + fecha +
                ", cantidadEntradas=" + cantidadEntradas +
                ", idUsuario='" + idUsuario + '\'' +
                '}';
    }
}
