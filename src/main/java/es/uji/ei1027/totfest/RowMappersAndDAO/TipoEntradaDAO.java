package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.TipoEntrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository// En Spring los DAOs van anotados con @Repository
public class TipoEntradaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTipoEntrada(TipoEntrada tipoEntrada) {
        jdbcTemplate.update(
                "INSERT INTO tipoentrada VALUES(?::tipo_entrada, ?, ?::tipo_duracion, ?, ?, ?)",
                tipoEntrada.getTipo(), tipoEntrada.getPrecio(), tipoEntrada.getTipoDuracion(), tipoEntrada.getDia(),
                tipoEntrada.getDescuento(), tipoEntrada.getNombreFestival());
    }

    /* Esborra el tipus de entrada de la base de dades */
    public void deleteTipoEntrada(String tipo, String nombreFestival) {
        jdbcTemplate.update("DELETE FROM tipoentrada WHERE tipo=?::tipo_entrada AND nombrefestival = ?",tipo,nombreFestival);
    }

    /*Actualiza el tipo de entrada*/
    public void updateTipoEntrada(TipoEntrada tipoEntrada) {
        jdbcTemplate.update("UPDATE tipoentrada SET precio = ?, tipoduracion = ?::tipo_duracion, dia = ?,  descuento = ? WHERE tipo = ?::tipo_entrada AND nombrefestival=?",
                tipoEntrada.getPrecio(), tipoEntrada.getTipoDuracion(), tipoEntrada.getDia(),
                tipoEntrada.getDescuento(), tipoEntrada.getTipo(), tipoEntrada.getNombreFestival());
    }

    /* Obté el tipus de entrada amb el nom donat. Torna null si no existeix. */
    public TipoEntrada getTipoEntrada(String tipo) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM tipoentrada WHERE tipo = ?::tipo_entrada",
                    new TipoEntradaRowMapper(), tipo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public TipoEntrada getTipoEntrada(String tipo, String nombrefestival) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM tipoentrada WHERE tipo = ?::tipo_entrada AND nombrefestival=?",
                    new TipoEntradaRowMapper(), tipo,nombrefestival);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    /* Una lista con todos los tipos de entradas */
    public List<TipoEntrada> getTipoEntrada() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM TipoEntrada",
                    new TipoEntradaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<TipoEntrada>();
        }
    }

    //Comprar
    public List<TipoEntrada> getTipoEntradaFest( String nombrefestival) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM TipoEntrada WHERE nombrefestival=?",
                    new TipoEntradaRowMapper(), nombrefestival);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<TipoEntrada>();
        }
    }

    //Fecha
    public LocalDate getFechaActual() {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT now()",  LocalDate.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}



