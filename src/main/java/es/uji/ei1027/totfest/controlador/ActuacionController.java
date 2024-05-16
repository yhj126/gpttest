package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Actuacion;
import es.uji.ei1027.totfest.Model.Contrato;
import es.uji.ei1027.totfest.RowMappersAndDAO.ActuacionDAO;
import es.uji.ei1027.totfest.RowMappersAndDAO.ArtistaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/actuacion")
public class ActuacionController {
    private ActuacionDAO actuacionDAO;

    @Autowired
    public void setActuacionDAO(ActuacionDAO actuacionDAO) {
        this.actuacionDAO = actuacionDAO;
    }

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("actuaciones", actuacionDAO.getActuacion());
        return "list";
    }

    @RequestMapping(value = "/add")
    public String addActuacion(Model model) {
        model.addAttribute("actuacion", new Actuacion());
        return "actuacion/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("actuacion") Actuacion actuacion,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "actuacion/add";
        actuacionDAO.addActuacion(actuacion);
        return "redirect:listActuaciones";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editarActuacion(Model model, @PathVariable int id) {
        model.addAttribute("actuacion", actuacionDAO.getActuacion(id));
        return "actuacion/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("actuacion") Actuacion actuacion,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "actuacion/update";
        actuacionDAO.updateActuacion(actuacion);
        return "redirect:listActuaciones";
    }

    @RequestMapping(value = "/delete/{actuacionID}")
    public String processDelete(@PathVariable int actuacionID) {
        actuacionDAO.deleteActuacion(actuacionID);
        return "listActuaciones";
    }

    @RequestMapping(value="/listActuaciones/{nombre}/{idArtista}", method = RequestMethod.GET)
    public String listActuaciones(Model model, @PathVariable String nombre, @PathVariable int idArtista) {
        //necesito las actuaciones en las que el idArtista participa de este festival
        model.addAttribute("actuacionesArtista", actuacionDAO.getActuacionesArtistaFestival(nombre, idArtista));
        //actuaciones en las que podría actuar
        List<Actuacion> actuacionesPosibles = actuacionDAO.getPosiblesActuacionesArtistaFestival(nombre, idArtista);
        model.addAttribute("actuacionesPosibles", actuacionesPosibles);
        //otras actuaciones fuera del periodo de contrato del artista
        List<Actuacion> actuacionesNoPosibles =  actuacionDAO.getActuacionFestival(nombre);
        actuacionesNoPosibles.removeAll(actuacionesPosibles);
        model.addAttribute("actuacionesNoPosibles", actuacionesNoPosibles);

        model.addAttribute("festival", nombre);
        model.addAttribute("idArtista", idArtista);
        model.addAttribute("nombreArtista", actuacionDAO.getNombreArtista(idArtista));
        return "actuacion/listActuaciones";
    }

    //Obtener contrato vigente del artista

    @RequestMapping(value = "/contratoVigenteArtista/{idArtista}/{idActuacion}", method = RequestMethod.GET)
    public String editarAsignacion(@PathVariable int idArtista, @PathVariable int idActuacion) {
        Actuacion actuacion = actuacionDAO.getActuacion(idActuacion);
        Contrato contrato = actuacionDAO.getContratoVigenteArtista(idArtista, idActuacion);
        if (contrato != null){
            actuacion.setIdContrato(contrato.getId());
            actuacionDAO.updateActuacion(actuacion);
        }
        String nombre = actuacion.getNombreFestival();
        return "redirect:/actuacion/listActuaciones/" + nombre + "/" + idArtista;
    }

    @RequestMapping(value = "/cancelarContratoVigenteArtista/{idArtista}/{idActuacion}", method = RequestMethod.GET)
    public String cancelarAsignacion(@PathVariable int idArtista, @PathVariable int idActuacion) {
        Actuacion actuacion = actuacionDAO.getActuacion(idActuacion);
        actuacion.setIdContrato(null);
        actuacionDAO.updateActuacion(actuacion);
        String nombre = actuacion.getNombreFestival();
        return "redirect:/actuacion/listActuaciones/" + nombre + "/" + idArtista;
    }

}
