package es.uji.ei1027.totfest.RowMappersAndDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.totfest.Model.Actuacion;
import org.springframework.jdbc.core.RowMapper;
import java.time.LocalDate;
import java.time.LocalTime;

public final class ActuacionRowMapper implements RowMapper<Actuacion> {
    public Actuacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actuacion actuacion = new Actuacion();
        actuacion.setId(rs.getInt("idActuacion"));
        actuacion.setInicioActuacion(rs.getObject("horaIni", LocalTime.class));
        actuacion.setFinActuacion(rs.getObject("horaFin", LocalTime.class));
        actuacion.setFechaActuacion(rs.getObject("fechaAct", LocalDate.class));
        actuacion.setLugar(rs.getString("lugar"));
        actuacion.setPrecioContrato(rs.getFloat("precioContrato"));
        actuacion.setNombreFestival(rs.getString("nombreFestival"));
        actuacion.setIdContrato(rs.getInt("idContrato"));
        return actuacion;
    }
}
