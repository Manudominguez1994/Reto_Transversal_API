package apirest.reto.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="tipos")
public class Tipo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo")
	private int idTipo;
	
	@NotBlank(message = "El nombre del tipo es obligatorio")
	@Size(max = 45)
	@Column(name = "nombre", nullable = false, length = 45)
	private String nombre;
	
	@Size(max = 200)
	@Column(name = "descripcion", length = 200)
	private String descripcion;
	
}
