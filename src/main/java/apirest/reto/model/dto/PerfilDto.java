package apirest.reto.model.dto;

import apirest.reto.model.entity.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idPerfil")

public class PerfilDto {

	private int idPerfil;
	private String nombre;

	public static PerfilDto convertirAPerfilDto(Perfil perfil) {
		PerfilDto perfilDto = new PerfilDto();

		perfilDto.setIdPerfil(perfil.getIdPerfil());
		perfilDto.setNombre(perfil.getNombre());

		return perfilDto;
	}

}