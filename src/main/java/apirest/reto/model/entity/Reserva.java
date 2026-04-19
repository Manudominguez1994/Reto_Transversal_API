package apirest.reto.model.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reserva")
	private int idReserva;
	
	@NotNull(message = "El evento es obligatorio")
	@ManyToOne
	@JoinColumn(name = "id_evento", nullable = false)
	private Evento evento;
	
	@NotNull(message = "El usuario es obligatorio")
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private Usuario usuario;
	
	@NotNull(message = "El precio de venta es obligatorio")
	@Positive(message = "El precio de venta debe ser mayor que 0")
	@Column(name = "precio_venta", nullable = false)
	private Double precioVenta;
	
	@Column(name = "observaciones", nullable = true, length = 200)
	private String observaciones;
	
	@Min(value = 1, message = "La cantidad minima es 1 entrada")
	@Max(value = 10, message = "No se pueden reservar mas de 10 entradas")
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
}
