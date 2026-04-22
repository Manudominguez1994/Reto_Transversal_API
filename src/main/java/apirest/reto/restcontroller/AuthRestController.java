package apirest.reto.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apirest.reto.config.JwtUtil;
import apirest.reto.model.dto.LoginDto;
import apirest.reto.model.dto.LoginResponseDto;
import apirest.reto.model.entity.Usuario;
import apirest.reto.model.entity.UsuarioPerfil;
import apirest.reto.repository.UsuarioPerfilRepository;
import apirest.reto.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticación", description = "Login y obtención de token JWT")
@RestController
@RequestMapping("/auth")
public class AuthRestController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioPerfilRepository upRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Operation(summary = "Iniciar sesión", description = "Devuelve un token JWT si el username y password son correctos")
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {

		Usuario usuario = usuarioRepository.findById(loginDto.getUsername()).orElse(null);

		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (!passwordEncoder.matches(loginDto.getPassword(), usuario.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (usuario.getEnabled() == 0) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		UsuarioPerfil up = upRepository.findFirstByUsuario_Username(usuario.getUsername());
		String rol = up != null ? up.getPerfil().getNombre() : "ROLE_CLIENTE";

		String token = jwtUtil.generarToken(usuario.getUsername(), rol);

		return ResponseEntity.ok(new LoginResponseDto(token, usuario.getUsername(), rol));
	}

}
