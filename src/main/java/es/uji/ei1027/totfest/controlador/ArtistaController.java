package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Artista;
import es.uji.ei1027.totfest.RowMappersAndDAO.ArtistaDAO;
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
@RequestMapping("/artista")
public class

ArtistaController {
    private ArtistaDAO artistaDAO;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setArtistaDAO(ArtistaDAO artistaDAO) {
        this.artistaDAO = artistaDAO;
    }

    @RequestMapping("/list")
    public String listArtistas(Model model) {
        model.addAttribute("artistas", artistaDAO.getArtista());
        return "artista/list";
    }

    @RequestMapping(value = "/add")
    public String addArtista(Model model) {
        model.addAttribute("artista", new Artista());
        List<String> tipoMusicaList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_musica))", String.class);
        model.addAttribute("tipoMusicaList", tipoMusicaList);
        return "artista/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("artista") Artista artista,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "artista/add";
        artistaDAO.addArtista(artista);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editarArtista(Model model, @PathVariable int id) {
        model.addAttribute("artista", artistaDAO.getArtista(id));
        List<String> tipoMusicaList = jdbcTemplate.queryForList("SELECT unnest(enum_range(NULL::tipo_musica))", String.class);
        model.addAttribute("tipoMusicaList", tipoMusicaList);
        return "artista/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("artista") Artista artista,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "artista/update";
        artistaDAO.updateArtista(artista);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{artistaID}")
    public String processDelete(@PathVariable int artistaID) {
        artistaDAO.deleteArtista(artistaID);
        return "redirect:../list";
    }


}

