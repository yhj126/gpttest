package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Persona;
import es.uji.ei1027.totfest.Model.Promotor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository //En Spring los DAOs van anotados con @Repository
public class PromotorDAO implements PersonaDAO {
    private JdbcTemplate jdbcTemplate;
    private BasicPasswordEncryptor passwordEncryptor;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        passwordEncryptor = new BasicPasswordEncryptor();
    }

    public void addPromotor(Promotor promotor) {
        String contraseñaCifrada= passwordEncryptor.encryptPassword(promotor.getPwd());
        jdbcTemplate.update(
                "INSERT INTO promotor VALUES(?, ?, ?, ?, ?, ?::tipo_organizacion, ?, ?)",
                promotor.getUsuario(), promotor.getNombre(), contraseñaCifrada, promotor.getEntidad(),
                promotor.getDni(), promotor.getTipoOrg(), promotor.getInicioRelacion(), promotor.getFinRelacion());
    }

    public void deletePromotor(String usuario) {
        jdbcTemplate.update("DELETE FROM promotor WHERE usuario = ?", usuario);
    }

    public void updatePromotor(Promotor promotor) {
        String contraseñaCifrada= passwordEncryptor.encryptPassword(promotor.getPwd());
        jdbcTemplate.update("UPDATE promotor SET nombre = ?,  pwd = ?, entidad = ?, dni = ?, tipoorg = ?::tipo_organizacion, relini = ?, relfin = ? WHERE usuario = ?",
                promotor.getNombre(), contraseñaCifrada, promotor.getEntidad(),
                promotor.getDni(), promotor.getTipoOrg(), promotor.getInicioRelacion(), promotor.getFinRelacion(), promotor.getUsuario());
    }

    public Promotor getPromotor(String usuario) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM promotor WHERE usuario = ?",
                    new PromotorRowMapper(), usuario);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Promotor> getPromotor() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM promotor",
                    new PromotorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Promotor>();
        }
    }

    @Override
    public Promotor loadUserByUsername(String username, String password) {
        Promotor user = getPromotor(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        }
        else {
            return null; // bad login!
        }
    }
}