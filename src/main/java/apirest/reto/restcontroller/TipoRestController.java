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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apirest.reto.model.dto.TipoDto;
import apirest.reto.model.entity.Tipo;
import apirest.reto.service.TipoService;

@RestController
@RequestMapping("/tipos")
public class TipoRestController{
	
	@Autowired
	private TipoService tipoService;
	
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
	public ResponseEntity<TipoDto> insertOne(@RequestBody Tipo tipo){
		Tipo nuevoTipo = tipoService.insertOne(tipo);
		return ResponseEntity.status(HttpStatus.CREATED).body(TipoDto.convertirATipoDto(nuevoTipo));
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<TipoDto> updateOne(@RequestBody Tipo tipo){
		Tipo tipoActualizado = tipoService.updateOne(tipo);
		
		if(tipoActualizado == null) {
			return ResponseEntity.notFound().build(); // el recurso no existe
		}else {
			return ResponseEntity.ok(TipoDto.convertirATipoDto(tipoActualizado));
		}
	}
	
	@DeleteMapping("/eliminar/{idTipo}")
	public ResponseEntity<Void> deleteById(@PathVariable int idTipo){
		int resultado = tipoService.deleteById(idTipo);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build(); // ha salido bien y no hay que devolver nada
		}else {
			return ResponseEntity.notFound().build(); // el recurso no existe
		}
		
	}
	
	
}