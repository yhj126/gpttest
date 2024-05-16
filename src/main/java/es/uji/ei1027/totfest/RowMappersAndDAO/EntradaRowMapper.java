package es.uji.ei1027.totfest.RowMappersAndDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.uji.ei1027.totfest.Model.Entrada;
import org.springframework.jdbc.core.RowMapper;

public final class EntradaRowMapper implements RowMapper<Entrada> {
    public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
        Entrada entrada = new Entrada();
        entrada.setNumero(rs.getInt("numero"));
        entrada.setPrecio(rs.getFloat("precio"));
        entrada.setIdPedido(rs.getInt("idpedido"));
        entrada.setTipo(rs.getString("tipo"));
        entrada.setNombreFestival(rs.getString("nombrefestival"));
        return entrada;
    }
}