package es.uji.ei1027.totfest.RowMappersAndDAO;

import java.util.HashMap;
import java.util.Map;

import es.uji.ei1027.totfest.Model.Persona;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

@Repository
public class FakeUserProvider implements PersonaDAO {
    Map<String, Persona> knownFakeUsers = new HashMap<>();

    public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        Persona userGestor1 = new Persona();
        userGestor1.setUsername("gestor1");
        userGestor1.setPassword(passwordEncryptor.encryptPassword("gestor1"));
        knownFakeUsers.put("gestor1", userGestor1);

        Persona userComercial1 = new Persona();
        userComercial1.setUsername("comercial1");
        userComercial1.setPassword(passwordEncryptor.encryptPassword("comercial1"));
        knownFakeUsers.put("comercial1", userComercial1);
    }

    @Override
    public Persona loadUserByUsername(String username, String password) {
        Persona user = knownFakeUsers.get(username.trim());
        if (user == null)
            return null;
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            return user;
        }
        else {
            return null;
        }
    }
}
