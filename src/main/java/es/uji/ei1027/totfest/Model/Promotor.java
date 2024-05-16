package es.uji.ei1027.totfest.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Promotor extends Persona {
    //private String usuario;Esto es el username que viene de Personas
    private String nombre;
    //private String pwd; Esto es el password que viene de Personas
    private String entidad;
    private String dni;
    private String  tipoOrg;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicioRelacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  finRelacion;

    public String getUsuario() {
        return username;
    }

    public void setUsuario(String usuario) {
        setUsername(usuario);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPwd() {
        return password;
    }

    public void setPwd(String pwd) {
        setPassword(pwd);
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTipoOrg() {
        return tipoOrg;
    }

    public void setTipoOrg(String tipoOrg) {
        this.tipoOrg = tipoOrg;
    }

    public LocalDate getInicioRelacion() {
        return inicioRelacion;
    }

    public void setInicioRelacion(LocalDate inicioRelacion) {
        this.inicioRelacion = inicioRelacion;
    }

    public LocalDate getFinRelacion() {
        return finRelacion;
    }

    public void setFinRelacion(LocalDate finRelacion) {
        this.finRelacion = finRelacion;
    }

    @Override
    public String toString() {
        return "Promotor{" +
                "usuario='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pwd='" + password + '\'' +
                ", entidad='" + entidad + '\'' +
                ", dni='" + dni + '\'' +
                ", tipoOrg='" + tipoOrg + '\'' +
                ", inicioRelacion=" + inicioRelacion +
                ", finRelacion=" + finRelacion +
                '}';
    }
}
