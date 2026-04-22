package apirest.reto.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.Perfil;
import apirest.reto.model.entity.Usuario;
import apirest.reto.model.entity.UsuarioPerfil;
import apirest.reto.repository.PerfilRepository;
import apirest.reto.repository.UsuarioPerfilRepository;
import apirest.reto.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario findById(String username) {
		return usuarioRepository.findById(username).orElse(null);
	}

	@Override
	public Usuario insertOne(Usuario usuario) {
		if(usuarioRepository.existsById(usuario.getUsername())) {
			return null;
		}else {
			return usuarioRepository.save(usuario);
		}
	}

	@Override
	public Usuario updateOne(Usuario usuario) {
		if(usuarioRepository.existsById(usuario.getUsername())) {
			return usuarioRepository.save(usuario);
		}else {
			return null;
		}
	}

	@Override
	public int deleteById(String username) {
		if(usuarioRepository.existsById(username)) {
			usuarioRepository.deleteById(username);
			return 1;
		}else {
			return 0;
		}
	}

	//"Buscar usuarios cuyo email contenga un texto específico"
	@Override
	public List<Usuario> buscarPorEmail(String texto) {
		return usuarioRepository.findByEmailContaining(texto);
	}

	//"usuarios que se registraron después de una fecha"
	@Override
	public List<Usuario> buscarRegistradosDespuesDe(LocalDate fechaRegistro) {
		return usuarioRepository.findByFechaRegistroAfter(fechaRegistro);
	}

	//"usuarios que hayan realizado alguna reserva"
	@Override
	public List<Usuario> buscarUsuariosConAlgunaReserva() {
		return usuarioRepository.buscarUsuariosConAlgunaReserva();
	}

	@Override
	public List<Usuario> buscarUsuariosConMasDeNReservas(int n) {
		return usuarioRepository.buscarUsuariosConMasDeNReservas(n);
	}

	@Override
	public Usuario registrar(Usuario usuario) {

		if (usuarioRepository.existsById(usuario.getUsername())) {
			return null;
		}

		usuario.setFechaRegistro(LocalDate.now());
		usuario.setEnabled(1);
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

		Usuario guardado = usuarioRepository.save(usuario);

		// le asignamos el perfil ROLE_CLIENTE (id = 2)
		Perfil perfilCliente = perfilRepository.findById(2).orElse(null);

		if (perfilCliente != null) {
			UsuarioPerfil up = new UsuarioPerfil();
			up.setUsuario(guardado);
			up.setPerfil(perfilCliente);
			usuarioPerfilRepository.save(up);
		}

		return guardado;
	}
	
}
