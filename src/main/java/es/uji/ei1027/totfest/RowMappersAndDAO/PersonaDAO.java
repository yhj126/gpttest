package es.uji.ei1027.totfest.RowMappersAndDAO;

import es.uji.ei1027.totfest.Model.Persona;

import java.util.Collection;

public interface PersonaDAO {
    Persona loadUserByUsername(String username, String password);
}