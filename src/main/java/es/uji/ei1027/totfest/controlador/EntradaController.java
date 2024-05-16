package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Entrada;
import es.uji.ei1027.totfest.RowMappersAndDAO.EntradaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;
import java.util.List;

@Controller
@RequestMapping("/entrada")
public class EntradaController {
    private EntradaDAO entradaDAO;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setEntradaDAO(EntradaDAO entradaDAO) {
        this.entradaDAO = entradaDAO;
    }

    @RequestMapping("/list")
    public String listEntrada(Model model) {
        model.addAttribute("entradas", entradaDAO.getEntrada());
        return "entrada/list";
    }

    @RequestMapping(value = "/add")
    public String addEntrada(Model model) {
        model.addAttribute("entrada", new Entrada());
        List<String> tipoEntradaList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_entrada))", String.class);
        model.addAttribute("tipoEntradaList", tipoEntradaList);
        return "entrada/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("entrada") Entrada entrada,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "entrada/add";
        entradaDAO.addEntrada(entrada);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{numero}", method = RequestMethod.GET)
    public String editarEntrada(Model model, @PathVariable int numero) {
        model.addAttribute("entrada", entradaDAO.getEntrada(numero));
        List<String> tipoEntradaList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_entrada))", String.class);
        model.addAttribute("tipoEntradaList", tipoEntradaList);
        return "entrada/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("entrada") Entrada entrada,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "entrada/update";
        entradaDAO.updateEntrada(entrada);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numero}")
    public String processDelete(@PathVariable int numero) {
        entradaDAO.deleteEntrada(numero);
        return "redirect:../list";
    }

}
