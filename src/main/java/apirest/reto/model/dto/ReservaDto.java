package apirest.reto.model.dto;

import apirest.reto.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idReserva")

public class ReservaDto {

	private int idReserva;
	private int idEvento;
	private String username;
	private Double precioVenta;
	private String observaciones;
	private int cantidad;

	public static ReservaDto convertirAReservaDto(Reserva reserva) {
		ReservaDto reservaDto = new ReservaDto();

		reservaDto.setIdReserva(reserva.getIdReserva());
		reservaDto.setIdEvento(reserva.getEvento().getIdEvento());
		reservaDto.setUsername(reserva.getUsuario().getUsername());
		reservaDto.setPrecioVenta(reserva.getPrecioVenta());
		reservaDto.setObservaciones(reserva.getObservaciones());
		reservaDto.setCantidad(reserva.getCantidad());

		return reservaDto;
	}

}