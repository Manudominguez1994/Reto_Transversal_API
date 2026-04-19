package apirest.reto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.Perfil;
import apirest.reto.repository.PerfilRepository;

@Service
public class PerfilServiceImpl implements PerfilService{
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public List<Perfil> findAll() {
		return perfilRepository.findAll();
	}

	@Override
	public Perfil findById(int idPerfil) {
		return perfilRepository.findById(idPerfil).orElse(null);
	}

	@Override
	public Perfil insertOne(Perfil perfil) {
		if(perfilRepository.existsById(perfil.getIdPerfil())) {
			return null; // badRequest
		}else {
			return perfilRepository.save(perfil); // status(HttpStatus.CREATED).body()
		}
		
	}

	@Override
	public Perfil updateOne(Perfil perfil) {
		if(perfilRepository.existsById(perfil.getIdPerfil())) {
			return perfilRepository.save(perfil); // ok
		}else {
			return null; // notFound
		}
	}

	@Override
	public int deleteById(int idPerfil) {
		if(perfilRepository.existsById(idPerfil)) {
			perfilRepository.deleteById(idPerfil);
			return 1; // noContent
		}else {
			return 0; // notFound
		}
	}

}
