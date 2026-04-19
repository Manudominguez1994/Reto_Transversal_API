package apirest.reto.model.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "perfiles")
public class Perfil implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@Id
	// Este no lleva GenerationType porque no es AUTO_INCREMENT
	@Column(name = "id_perfil")
	private int idPerfil;
	
	@NotBlank(message = "El nombre del perfil es obligatorio")
	@Size(max = 45)
	@Column(name = "nombre", nullable = false, unique = true, length = 45)
	private String nombre;
	
}
