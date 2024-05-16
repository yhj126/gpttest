package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Artista;
import es.uji.ei1027.totfest.Model.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring los DAOs van anotados con @Repository
public class ArtistaDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addArtista(Artista artista) {
        jdbcTemplate.update(
                "INSERT INTO Artista (nombre, tipoMusica, biografia, cache) " +
                        "VALUES(?, ?::tipo_musica, ?, ?)",
                artista.getNombre(), artista.getTipoMusica(), artista.getBiografia(), artista.getCache());
    }


    public void deleteArtista(int idArtista) {
        jdbcTemplate.update("DELETE FROM Artista WHERE idArtista = ?", idArtista);
    }


    public void updateArtista(Artista artista) {
        jdbcTemplate.update("UPDATE Artista SET nombre = ?, tipoMusica = ?::tipo_musica, biografia = ?, cache = ? WHERE idArtista = ?",
                artista.getNombre(), artista.getTipoMusica(), artista.getBiografia(), artista.getCache(), artista.getId());
    }


    public Artista getArtista(int idUsuario) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Artista WHERE idArtista = ?",
                    new ArtistaRowMapper(), idUsuario);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Artista> getArtista() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Artista",
                    new ArtistaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Artista>();
        }
    }
}

