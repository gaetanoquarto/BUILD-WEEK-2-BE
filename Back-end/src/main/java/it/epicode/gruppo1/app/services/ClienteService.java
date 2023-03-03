package it.epicode.gruppo1.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.repositories.ClienteRepo;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepo cr;
	
	public Cliente save(Cliente c) {
		return cr.save(c);
	}
	
	public List<Cliente> getAll() {
		return cr.findAll();
	}
	
	public Page<Cliente> getAll_page(Pageable pageable) {
		return cr.findAll(pageable);
	}
	
	public Optional<Cliente> getById(int id) {
		return cr.findById(id);
	}
	
	public void delete(Cliente c) {
		cr.delete(c);
	}
	
	public List<Cliente> ordinaPerSedeLegale() {
		return cr.ordinaBySedeLegale();
	}
	
	public List<Cliente> findByFatturatoAnnuale(double fatturato) {
		return cr.findByFatturatoAnnuale(fatturato);
	}

	public List<Cliente> findByDataInserimento(LocalDate dataInserimento) {
		return cr.findByDataInserimento(dataInserimento);
	}

	public List<Cliente> findByDataUltimoContatto(LocalDate data) {
		return cr.findByDataUltimoContatto(data);
	}

	public List<Cliente> findByNome(String nome) {
		return cr.findByNome(nome);
	}
	
}
