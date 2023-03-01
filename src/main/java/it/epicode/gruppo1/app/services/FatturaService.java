package it.epicode.gruppo1.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.entities.enums.StatoFattura;
import it.epicode.gruppo1.app.repositories.FatturaRepo;

@Service
public class FatturaService {

	@Autowired
	private FatturaRepo fr;
	
	public Fattura save(Fattura f) {
		return fr.save(f);
	}
	
	public List<Fattura> getAll() {
		return fr.findAll();
	}
	
	public Page<Fattura> getAll_page(Pageable pageable) {
		return fr.findAll(pageable);
	}
	
	public Optional<Fattura> getById(int id) {
		return fr.findById(id);
	}
	
	public void delete(Fattura f) {
		fr.delete(f);
	}
	
    public List<Fattura> getByCliente(int id) {
		return fr.findByCliente(id);
	}
	
    public List<Fattura> getFromStatoFattura(StatoFattura stato) {
    	return fr.findByStatoFattura(stato);
    }
	
    public List<Fattura> getFromData(LocalDate data) {
    	return fr.findByData(data);
    }
    
    public List<Fattura> getFromAnno(int anno) {
    	return fr.findByAnno(anno);
    }
    
    public List<Fattura> getByImporto(double min, double max) {
		return fr.findByImportoBetween(min, max);
	}
    
}
