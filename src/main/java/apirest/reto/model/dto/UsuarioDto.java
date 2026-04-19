package apirest.reto.model.dto;

import java.time.LocalDate;

import apirest.reto.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class UsuarioDto {

	private String email;
	private String nombre;
	private String apellidos;
	private String direccion;
	private LocalDate fechaRegistro;

	public static UsuarioDto convertirAUsuarioDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();

		usuarioDto.setEmail(usuario.getEmail());
		usuarioDto.setNombre(usuario.getNombre());
		usuarioDto.setApellidos(usuario.getApellidos());
		usuarioDto.setDireccion(usuario.getDireccion());
		usuarioDto.setFechaRegistro(usuario.getFechaRegistro());

		return usuarioDto;
	}

}