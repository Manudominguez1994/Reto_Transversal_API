package apirest.reto.model.dto;

import apirest.reto.model.entity.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idTipo")

public class TipoDto {

	private int idTipo;
	private String nombre;
	private String descripcion;

	public static TipoDto convertirATipoDto(Tipo tipo) {
		TipoDto tipoDto = new TipoDto();

		tipoDto.setIdTipo(tipo.getIdTipo());
		tipoDto.setNombre(tipo.getNombre());
		tipoDto.setDescripcion(tipo.getDescripcion());

		return tipoDto;
	}

}