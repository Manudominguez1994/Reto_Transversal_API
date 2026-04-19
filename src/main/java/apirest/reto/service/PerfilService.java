package apirest.reto.service;

import java.util.List;

import apirest.reto.model.entity.Perfil;

public interface PerfilService {
	
	List<Perfil> findAll();
	
	Perfil findById(int idPerfil);
	
	Perfil insertOne(Perfil perfil);
	
	Perfil updateOne(Perfil perfil);
	
	int deleteById(int idPerfil);

}
