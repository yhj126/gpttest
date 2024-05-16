package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.UsuarioRegistrado;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UsuarioRegistradoRowMapper implements RowMapper<UsuarioRegistrado> {
    public UsuarioRegistrado mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsuarioRegistrado usuarioRegistrado = new UsuarioRegistrado();
        usuarioRegistrado.setId(rs.getInt("idUsuario"));
        usuarioRegistrado.setNombre(rs.getString("nombre"));
        usuarioRegistrado.setPwd(rs.getString("pwd"));
        usuarioRegistrado.setCorreo(rs.getString("correo"));
        return usuarioRegistrado;
    }

}
