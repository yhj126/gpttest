package es.uji.ei1027.totfest.RowMappersAndDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import es.uji.ei1027.totfest.Model.Promotor;
import org.springframework.jdbc.core.RowMapper;

public final class PromotorRowMapper implements RowMapper<Promotor> {
    public Promotor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Promotor promotor = new Promotor();
        promotor.setUsuario(rs.getString("usuario"));
        promotor.setNombre(rs.getString("nombre"));
        promotor.setPwd(rs.getString("pwd"));
        promotor.setEntidad(rs.getString("entidad"));
        promotor.setDni(rs.getString("dni"));
        promotor.setTipoOrg(rs.getString("tipoorg"));
        promotor.setInicioRelacion(rs.getObject("relini", LocalDate.class));
        promotor.setFinRelacion(rs.getObject("relfin", LocalDate.class));

        return promotor;
    }
}