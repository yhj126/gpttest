package es.uji.ei1027.totfest.Model;

public class UsuarioRegistrado extends Persona {
    private int id;
    private String nombre;
    //private String pwd; Esto es el password que viene de Personas
    //private String correo; Esto es el username que viene de Personas

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCorreo() {
        return username;
    }

    public void setCorreo(String correo) {
        setUsername(correo);
    }

    @Override
    public String toString() {
        return "UsuarioRegistrado{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pwd='" + password + '\'' +
                ", correo='" + username + '\'' +
                '}';
    }
}
