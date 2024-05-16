package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.TipoEntrada;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TipoEntradaRowMapper implements RowMapper<TipoEntrada> {
    public TipoEntrada mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoEntrada tipoEntrada = new TipoEntrada();
        tipoEntrada.setTipo(rs.getString("tipo"));
        tipoEntrada.setPrecio(rs.getFloat("precio"));
        tipoEntrada.setTipoDuracion(rs.getString("tipoduracion"));
        tipoEntrada.setDia(rs.getObject("dia", LocalDate.class));
        tipoEntrada.setDescuento(rs.getFloat("descuento"));
        tipoEntrada.setNombreFestival(rs.getString("nombrefestival"));
        return tipoEntrada;
    }
}