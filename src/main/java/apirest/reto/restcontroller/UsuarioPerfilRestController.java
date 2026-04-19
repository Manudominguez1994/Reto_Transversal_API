package apirest.reto.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apirest.reto.config.JwtUtil;
import apirest.reto.model.dto.UsuarioPerfilDto;
import apirest.reto.model.entity.UsuarioPerfil;
import apirest.reto.service.UsuarioPerfilService;

@RestController
@RequestMapping("/usuario-perfiles")
public class UsuarioPerfilRestController {
	
	@Autowired
	private UsuarioPerfilService upService;

	@Autowired
	private JwtUtil jwtUtil;

	private boolean esAdmin(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
		String token = authHeader.substring(7);
		if (!jwtUtil.esTokenValido(token)) return false;
		return "ROLE_ADMON".equals(jwtUtil.obtenerRol(token));
	}

	@GetMapping("")
	public ResponseEntity<List<UsuarioPerfilDto>> findAll() {
		List<UsuarioPerfilDto> usuarioPerfiles = upService.findAll()
				.stream()
				.map(UsuarioPerfilDto::convertirAUsuarioPerfilDto)
				.toList();

		return ResponseEntity.ok(usuarioPerfiles);
	}
	
	@GetMapping("/{idUsuarioPerfil}")
	public ResponseEntity<UsuarioPerfilDto> findById(@PathVariable int idUsuarioPerfil){
		UsuarioPerfil usuarioPerfil = upService.findById(idUsuarioPerfil);
		
		if(usuarioPerfil == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(UsuarioPerfilDto.convertirAUsuarioPerfilDto(usuarioPerfil));
		}
	}
	
	@PostMapping("/alta")
	public ResponseEntity<?> insertOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestBody UsuarioPerfil usuarioPerfil){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		UsuarioPerfil nuevoUP = upService.insertOne(usuarioPerfil);
		
		if(nuevoUP == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioPerfilDto.convertirAUsuarioPerfilDto(nuevoUP));
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> updateOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestBody UsuarioPerfil usuarioPerfil){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		UsuarioPerfil upActualizado = upService.updateOne(usuarioPerfil);
		
		if(upActualizado == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(UsuarioPerfilDto.convertirAUsuarioPerfilDto(upActualizado));
		}
	}
	
	@DeleteMapping("/eliminar/{idUsuarioPerfil}")
	public ResponseEntity<?> deleteById(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable int idUsuarioPerfil){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		int resultado = upService.deleteById(idUsuarioPerfil);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}	
	}
	
}
