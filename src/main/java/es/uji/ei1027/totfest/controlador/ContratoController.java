package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Actuacion;
import es.uji.ei1027.totfest.Model.Contrato;
import es.uji.ei1027.totfest.RowMappersAndDAO.ActuacionDAO;
import es.uji.ei1027.totfest.RowMappersAndDAO.ContratoDAO;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/contrato")
public class ContratoController {
    private ContratoDAO contratoDAO;
    private int idArtista;

    @Autowired
    public void setContratoDAO(ContratoDAO contratoDAO) {
        this.contratoDAO = contratoDAO;
    }

    @RequestMapping("/list")
    public String listContratos(Model model) {
        model.addAttribute("contratos", contratoDAO.getContrato());
        return "contrato/list";
    }

    @RequestMapping(value = "/add")
    public String addContrato(Model model) {
        model.addAttribute("contrato", new Contrato());
        return "contrato/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contrato") Contrato contrato,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "contrato/add";
        contratoDAO.addContrato(contrato);
        return "redirect:list";
    }

    @RequestMapping(value = "/addAArtista/{idArtista}")
    public String addContratoArtista(Model model,  @PathVariable int idArtista) {
        Contrato contrato = new Contrato();
        this.idArtista = idArtista;
        model.addAttribute("contrato", contrato);
        return "contrato/addAArtista";
    }

    @RequestMapping(value = "/addAArtista", method = RequestMethod.POST)
    public String processAddArtistaSubmit(@ModelAttribute("contrato") Contrato contrato,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "contrato/addAArtista";
        contrato.setIdArtista(this.idArtista);
        contratoDAO.addContrato(contrato);
        return "redirect:../contrato/porArtista/" + contrato.getIdArtista();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editarContrato(Model model, @PathVariable int id) {
        model.addAttribute("contrato", contratoDAO.getContrato(id));
        return "contrato/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("contrato") Contrato contrato,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "contrato/update";
        contratoDAO.updateContrato(contrato);
        return "redirect:../contrato/porArtista/" + contrato.getIdArtista();
    }

    @RequestMapping(value = "/delete/{contratoID}")
    public String processDelete(@PathVariable int contratoID) {
        Contrato contrato = contratoDAO.getContrato(contratoID);
        contratoDAO.deleteContrato(contratoID);
        return "redirect:../porArtista/" + contrato.getIdArtista();
    }

    @RequestMapping(value = "/porArtista/{idArtista}", method = RequestMethod.GET)
    public String listContratosPorArtista(Model model, @PathVariable int idArtista) {
        model.addAttribute("contratosartista",
               contratoDAO.getContratosArtista(idArtista));
        model.addAttribute("idArtista", idArtista);
        return "contrato/porArtista";
    }

}
