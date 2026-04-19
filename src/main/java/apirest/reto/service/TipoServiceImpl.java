package apirest.reto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.Tipo;
import apirest.reto.repository.TipoRepository;

@Service
public class TipoServiceImpl implements TipoService{

	@Autowired
	private TipoRepository tipoRepository;
	
	@Override
	public List<Tipo> findAll() {
		return tipoRepository.findAll();
	}

	@Override
	public Tipo findById(int idTipo) {
		return tipoRepository.findById(idTipo).orElse(null);
	}

	@Override
	public Tipo insertOne(Tipo tipo) {
		return tipoRepository.save(tipo);
	}

	@Override
	public Tipo updateOne(Tipo tipo) {
		if(tipoRepository.existsById(tipo.getIdTipo())) {
			return tipoRepository.save(tipo);
		}else {
			return null;
		}
	}

	@Override
	public int deleteById(int idTipo) {
		if(tipoRepository.existsById(idTipo)) {
			tipoRepository.deleteById(idTipo);
			return 1;
		}else {
			return 0;
		}
	}

}
