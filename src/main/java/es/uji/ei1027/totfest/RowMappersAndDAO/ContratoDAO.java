package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring los DAOs van anotados con @Repository
public class ContratoDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addContrato(Contrato contrato) {
        jdbcTemplate.update(
                "INSERT INTO Contrato (pagoArtista, fechaFirma, fechaIni, fechaFin, numActuaciones, descripcion, idArtista) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                contrato.getTasa(), contrato.getFechaFirma(), contrato.getFechaIni(), contrato.getFechaFin(),
                contrato.getNumActuaciones(), contrato.getDescripcion(), contrato.getIdArtista());
    }

    /* Borra el contrato de la base de datos */
    public void deleteContrato(int contratoID) {
        jdbcTemplate.update("DELETE FROM Contrato WHERE idContrato = ?", contratoID);
    }

    /*Actualiza el contrato*/
    public void updateContrato(Contrato contrato) {
        jdbcTemplate.update("UPDATE Contrato SET pagoArtista = ?, fechaFirma = ?, fechaIni = ?, fechaFin = ?, numActuaciones = ?, descripcion = ?, idArtista = ? WHERE idContrato = ?",
                contrato.getTasa(), contrato.getFechaFirma(), contrato.getFechaIni(), contrato.getFechaFin(),
                contrato.getNumActuaciones(), contrato.getDescripcion(), contrato.getIdArtista(), contrato.getId());
    }

    /*Obtiene el contrato con el nombre dado. Vuelve null si no existe. */
    public Contrato getContrato(int idContrato) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Contrato WHERE idContrato = ?",
                    new ContratoRowMapper(), idContrato);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene los contratos del artista. Vuelve null si no existe. */
    public List<Contrato> getContratosArtista(int idArtista) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Contrato WHERE idartista = ?",
                    new ContratoRowMapper(), idArtista);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contrato>();
        }
    }

    /* Una lista con todos los contratos */
    public List<Contrato> getContrato() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Contrato",
                    new ContratoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contrato>();
        }
    }

}

