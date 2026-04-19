package apirest.reto.service;

import java.util.List;

import apirest.reto.model.entity.UsuarioPerfil;

public interface UsuarioPerfilService {
	
	List<UsuarioPerfil> findAll();
	
	UsuarioPerfil findById(int idUsuarioPerfil);
	
	UsuarioPerfil insertOne(UsuarioPerfil usuarioPerfil);
	
	UsuarioPerfil updateOne(UsuarioPerfil usuarioPerfil);
	
	int deleteById(int idUsuarioPerfil);
}
