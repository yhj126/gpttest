package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.TipoEntrada;
import es.uji.ei1027.totfest.RowMappersAndDAO.TipoEntradaDAO;
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
@RequestMapping("/tipoEntrada")
public class TipoEntradaController {
    private TipoEntradaDAO tipoEntradaDAO;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource);    }

    @Autowired
    public void setTipoEntradaDAO(TipoEntradaDAO tipoEntradaDAO) {
        this.tipoEntradaDAO = tipoEntradaDAO;
    }

    @RequestMapping("/list")
    public String listTipoEntrada(Model model) {
        model.addAttribute("tipoEntradas", tipoEntradaDAO.getTipoEntrada());
        return "tipoEntrada/list";
    }

    private String nombreFestival;
    @RequestMapping(value = "/add/{nombreFestival}")
    public String addTipoEntrada(Model model, @PathVariable String nombreFestival) {
        this.nombreFestival= nombreFestival;
        model.addAttribute("tipoEntrada", new TipoEntrada());
        List<String> tipoEntradaList=jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_entrada))", String.class);
        model.addAttribute("tipoEntradaList", tipoEntradaList);
        List<String> tipoDuracionList=jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_duracion))", String.class);
        model.addAttribute("tipoDuracionList", tipoDuracionList);
        model.addAttribute("festival", nombreFestival);
        return "tipoEntrada/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("tipoEntrada") TipoEntrada tipoEntrada,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tipoEntrada/add";
        tipoEntrada.setNombreFestival(nombreFestival);
        tipoEntrada.setDia(tipoEntradaDAO.getFechaActual());
        tipoEntradaDAO.addTipoEntrada(tipoEntrada);
        return "redirect:listPromotor";
    }

    @RequestMapping(value = "/update/{tipo}/{nombreFestival}", method = RequestMethod.GET)
    public String editarTipoEntrada(Model model, @PathVariable String tipo, @PathVariable String nombreFestival) {
        model.addAttribute("tipoEntrada", tipoEntradaDAO.getTipoEntrada(tipo, nombreFestival));
        List<String> tipoEntradaList=jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_entrada))", String.class);
        model.addAttribute("tipoEntradaList", tipoEntradaList);
        List<String> tipoDuracionList=jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_duracion))", String.class);
        model.addAttribute("tipoDuracionList", tipoDuracionList);
        return "tipoEntrada/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("tipoEntrada") TipoEntrada tipoEntrada,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tipoEntrada/update";
        tipoEntradaDAO.updateTipoEntrada(tipoEntrada);
        return "redirect:listPromotor";
    }

    @RequestMapping(value = "/delete/{tipo}/{nombreFestival}")
    public String processDelete(@PathVariable String tipo, @PathVariable String nombreFestival) {
        tipoEntradaDAO.deleteTipoEntrada(tipo, nombreFestival);
        return "redirect:../../listPromotor";
    }

    //Compra
    @RequestMapping("/list/{nombreFestival}")
    public String listTipoEntrada(Model model, @PathVariable String nombreFestival) {
        model.addAttribute("tipoEntradas", tipoEntradaDAO.getTipoEntradaFest(nombreFestival));
        return "tipoEntrada/list";
    }

    //List por festival
    @RequestMapping("/listPromotor/{nombreFestival}")
    public String listTipoEntradaFestival(Model model, @PathVariable String nombreFestival) {
        model.addAttribute("tipoEntradas", tipoEntradaDAO.getTipoEntradaFest(nombreFestival));
        model.addAttribute("festival", nombreFestival);
        return "tipoEntrada/listPromotor";
    }
}
