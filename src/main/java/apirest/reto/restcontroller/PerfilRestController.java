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
import apirest.reto.model.dto.PerfilDto;
import apirest.reto.model.entity.Perfil;
import apirest.reto.service.PerfilService;

@RestController
@RequestMapping("/perfiles")
public class PerfilRestController {
	
	@Autowired
	private PerfilService perfilService;

	@Autowired
	private JwtUtil jwtUtil;

	private boolean esAdmin(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
		String token = authHeader.substring(7);
		if (!jwtUtil.esTokenValido(token)) return false;
		return "ROLE_ADMON".equals(jwtUtil.obtenerRol(token));
	}

	@GetMapping("")
	public ResponseEntity<List<PerfilDto>> findAll() {
		List<PerfilDto> perfiles = perfilService.findAll()
				.stream()
				.map(PerfilDto::convertirAPerfilDto)
				.toList();

		return ResponseEntity.ok(perfiles);
	}

	@GetMapping("/{idPerfil}")
	public ResponseEntity<PerfilDto> findById(@PathVariable int idPerfil){
		Perfil perfil = perfilService.findById(idPerfil);
		if(perfil == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(PerfilDto.convertirAPerfilDto(perfil));
		}
	}
	
	@PostMapping("/alta")
	public ResponseEntity<?> insertOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestBody Perfil perfil){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Perfil nuevoPerfil = perfilService.insertOne(perfil);
		
		if(nuevoPerfil == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(PerfilDto.convertirAPerfilDto(nuevoPerfil));
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> updateOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestBody Perfil perfil){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Perfil perfilActualizado = perfilService.updateOne(perfil);
		
		if(perfilActualizado == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(PerfilDto.convertirAPerfilDto(perfilActualizado));
		}
	}
	
	@DeleteMapping("/eliminar/{idPerfil}")
	public ResponseEntity<?> deleteOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable int idPerfil){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		int resultado = perfilService.deleteById(idPerfil);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
