package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Contrato;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ContratoRowMapper implements RowMapper<Contrato> {
    public Contrato mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contrato contrato = new Contrato();
        contrato.setId(rs.getInt("idContrato"));
        contrato.setTasa(rs.getFloat("pagoArtista"));
        contrato.setFechaFirma(rs.getObject("fechaFirma", LocalDate.class));
        contrato.setFechaIni(rs.getObject("fechaIni", LocalDate.class));
        contrato.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        contrato.setNumActuaciones(rs.getInt("numActuaciones"));
        contrato.setDescripcion(rs.getString("descripcion"));
        contrato.setIdArtista(rs.getInt("idArtista"));
        return contrato;
    }
}
