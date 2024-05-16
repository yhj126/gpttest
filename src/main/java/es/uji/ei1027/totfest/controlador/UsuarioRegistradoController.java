package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Persona;
import es.uji.ei1027.totfest.Model.UsuarioRegistrado;
import es.uji.ei1027.totfest.RowMappersAndDAO.UsuarioRegistradoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
class UserRegistradoValidator implements Validator {
    private UsuarioRegistradoDAO usuarioRegistradoDAO;

    public UserRegistradoValidator(UsuarioRegistradoDAO usuarioRegistradoDAO) {
        this.usuarioRegistradoDAO = usuarioRegistradoDAO;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Persona.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Persona user = (Persona) obj;
        if (user.getUsername().trim().isEmpty())
            errors.rejectValue("username", "obligatorio",
                    "Falta introducir el usuario");
        if (user.getPassword().trim().isEmpty())
            errors.rejectValue("password", "obligatorio",
                    "Falta introducir la contraseña");
        if (usuarioRegistradoDAO.getUsuarioRegistradoPorCorreo(user.getUsername()) != null)
            errors.rejectValue("username", "existe","Ya hay un usuario registrado con este correo");
    }
}

@Controller
@RequestMapping("/usuarioRegistrado")
public class UsuarioRegistradoController {
    private UsuarioRegistradoDAO usuarioRegistradoDAO;

    @Autowired
    public void setUsuarioRegistradoDAO(UsuarioRegistradoDAO usuarioRegistradoDAO) {
        this.usuarioRegistradoDAO = usuarioRegistradoDAO;
    }

    @RequestMapping("/list")
    public String listUsuariosRegistrados(Model model) {
        model.addAttribute("usuariosRegistrados", usuarioRegistradoDAO.getUsuarioRegistrado());
        return "usuarioRegistrado/list";
    }

    @RequestMapping(value = "/add")
    public String addUsuarioRegistrado(Model model) {
        model.addAttribute("usuarioRegistrado", new UsuarioRegistrado());
        return "usuarioRegistrado/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("usuarioRegistrado") UsuarioRegistrado usuarioRegistrado,
                                   BindingResult bindingResult) {
        UserRegistradoValidator usuarioRegistradoValidator = new UserRegistradoValidator(usuarioRegistradoDAO);
        usuarioRegistradoValidator.validate(usuarioRegistrado, bindingResult);
        if (bindingResult.hasErrors())
            return "usuarioRegistrado/add";
        usuarioRegistradoDAO.addUsuarioRegistrado(usuarioRegistrado);
        return "redirect:/login"; //Al registrarse tiene que llevarte al login
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editarUsuarioRegistrado(Model model, @PathVariable int id) {
        model.addAttribute("usuarioRegistrado", usuarioRegistradoDAO.getUsuarioRegistrado(id));
        return "usuarioRegistrado/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("usuarioRegistrado") UsuarioRegistrado usuarioRegistrado,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "usuarioRegistrado/update";
        usuarioRegistradoDAO.updateUsuarioRegistrado(usuarioRegistrado);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDelete(@PathVariable int id) {
        usuarioRegistradoDAO.deleteUsuarioRegistrado(id);
        return "redirect:../list";
    }
}
