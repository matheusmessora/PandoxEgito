package pandox.egito.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pandox.egito.model.Usuario;
import pandox.egito.service.UsuarioService;



@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController extends BaseController {
    
    private static Logger log = Logger.getLogger(UsuarioController.class);
    
    private Usuario usuario;

     @Autowired
     private UsuarioService usuarioService;
    //
    // @Autowired
    // private UserUtil userUtil;
    
    public UsuarioController() {
        log.info("INICIANDO USUARIO CONTROLLER");
        usuario = new Usuario();
        usuario.setNome("MMM");
        
        log.info("MMMMMM: " + usuario.getNome());
    }

    @RequestMapping(value = "")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
   	 Usuario user = usuarioService.find();
   	 log.info("USER: " + user.getNome() + " || " + user);
   	 return null;
    }
}
