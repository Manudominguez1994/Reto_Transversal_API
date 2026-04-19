package apirest.reto.model.dto;

import java.time.LocalDate;
import apirest.reto.model.entity.Evento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idEvento")

public class EventoDto {

	private int idEvento;
	private int idTipo;
	private String nombre;
	private String descripcion;
	private LocalDate fechaInicio;
	private int duracion;
	private String direccion;
	private Evento.Estado estado;
	private Evento.Destacado destacado;
	private int aforoMaximo;
	private int minimoAsistencia;
	private Double precio;

	public static EventoDto convertirAEventoDto(Evento evento) {
		EventoDto eventoDto = new EventoDto();

		eventoDto.setIdEvento(evento.getIdEvento());
		eventoDto.setIdTipo(evento.getTipo().getIdTipo());
		eventoDto.setNombre(evento.getNombre());
		eventoDto.setDescripcion(evento.getDescripcion());
		eventoDto.setFechaInicio(evento.getFechaInicio());
		eventoDto.setDuracion(evento.getDuracion());
		eventoDto.setDireccion(evento.getDireccion());
		eventoDto.setEstado(evento.getEstado());
		eventoDto.setDestacado(evento.getDestacado());
		eventoDto.setAforoMaximo(evento.getAforoMaximo());
		eventoDto.setMinimoAsistencia(evento.getMinimoAsistencia());
		eventoDto.setPrecio(evento.getPrecio());

		return eventoDto;
	}

}