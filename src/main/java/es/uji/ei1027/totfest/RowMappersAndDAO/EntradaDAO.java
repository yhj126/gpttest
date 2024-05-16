package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Entrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntradaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addEntrada(Entrada entrada) {
        jdbcTemplate.update(
                "INSERT INTO entrada (precio, idPedido, tipo, nombreFestival) " +
                        "VALUES(?, ?, ?::tipo_entrada, ?)",
                entrada.getPrecio(), entrada.getIdPedido(), entrada.getTipo(), entrada.getNombreFestival());
    }

    /* Elimina la entrada de la base de datos */
    public void deleteEntrada(int numeroEntrada) {
        jdbcTemplate.update(
                "DELETE FROM entrada WHERE numero = ?", numeroEntrada);
    }

    /*Actualiza entrada*/
    public void updateEntrada(Entrada entrada) {
        jdbcTemplate.update("UPDATE entrada SET precio = ?, idpedido = ?, tipo = ?::tipo_entrada, nombrefestival=? WHERE numero = ? ",
                entrada.getPrecio(), entrada.getIdPedido(), entrada.getTipo(),
                entrada.getNombreFestival(), entrada.getNumero());
    }

    /* Obtiene  entrada con el nombre dado. Vuelve null si no existe.*/
    public Entrada getEntrada(int numero) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM entrada WHERE numero = ?",
                    new EntradaRowMapper(), numero);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Una lista con todos las entradas */
    public List<Entrada> getEntrada() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM entrada",
                    new EntradaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Entrada>();
        }
    }

    //Compra
    public List<Entrada> getEntradas(Entrada entrada) {
        try {
            return jdbcTemplate.query("SELECT * FROM entrada WHERE idpedido = ?",
                    new EntradaRowMapper(), entrada.getIdPedido());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Entrada>();
        }
    }
}