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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apirest.reto.model.dto.PerfilDto;
import apirest.reto.model.entity.Perfil;
import apirest.reto.service.PerfilService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/perfiles")
public class PerfilRestController {
	
	@Autowired
	private PerfilService perfilService;
	
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
	public ResponseEntity<PerfilDto> insertOne(@RequestBody Perfil perfil){
		Perfil nuevoPerfil = perfilService.insertOne(perfil);
		
		if(nuevoPerfil == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(PerfilDto.convertirAPerfilDto(nuevoPerfil));
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<PerfilDto> updateOne(@RequestBody Perfil perfil){
		Perfil perfilActualizado = perfilService.updateOne(perfil);
		
		if(perfilActualizado == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(PerfilDto.convertirAPerfilDto(perfilActualizado));
		}
	}
	
	@DeleteMapping("/eliminar/{idPerfil}")
	public ResponseEntity<Void> deleteOne(@PathVariable int idPerfil){
		int resultado = perfilService.deleteById(idPerfil);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}