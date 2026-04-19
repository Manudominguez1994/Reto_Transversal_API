package apirest.reto.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name="eventos")
public class Evento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private int idEvento;
	
	@NotNull(message = "El tipo del evento es obligatorio")
	@ManyToOne
	@JoinColumn(name = "id_tipo", nullable = false)
	private Tipo tipo;
	
	@NotBlank(message = "El nombre del evento es obligatorio")
	@Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	@Size(max = 200, message = "La descripcion no puede superar los 200 caracteres")
	@Column(name = "descripcion", length = 200)
	private String descripcion;
	
	@NotNull(message = "La fecha de inicio es obligatoria")
	@Column(name = "fecha_inicio", nullable = false)
	private LocalDate fechaInicio;
	
	@Min(value = 1, message = "La duracion debe ser al menos 1 minuto")
	@Column(name = "duracion", nullable = false)
	private int duracion;
	
	@NotBlank(message = "La direccion es obligatoria")
	@Size(max = 100, message = "La direccion no puede superar los 100 caracteres")
	@Column(name = "direccion", nullable = false, length = 100)
	private String direccion;
	
	public enum Estado{
		CANCELADO, TERMINADO, ACTIVO
	}
	
	@NotNull(message = "El estado del evento es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private Estado estado;
	
	public enum Destacado{
		S, N
	}
	
	@NotNull(message = "El campo destacado es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(name = "destacado", nullable = false)
	private Destacado destacado;
	
	@Min(value = 1, message = "El aforo maximo debe ser al menos 1")
	@Column(name = "aforo_maximo", nullable = false)
	private int aforoMaximo;
	
	@Min(value = 1, message = "El minimo de asistencia debe ser al menos 1")
	@Column(name = "minimo_asistencia", nullable = false)
	private int minimoAsistencia;
	
	@NotNull(message = "El precio es obligatorio")
	@Positive(message = "El precio debe ser mayor que 0")
	@Column(name = "precio", nullable = false)
	private Double precio;

}
