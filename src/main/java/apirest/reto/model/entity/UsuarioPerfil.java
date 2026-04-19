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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "usuario_perfiles")
public class UsuarioPerfil implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario_perfiles")
    private int idUsuarioPerfil;
	
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_perfil", nullable = false)
	private Perfil perfil;

}
