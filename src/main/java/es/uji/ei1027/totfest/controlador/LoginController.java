package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Persona;
import es.uji.ei1027.totfest.Model.Promotor;
import es.uji.ei1027.totfest.Model.UsuarioRegistrado;
import es.uji.ei1027.totfest.RowMappersAndDAO.PersonaDAO;
import es.uji.ei1027.totfest.RowMappersAndDAO.PromotorDAO;
import es.uji.ei1027.totfest.RowMappersAndDAO.UsuarioRegistradoDAO;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Persona.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Persona user = (Persona) obj;
        if (user.getUsername().trim().equals(""))
            errors.rejectValue("username", "obligatorio",
                    "Falta introducir el usuario");
        if (user.getPassword().trim().equals(""))
            errors.rejectValue("password", "obligatorio",
                    "Falta introducir la contraseña");
    }
}

@Controller
public class LoginController {
    @Autowired
    private UsuarioRegistradoDAO usuarioRegistradoDao;
    @Autowired
    private PromotorDAO promotorDao;
    @Autowired
    private PersonaDAO fakeUserProvider;

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", session);
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Persona());
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Persona user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        Persona loadedUser;

        loadedUser = usuarioRegistradoDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (loadedUser == null) {
            loadedUser = promotorDao.loadUserByUsername(user.getUsername(), user.getPassword());
            if (loadedUser == null) {
                loadedUser = fakeUserProvider.loadUserByUsername(user.getUsername(), user.getPassword());
                if (loadedUser == null) {
                    bindingResult.rejectValue("password", "badpw", "Contraseña incorrecta");
                    return "login";
                }
            }
        }

        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", loadedUser);

        if (loadedUser instanceof Promotor) {
            return "redirect:/promotor/inicioManager";
        } else if (loadedUser instanceof UsuarioRegistrado) {
            return "redirect:/";
        } else {
            if (loadedUser.getUsername() == "gestor1")
                return "redirect:/artista/list";
            else
                return "redirect:/promotor/pagedlist"; //es un comercial
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
