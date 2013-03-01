package pandox.egito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pandox.egito.dao.UsuarioDAO;
import pandox.egito.model.Usuario;


@Service
public class UsuarioService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private UsuarioDAO dao;

	@SuppressWarnings("unchecked")
	public Usuario find() {
      Usuario usuario = new Usuario();
      usuario.setNome("MMM");
      usuario.setEmail("mmm");
      usuario.setSenha("aaa");
      dao.save(usuario);
		
		Usuario user = (Usuario) dao.findAll(Usuario.class).get(0);
		System.out.println(user);
		return user;
	}
}
