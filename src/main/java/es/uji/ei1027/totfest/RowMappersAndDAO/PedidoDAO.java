package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository //En Spring los DAOs van anotados con @Repository
public class PedidoDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPedido(Pedido pedido) {
        jdbcTemplate.update(
                "INSERT INTO pedido (correo, fecha, cantidadEntradas, idUsuario) " +
                        "VALUES(?, ?, ?, ?)",
                pedido.getCorreo(), pedido.getFecha(), pedido.getCantidadEntradas(),
                pedido.getIdUsuario());
    }

    public void deletePedido(int idPedido) {
        jdbcTemplate.update("DELETE FROM pedido WHERE idpedido = ?", idPedido);
    }

    public void updatePedido(Pedido pedido) {
        jdbcTemplate.update("UPDATE pedido SET correo = ?, fecha = ?,  cantidadentradas = ?, idusuario = ? WHERE idpedido = ?",
                pedido.getCorreo(), pedido.getFecha(), pedido.getCantidadEntradas(),
                pedido.getIdUsuario(), pedido.getIdPedido());
    }

    public Pedido getPedido(int idPedido) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM pedido WHERE idpedido = ?",
                    new PedidoRowMapper(), idPedido);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Pedido> getPedido() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM pedido",
                    new PedidoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Pedido>();
        }
    }

    public LocalDateTime getFechaActual() {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT now()",  LocalDateTime.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Integer getIdPedido(Pedido pedido) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT idpedido FROM pedido WHERE correo = ? AND fecha = ? ",
                    Integer.class, pedido.getCorreo(), pedido.getFecha());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}