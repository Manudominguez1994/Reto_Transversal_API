package apirest.reto.model.dto;

import apirest.reto.model.entity.UsuarioPerfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idUsuarioPerfil")

public class UsuarioPerfilDto {

	private int idUsuarioPerfil;
	private int idPerfil;

	public static UsuarioPerfilDto convertirAUsuarioPerfilDto(UsuarioPerfil usuarioPerfil) {
		UsuarioPerfilDto usuarioPerfilDto = new UsuarioPerfilDto();

		usuarioPerfilDto.setIdUsuarioPerfil(usuarioPerfil.getIdUsuarioPerfil());
		usuarioPerfilDto.setIdPerfil(usuarioPerfil.getPerfil().getIdPerfil());

		return usuarioPerfilDto;
	}

}