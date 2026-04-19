package apirest.reto.service;

import java.time.LocalDate;
import java.util.List;
import apirest.reto.model.entity.Usuario;

public interface UsuarioService {
	
	List<Usuario> findAll();
	
	Usuario findById(String username);
	
	Usuario insertOne(Usuario usuario);
	
	Usuario updateOne(Usuario usuario);
	
	int deleteById(String username);
	
	//“Buscar usuarios cuyo email contenga un texto específico”
	List<Usuario> buscarPorEmail(String texto);
	
	//“usuarios que se registraron después de una fecha”
	List<Usuario> buscarRegistradosDespuesDe(LocalDate fechaRegistro);
	
	//“usuarios que hayan realizado alguna reserva”
	List<Usuario> buscarUsuariosConAlgunaReserva();
	
	//usuarios con más de N reservas.
	List<Usuario> buscarUsuariosConMasDeNReservas(int n);

}
