package apirest.reto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.UsuarioPerfil;
import apirest.reto.repository.UsuarioPerfilRepository;

@Service
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService{

	@Autowired
	private UsuarioPerfilRepository upRepository;
	
	@Override
	public List<UsuarioPerfil> findAll() {
		return upRepository.findAll();
	}

	@Override
	public UsuarioPerfil findById(int idUsuarioPerfil) {
		return upRepository.findById(idUsuarioPerfil).orElse(null);
	}

	@Override
	public UsuarioPerfil insertOne(UsuarioPerfil usuarioPerfil) {
		if(upRepository.existsById(usuarioPerfil.getIdUsuarioPerfil())) {
			return null;
		}else {
			return upRepository.save(usuarioPerfil);
		}
	}

	@Override
	public UsuarioPerfil updateOne(UsuarioPerfil usuarioPerfil) {
		if(upRepository.existsById(usuarioPerfil.getIdUsuarioPerfil())) {
			return upRepository.save(usuarioPerfil);
		}else {
			return null;
		}
	}

	@Override
	public int deleteById(int idUsuarioPerfil) {
		if(upRepository.existsById(idUsuarioPerfil)){
			upRepository.deleteById(idUsuarioPerfil);
			return 1;
		}else {
			return 0;
		}
	}

}
