package apirest.reto.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apirest.reto.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
	//“Buscar usuarios cuyo email contenga un texto específico”
	List<Usuario> findByEmailContaining(String texto);
	
	//“usuarios que se registraron después de una fecha”
	List<Usuario> findByFechaRegistroAfter(LocalDate fechaRegistro);
	
	//“usuarios que hayan realizado alguna reserva”
	@Query("select distinct r.usuario from Reserva r")
	List<Usuario> buscarUsuariosConAlgunaReserva();
	
	//usuarios con más de N reservas.
	@Query("select r.usuario from Reserva r group by r.usuario having count(r) > ?1")
	List<Usuario> buscarUsuariosConMasDeNReservas(int n);
	
}
