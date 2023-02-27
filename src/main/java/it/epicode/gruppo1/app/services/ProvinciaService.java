package it.epicode.gruppo1.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.repositories.ProvinciaRepo;

@Service
public class ProvinciaService {
	
	@Autowired
	private ProvinciaRepo pr;
	
	public Provincia save(Provincia p) {
		return pr.save(p);
	}
	
	public Optional<Provincia> getById(int id) {
		return pr.findById(id);
	}
	
	public List<Provincia> getAll() {
		return pr.findAll();
	}
	
	public Page<Provincia> getAll_page(Pageable pageable) {
		return pr.findAll(pageable);
	}
	
	public void delete(Provincia p) {
		pr.delete(p);
	}

}
