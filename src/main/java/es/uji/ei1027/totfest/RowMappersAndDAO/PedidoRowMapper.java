package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Pedido;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PedidoRowMapper implements RowMapper<Pedido> {
    public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("idpedido"));
        pedido.setCorreo(rs.getString("correo"));
        pedido.setFecha(rs.getObject("fecha", LocalDateTime.class));
        pedido.setCantidadEntradas(rs.getInt("cantidadentradas"));
        pedido.setIdUsuario(rs.getInt("idusuario"));
        return pedido;
    }
}
