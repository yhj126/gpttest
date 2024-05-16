package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Festival;

import es.uji.ei1027.totfest.Model.Promotor;
import es.uji.ei1027.totfest.RowMappersAndDAO.FestivalDAO;
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
@RequestMapping("/festival")
public class FestivalController {
        private FestivalDAO festivalDAO;
        private JdbcTemplate jdbcTemplate;


        @Autowired
        public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


        @Autowired
        public void setFestivalDAO(FestivalDAO festivalDAO) {
            this.festivalDAO = festivalDAO;
        }

       /* @RequestMapping("/list")
        public String listFestivales(Model model) {
            model.addAttribute("festivales", festivalDAO.getFestival());
            return "festival/list";
        }*/

        @RequestMapping("/listMisFestivales")
        public String listMisFestivales(Model model, HttpSession session) {
            Promotor promotor = (Promotor) session.getAttribute("user");
            model.addAttribute("festivales", festivalDAO.getMisFestival(promotor.getUsername()));
            return "festival/listMisFestivales";
        }

        @RequestMapping("/listGestor/{id}")
        public String listFestivales(Model model, @PathVariable int id) {
            model.addAttribute("festivales", festivalDAO.getFestival());
            model.addAttribute("idArtista", id);
            return "/festival/listGestor";
        }
        @RequestMapping(value ="/listGestor", method = RequestMethod.POST)
        public String processListSubmit(Model model, @PathVariable int id) {
            model.addAttribute("festivales", festivalDAO.getFestival());
            model.addAttribute("idArtista", id);
            return "/festival/listGestor";
        }

        @RequestMapping(value = "/add")
        public String addFestival(Model model) {
            model.addAttribute("festival", new Festival());
            List<String> tipoFestivalList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_festival))", String.class);
            model.addAttribute("tipoFestivalList", tipoFestivalList);
            List<String> tipoAudenciaList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_audiencia))", String.class);
            model.addAttribute("tipoAudienciaList", tipoAudenciaList);
            return "/festival/add";
        }

        Promotor promotor;
        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public String processAddSubmit(@ModelAttribute("festival") Festival festival,
                                       BindingResult bindingResult, HttpSession session) {
            if (bindingResult.hasErrors()) {
                System.out.println("Error");
                return "festival/add";
            }
            promotor = (Promotor) session.getAttribute("user");
            festival.setPromotor(promotor.getUsername());
            festivalDAO.addFestival(festival);
            return "redirect:listMisFestivales";
        }

        @RequestMapping(value = "/update/{nombre}", method = RequestMethod.GET)
        public String editarFestival(Model model, @PathVariable String nombre) {
            model.addAttribute("festival", festivalDAO.getFestival(nombre));
            List<String> tipoFestivalList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_festival))", String.class);
            model.addAttribute("tipoFestivalList", tipoFestivalList);
            List<String> tipoAudenciaList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_audiencia))", String.class);
            model.addAttribute("tipoAudienciaList", tipoAudenciaList);
            return "festival/update";
        }

        @RequestMapping(value = "/update", method = RequestMethod.POST)
        public String processUpdateSubmit(
                @ModelAttribute("festival") Festival festival,
                BindingResult bindingResult, HttpSession session) {
            if (bindingResult.hasErrors())
                return "festival/update";
            promotor = (Promotor) session.getAttribute("user");
            festival.setPromotor(promotor.getUsername());
            festivalDAO.updateFestival(festival);
            return "redirect:listMisFestivales";
        }

        @RequestMapping(value = "/delete/{nombre}")
        public String processDelete(@PathVariable String nombre) {
            festivalDAO.deleteFestival(nombre);
            return "redirect:../listMisFestivales";
        }


        //Lista festivales a la venta
        @RequestMapping("/listVenta")
        public String listFestivalesAVenta(Model model) {
            model.addAttribute("festivales", festivalDAO.getFestivalAVenta());
            model.addAttribute("festival", new Festival());
            return "festival/listVenta";
        }

        //Lista festivales no a la venta
        @RequestMapping("/listNoVenta")
        public String listFestivalesNoVenta(Model model) {
            model.addAttribute("festivales", festivalDAO.getFestivalNoVenta());
            model.addAttribute("festival", new Festival());
            return "festival/listNoVenta";
        }

        //Más info. venta
        @RequestMapping(value = "/masInfoVenta/{nombre}")
        public String masInfoVenta(@PathVariable String nombre, Model model) {
            model.addAttribute("festival",
                    festivalDAO.getFestival(nombre));
            return "/festival/masInfoVenta";
        }

        //Más info. venta
        @RequestMapping(value = "/masInfoNoVenta/{nombre}")
        public String masInfoNoVenta(@PathVariable String nombre, Model model) {
            model.addAttribute("festival",
                    festivalDAO.getFestival(nombre));
            return "/festival/masInfoNoVenta";
        }

        //Buscar festival
        @RequestMapping(value = "/listVenta/{nombre}",  method = RequestMethod.GET)
        public String processBusquedaVenta(@PathVariable String nombre, BindingResult bindingResult, Model model) {

            if (bindingResult.hasErrors())
                return "festival/listVenta";
            System.out.println("Hola");
            model.addAttribute("festivalesFiltrados", festivalDAO.getFestivalEnVenta(nombre));
            return "redirect:/festival/listVenta";
        }

        @RequestMapping(value = "/listNoVenta/{nombre}",  method = RequestMethod.GET)
        public String processBusquedaNoVenta(@PathVariable String nombre, BindingResult bindingResult, Model model) {
            if (bindingResult.hasErrors())
                return "festival/listNoVenta";
            model.addAttribute("festivales",
                    festivalDAO.getFestivalNoVenta(nombre));
            return "redirect:/listNoVenta";
        }
        final private int pageLength= 5;

        //Paginación
        @RequestMapping("/listVentaPaginada")
        public String listFestivalPaged(Model model,
                                        @RequestParam("page") Optional<Integer> page) {

            // Paso 1: Crear la lista paginada de promotores
            List<Festival> festival = festivalDAO.getFestivalAVenta();


            ArrayList<ArrayList<Festival>> promPaged = new ArrayList<>();
            int ini = 0;

            int fin = pageLength;
            while (fin < festival.size()) {
                promPaged.add(new ArrayList<>(festival.subList(ini, fin)));
                ini += pageLength;
                fin += pageLength;
            }
            promPaged.add(new ArrayList<>(festival.subList(ini, festival.size())));
            model.addAttribute("festivalPaged", promPaged);

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
            return "festival/listVentaPaginada";
        }
    //Paginación
    @RequestMapping("/list")
    public String listFestivalPagedPro(Model model,
                                    @RequestParam("page") Optional<Integer> page) {

        // Paso 1: Crear la lista paginada de promotores
        List<Festival> festival = festivalDAO.getFestival();


        ArrayList<ArrayList<Festival>> promPaged = new ArrayList<>();
        int ini = 0;

        int fin = pageLength;
        while (fin < festival.size()) {
            promPaged.add(new ArrayList<>(festival.subList(ini, fin)));
            ini += pageLength;
            fin += pageLength;
        }
        promPaged.add(new ArrayList<>(festival.subList(ini, festival.size())));
        model.addAttribute("todoFestivalPaged", promPaged);

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
        return "festival/list";
    }



}
