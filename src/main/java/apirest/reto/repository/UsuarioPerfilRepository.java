package apirest.reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import apirest.reto.model.entity.UsuarioPerfil;

public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Integer>{

	UsuarioPerfil findFirstByUsuario_Username(String username);

}
