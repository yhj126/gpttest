package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Persona;
import es.uji.ei1027.totfest.Model.UsuarioRegistrado;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


@Repository // En Spring los DAOs van anotados con @Repository
public class UsuarioRegistradoDAO implements PersonaDAO {
    private JdbcTemplate jdbcTemplate;
    private BasicPasswordEncryptor passwordEncryptor;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        passwordEncryptor = new BasicPasswordEncryptor();
    }

    public void addUsuarioRegistrado(UsuarioRegistrado usuarioRegistrado) {
        String contraseñaCifrada= passwordEncryptor.encryptPassword(usuarioRegistrado.getPwd());
        jdbcTemplate.update(
                "INSERT INTO UsuarioRegistrado (nombre, pwd, correo) VALUES(?, ?, ?)",
                usuarioRegistrado.getNombre(), contraseñaCifrada, usuarioRegistrado.getCorreo());
    }


    public void deleteUsuarioRegistrado(int idUsuario) {
        jdbcTemplate.update("DELETE FROM UsuarioRegistrado WHERE idUsuario = ?", idUsuario);
    }


    public void updateUsuarioRegistrado(UsuarioRegistrado usuarioRegistrado) {
        String contraseñaCifrada= passwordEncryptor.encryptPassword(usuarioRegistrado.getPwd());
        jdbcTemplate.update("UPDATE UsuarioRegistrado SET nombre = ?, pwd = ?, correo = ? WHERE idUsuario = ?",
                usuarioRegistrado.getNombre(), contraseñaCifrada, usuarioRegistrado.getCorreo(),usuarioRegistrado.getId());
    }


    public UsuarioRegistrado getUsuarioRegistrado(int idUsuario) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM UsuarioRegistrado WHERE idUsuario = ?",
                    new UsuarioRegistradoRowMapper(), idUsuario);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UsuarioRegistrado getUsuarioRegistradoPorCorreo(String correo) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM UsuarioRegistrado WHERE correo = ?",
                    new UsuarioRegistradoRowMapper(), correo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<UsuarioRegistrado> getUsuarioRegistrado() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM UsuarioRegistrado",
                    new UsuarioRegistradoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<UsuarioRegistrado>();
        }
    }

    @Override
    public UsuarioRegistrado loadUserByUsername(String username, String password) {
        UsuarioRegistrado user = getUsuarioRegistradoPorCorreo(username.trim());
        if (user == null){
            return null;}
        // Contrasenya
        if (passwordEncryptor.checkPassword(password.trim(), user.getPwd())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        }
        else {
            return null; // bad login!
        }
    }

}

