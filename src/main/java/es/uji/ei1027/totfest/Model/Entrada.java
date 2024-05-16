package es.uji.ei1027.totfest.Model;

public class Entrada {
    private int numero;
    private float precio;
    private int idPedido;
    private String tipo;
    private String nombreFestival;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreFestival() {
        return nombreFestival;
    }

    public void setNombreFestival(String nombreFestival) {
        this.nombreFestival = nombreFestival;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "numero=" + numero +
                ", precio=" + precio +
                ", idPedido='" + idPedido + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nombreFestival='" + nombreFestival + '\'' +
                '}';
    }
}
