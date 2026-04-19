package apirest.reto.service;

import java.util.List;
import apirest.reto.model.entity.Tipo;

public interface TipoService {
	
	List<Tipo> findAll();
	
	Tipo findById(int idTipo);
	
	Tipo insertOne(Tipo tipo);
	
	Tipo updateOne(Tipo tipo);
	
	int deleteById(int idTipo);
	
}
