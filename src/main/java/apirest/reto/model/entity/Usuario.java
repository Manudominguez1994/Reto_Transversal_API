package apirest.reto.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username", length = 45)
	private String username;

	@NotBlank(message = "La contraseña es obligatoria")
	@Size(max = 100)
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El formato del email no es valido")
	@Size(max = 100)
	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;
	
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 30)
	@Column(name = "nombre", nullable = false, length = 30)
	private String nombre;
	
	@NotBlank(message = "Los apellidos son obligatorios")
	@Size(max = 45)
	@Column(name = "apellidos", nullable = false, length = 45)
	private String apellidos;
	
	@Size(max = 100)
	@Column(name = "direccion", length = 100)
	private String direccion;
	
	@NotNull(message = "El campo enabled es obligatorio")
	@Column(name = "enabled", nullable = false)
	private int enabled;
	
	@NotNull(message = "La fecha de registro es obligatoria")
	@Column(name = "fecha_registro", nullable = false)
	private LocalDate fechaRegistro; 

}