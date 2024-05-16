package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Artista;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ArtistaRowMapper implements RowMapper<Artista> {
    public Artista mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artista artista = new Artista();
        artista.setId(rs.getInt("idArtista"));
        artista.setNombre(rs.getString("nombre"));
        artista.setTipoMusica(rs.getString("tipoMusica"));
        artista.setBiografia(rs.getString("biografia"));
        artista.setCache(rs.getFloat("cache"));
        return artista;
    }

}
