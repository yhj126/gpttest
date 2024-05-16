package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Festival;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring los DAOs van anotados con @Repository
public class FestivalDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addFestival(Festival festival) {
        jdbcTemplate.update(
                "INSERT INTO Festival VALUES(?, ?, ?::tipo_festival, ?, ?::tipo_audiencia, ?, ?, ?, ?, ?, ?)",
                festival.getNombre(), festival.getLocalizacion(), festival.getTipoFestival(), festival.getDescripcion(), festival.getAudiencia(), festival.getCapacidad(),
                festival.getMinimoEdad(), festival.isEnVenta(), festival.getFechaIni(), festival.getFechaFin(), festival.getPromotor());
    }


    public void deleteFestival(String nombre) {
        jdbcTemplate.update("DELETE FROM Festival WHERE nombreFestival = ?", nombre);
    }


    public void updateFestival(Festival festival) {
        jdbcTemplate.update("UPDATE Festival SET localizacion =?, tipoFestival =?::tipo_festival, descripcion =?, audiencia =?::tipo_audiencia, capacidad =?, " +
                        "minimoEdad = ?, enVenta = ?, fechaInicio = ?, fechaFin  = ?, usuarioPromotor  = ?  WHERE nombreFestival =?",
                festival.getLocalizacion(), festival.getTipoFestival(), festival.getDescripcion(), festival.getAudiencia(), festival.getCapacidad(),
                festival.getMinimoEdad(), festival.isEnVenta(), festival.getFechaIni(), festival.getFechaFin(), festival.getPromotor(), festival.getNombre());
    }

    public Festival getFestival(String nombre) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Festival WHERE nombreFestival = ?",
                    new FestivalRowMapper(), nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Festival> getFestival() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Festival",
                    new FestivalRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Festival>();
        }
    }
    //Festivales disponibles para comprar
    public List<Festival> getFestivalAVenta() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Festival WHERE enVenta = TRUE",
                    new FestivalRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Festival>();
        }
    }
    //Festivales no disponibles para comprar
    public List<Festival> getFestivalNoVenta() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Festival WHERE enVenta  = FALSE",
                    new FestivalRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Festival>();
        }
    }
    //Buscar festivales en venta
    public List<Festival> getFestivalEnVenta(String nombre) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Festival WHERE upper(nombreFestival) LIKE upper(?) and enVenta = TRUE",
                    new FestivalRowMapper(), nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    //Buscar festivales en venta
    public List<Festival> getFestivalNoVenta(String nombre) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Festival WHERE upper(nombreFestival) LIKE upper(?) and enVenta = FALSE",
                    new FestivalRowMapper(), nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //Festivales del promotor
    public List<Festival> getMisFestival(String promotor) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Festival WHERE usuarioPromotor=?",
                    new FestivalRowMapper(), promotor);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Festival>();
        }
    }

}

