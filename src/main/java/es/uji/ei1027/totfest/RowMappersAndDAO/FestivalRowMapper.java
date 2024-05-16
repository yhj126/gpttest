package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Festival;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class FestivalRowMapper implements RowMapper<Festival> {
    public Festival mapRow(ResultSet rs, int rowNum) throws SQLException {
        Festival festival = new Festival();
        festival.setNombre(rs.getString("nombreFestival"));
        festival.setLocalizacion(rs.getString("localizacion"));
        festival.setTipoFestival(rs.getString("tipoFestival"));
        festival.setDescripcion(rs.getString("descripcion"));
        festival.setAudiencia(rs.getString("audiencia"));
        festival.setCapacidad(rs.getInt("capacidad"));
        festival.setMinimoEdad(rs.getInt("minimoEdad"));
        festival.setEnVenta(rs.getBoolean("enVenta"));
        festival.setFechaIni(rs.getObject("fechaInicio", LocalDate.class));
        festival.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        festival.setPromotor(rs.getString("usuarioPromotor"));
        return festival;
    }
}
