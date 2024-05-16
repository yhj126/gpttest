package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Actuacion;
import es.uji.ei1027.totfest.Model.Artista;
import es.uji.ei1027.totfest.Model.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring los DAOs van anotados con @Repository
public class ActuacionDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActuacion(Actuacion actuacion) {
        jdbcTemplate.update(
                "INSERT INTO Actuacion (horaIni, horaFin, fechaAct, lugar, precioContrato, nombreFestival, idContrato) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                actuacion.getInicioActuacion(), actuacion.getFinActuacion(), actuacion.getFechaActuacion(),
                actuacion.getLugar(), actuacion.getPrecioContrato(), actuacion.getNombreFestival(), actuacion.getIdContrato());
    }

    /* Borra la actuacion de la base de datos */
    public void deleteActuacion(int actuacionID) {
        jdbcTemplate.update("DELETE FROM Actuacion WHERE idActuacion = ?", actuacionID);
    }

    /*Actualiza la actuacion*/
    public void updateActuacion(Actuacion actuacion) {
        jdbcTemplate.update("UPDATE Actuacion SET horaIni = ?, horaFin = ?, fechaAct = ?, lugar = ?, precioContrato = ?, nombreFestival = ?, idContrato = ? WHERE idActuacion = ?",
                actuacion.getInicioActuacion(), actuacion.getFinActuacion(), actuacion.getFechaActuacion(), actuacion.getLugar(),
                actuacion.getPrecioContrato(), actuacion.getNombreFestival(), actuacion.getIdContrato(), actuacion.getId());
    }

    /*Obtiene la actuación con el nombre dado. Vuelve null si no existe. */
    public Actuacion getActuacion(int idActuacion) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Actuacion WHERE idActuacion = ?",
                    new ActuacionRowMapper(), idActuacion);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Una lista con todas las actuaciones */
    public List<Actuacion> getActuacion() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Actuacion",
                    new ActuacionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actuacion>();
        }
    }
    //todas las actuaciones del festival
    public List<Actuacion> getActuacionFestival(String nombre) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Actuacion WHERE nombreFestival = ?",
                    new ActuacionRowMapper(), nombre);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actuacion>();
        }
    }
    //actuaciones del festival en las que participa el artista
    public List<Actuacion> getActuacionesArtistaFestival(String nombre, int idArtista) {
        try {
            return jdbcTemplate.query(
                    "SELECT Actuacion.* FROM Actuacion JOIN Contrato ON actuacion.idcontrato = contrato.idcontrato WHERE nombreFestival = ? and  idartista = ?",
                    new ActuacionRowMapper(), nombre, idArtista);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actuacion>();
        }
    }

    //actuaciones del festival en las que podría participar el artista
    public List<Actuacion> getPosiblesActuacionesArtistaFestival(String nombre, int idArtista) {
        try {
            return jdbcTemplate.query(
                    "SELECT Actuacion.* FROM Contrato, Actuacion WHERE nombreFestival = ? and idartista = ? and fechaini < fechaact and fechafin > fechaact and actuacion.idcontrato IS NULL",
                    new ActuacionRowMapper(), nombre, idArtista);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actuacion>();
        }
    }

    public Contrato getContratoVigenteArtista(int idArtista, int idActuacion) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT Contrato.* FROM Contrato, Actuacion WHERE idartista = ? and idactuacion = ? and fechaini < fechaact and fechafin > fechaact and actuacion.idcontrato IS NULL",
                    new ContratoRowMapper(), idArtista, idActuacion);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getNombreArtista(int idArtista) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT nombre FROM Artista WHERE idartista = ?",
                    String.class, idArtista);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }


}

