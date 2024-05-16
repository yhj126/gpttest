package es.uji.ei1027.totfest.controlador;

import es.uji.ei1027.totfest.Model.Entrada;
import es.uji.ei1027.totfest.Model.Pedido;
import es.uji.ei1027.totfest.Model.UsuarioRegistrado;
import es.uji.ei1027.totfest.RowMappersAndDAO.EntradaDAO;
import es.uji.ei1027.totfest.RowMappersAndDAO.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Controller
@RequestMapping("/pedido")
public class PedidoController {
    private PedidoDAO pedidoDAO;

    @Autowired
    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    @RequestMapping("/list")
    public String listPedido(Model model) {
        model.addAttribute("pedidos", pedidoDAO.getPedido());
        return "listTipo";
    }

    @RequestMapping(value = "/add")
    public String addPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "pedido/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("pedido") Pedido pedido,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "pedido/add";
        pedidoDAO.addPedido(pedido);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editarPedido(Model model, @PathVariable int id) {
        model.addAttribute("pedido", pedidoDAO.getPedido(id));
        return "pedido/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("pedido") Pedido pedido,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "pedido/update";
        pedidoDAO.updatePedido(pedido);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{pedidoID}")
    public String processDelete(@PathVariable int pedidoID) {
        pedidoDAO.deletePedido(pedidoID);
        return "redirect:../list";
    }

    //Comprar
    private JdbcTemplate jdbcTemplate;
    private EntradaDAO entradaDAO;
    private Entrada nuevaEntrada = new Entrada();
    private UsuarioRegistrado user;
    private String nombreFest;

    @Autowired
    public void setPedidoDAO(EntradaDAO entradaDAO) {
        this.entradaDAO = entradaDAO;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/comprar/{tipo}/{nombreFestival}/{precio}")
    public String addCompra(Model model, @PathVariable String tipo, @PathVariable String nombreFestival, @PathVariable String precio, HttpSession session) {
        Pedido pedido = new Pedido();
        user = (UsuarioRegistrado) session.getAttribute("user");
        if (user != null) {
            pedido.setCorreo(user.getCorreo());
        }
        nuevaEntrada.setNombreFestival(nombreFestival);
        nuevaEntrada.setTipo(tipo);
        nuevaEntrada.setPrecio(Float.parseFloat(precio));
        nombreFest = nombreFestival;
        //Precio mostrar dinamico
        model.addAttribute("precio", precio);
        model.addAttribute("pedido", pedido);

        return "pedido/comprar";
    }

    @RequestMapping(value = "/comprar", method = RequestMethod.POST)
    public String processCompraSubmit(@ModelAttribute("pedido") Pedido pedido,
                                      BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "pedido/comprar";
        }
        if (user != null) {
            pedido.setIdUsuario(user.getId());
            pedido.setCorreo(user.getCorreo());
        } else
            pedido.setIdUsuario(null);
        pedido.setFecha(pedidoDAO.getFechaActual());
        pedidoDAO.addPedido(pedido);
        nuevaEntrada.setIdPedido(pedidoDAO.getIdPedido(pedido));
        ArrayList<Entrada> entradas = new ArrayList<>();

        for (int i = 0; i < pedido.getCantidadEntradas(); i++) {
            entradaDAO.addEntrada(nuevaEntrada);
        }
        entradas.addAll(entradaDAO.getEntradas(nuevaEntrada));
        model.addAttribute("precioTotal", String.format("%.2f", entradas.get(0).getPrecio()*pedido.getCantidadEntradas()));
        model.addAttribute("entradas", entradas);
        model.addAttribute("pedido", pedido);
        model.addAttribute("festivalDatos", this.nombreFest);
        return "pedido/confirmacionPedidoTEST";
    }
}
