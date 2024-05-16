package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Promotor;
import es.uji.ei1027.totfest.Model.UsuarioRegistrado;
import es.uji.ei1027.totfest.RowMappersAndDAO.PromotorDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/promotor")
public class PromotorController {
    private PromotorDAO promotorDAO;
    private JdbcTemplate jdbcTemplate;
    private int pageLength = 3;//Paginación

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setPromotorDAO(PromotorDAO promotorDAO) {
        this.promotorDAO = promotorDAO;
    }

    @RequestMapping("/list")
    public String listPromotor(Model model) {
        model.addAttribute("promotores", promotorDAO.getPromotor());
        return "promotor/list";
    }

    //Inicio
    @RequestMapping("/inicioManager")
    public String inicioPromotor(Model model, HttpSession session) {
        Promotor promotor = (Promotor) session.getAttribute("user");
        model.addAttribute("promotor", promotor.getNombre());
        return "promotor/inicioManager";
    }

    @RequestMapping(value = "/add")
    public String addPromotor(Model model) {
        model.addAttribute("promotor", new Promotor());
        List<String> tipoOrganizacionList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_organizacion))", String.class);
        model.addAttribute("tipoOrganizacionList", tipoOrganizacionList);
        return "promotor/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("promotor") Promotor promotor,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "promotor/add";
        promotorDAO.addPromotor(promotor);
        return "redirect:pagedlist";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editarPromotor(Model model, @PathVariable String usuario) {
        model.addAttribute("promotor", promotorDAO.getPromotor(usuario));
        List<String> tipoOrganizacionList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_organizacion))", String.class);
        model.addAttribute("tipoOrganizacionList", tipoOrganizacionList);
        return "promotor/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("promotor") Promotor promotor,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "promotor/update";
        promotorDAO.updatePromotor(promotor);
        return "redirect:pagedlist";
    }

    @RequestMapping(value = "/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        promotorDAO.deletePromotor(usuario);
        return "redirect:../pagedlist";
    }
    //Paginación
    @RequestMapping("/pagedlist")
    public String listPromotorPaged(Model model,
                                    @RequestParam("page") Optional<Integer> page) {
        // Paso 1: Crear la lista paginada de promotores
        List<Promotor> promotor = promotorDAO.getPromotor();
        // Ordenar

        ArrayList<ArrayList<Promotor>> promPaged = new ArrayList<>();
        int ini = 0;
        int fin = pageLength;
        while (fin < promotor.size()) {
            promPaged.add(new ArrayList<>(promotor.subList(ini, fin)));
            ini += pageLength;
            fin += pageLength;
        }
        promPaged.add(new ArrayList<>(promotor.subList(ini, promotor.size())));
        model.addAttribute("promotorPaged", promPaged);

        // Paso 2: Crear la lista de numeros de pagina
        int totalPages = promPaged.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        // Paso 3: selectedPage: usar parametro opcional page, o en su defecto, 1
        int currentPage = page.orElse(0);
        model.addAttribute("selectedPage", currentPage);
        return "promotor/pagedlist";
    }


}
