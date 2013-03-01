package pandox.egito.dao;

import org.springframework.stereotype.Repository;

import pandox.egito.model.Usuario;

@Repository
public class UsuarioDAOImpl extends GenericDAO<Usuario, Long> implements UsuarioDAO<Usuario, Long> {

}