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
import apirest.reto.model.dto.TipoDto;
import apirest.reto.model.entity.Tipo;
import apirest.reto.service.TipoService;

@RestController
@RequestMapping("/tipos")
public class TipoRestController{
	
	@Autowired
	private TipoService tipoService;

	@Autowired
	private JwtUtil jwtUtil;

	private boolean esAdmin(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
		String token = authHeader.substring(7);
		if (!jwtUtil.esTokenValido(token)) return false;
		return "ROLE_ADMON".equals(jwtUtil.obtenerRol(token));
	}

	@GetMapping("")
	public ResponseEntity<List<TipoDto>> findAll() {
		List<TipoDto> tipos = tipoService.findAll()
				.stream()
				.map(TipoDto::convertirATipoDto)
				.toList();

		return ResponseEntity.ok(tipos);
	}
	
	@GetMapping("/{idTipo}")
	public ResponseEntity<TipoDto> findById(@PathVariable int idTipo){
		Tipo tipo = tipoService.findById(idTipo);
		
		if(tipo == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(TipoDto.convertirATipoDto(tipo));
		}
	}
	
	@PostMapping("/alta")
	public ResponseEntity<?> insertOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestBody Tipo tipo){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Tipo nuevoTipo = tipoService.insertOne(tipo);
		return ResponseEntity.status(HttpStatus.CREATED).body(TipoDto.convertirATipoDto(nuevoTipo));
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> updateOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestBody Tipo tipo){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Tipo tipoActualizado = tipoService.updateOne(tipo);
		
		if(tipoActualizado == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(TipoDto.convertirATipoDto(tipoActualizado));
		}
	}
	
	@DeleteMapping("/eliminar/{idTipo}")
	public ResponseEntity<?> deleteById(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable int idTipo){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		int resultado = tipoService.deleteById(idTipo);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
