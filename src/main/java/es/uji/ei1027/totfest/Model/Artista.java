package es.uji.ei1027.totfest.Model;

public class Artista {
    private int id;
    private String nombre;
    private String tipoMusica;
    private String biografia;
    private float cache;

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

    public String getTipoMusica() {
        return tipoMusica;
    }

    public void setTipoMusica(String tipoMusica) {
        this.tipoMusica = tipoMusica;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public float getCache() {
        return cache;
    }

    public void setCache(float cache) {
        this.cache = cache;
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipoMusica='" + tipoMusica + '\'' +
                ", biografia='" + biografia + '\'' +
                ", cache=" + cache +
                '}';
    }
}
