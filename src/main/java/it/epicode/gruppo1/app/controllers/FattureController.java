package it.epicode.gruppo1.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.services.FatturaService;

@RestController
@RequestMapping("/")
public class FattureController {

	@Autowired
	private FatturaService fs;
	
	@GetMapping("fatture")
	public ResponseEntity<List<Fattura>> getClienti() {
		List<Fattura> fatture = fs.getAll();
		
		return new ResponseEntity<>(fatture, HttpStatus.CREATED);
	}
	
	@GetMapping("fatture/{id}")
	public ResponseEntity<Object> getFatturaById(@PathVariable int id) {
		Optional<Fattura> fatturaObj = fs.getById(id);
		
		ResponseEntity<Object> check = checkExists(fatturaObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(fatturaObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("fatture_page")
	public ResponseEntity<Object> getFattureInPages(Pageable pageable) {
		Page<Fattura> fatture = fs.getAllInPages(pageable);
		
		if(fatture.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(fatture, HttpStatus.OK);
	}
	
	@PostMapping("fatture")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createCliente(@RequestBody Fattura f) {
		Fattura fatture = fs.save(f);
		
		return new ResponseEntity<Object>(fatture, HttpStatus.CREATED);
	}
	
	@PutMapping("fatture/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateCliente(@PathVariable int id, @RequestBody Fattura _fattura) {
		Optional<Fattura> fatturaObj = fs.getById(id);
		
		ResponseEntity<Object> check = checkExists(fatturaObj);
		if(check != null) return check;
		
		Fattura fattura = fatturaObj.get();
		
		fattura.setAnno(_fattura.getAnno());
		fattura.setData(_fattura.getData());
		fattura.setImporto(_fattura.getImporto());
		fattura.setNumero(_fattura.getNumero());
		fattura.setStatoFattura(_fattura.getStatoFattura());

		fs.save(fattura);
		
		return new ResponseEntity<Object>(fattura, HttpStatus.CREATED);
	}
	
	@DeleteMapping("fatture/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		Optional<Fattura> fatturaObj = fs.getById(id);
		
		ResponseEntity<Object> check = checkExists(fatturaObj);
		if(check != null) return check;
		
		fs.delete(fatturaObj.get());
		
		return new ResponseEntity<>(
			String.format("La Fattura con id %d è stata eliminata!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Fattura> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}
	
}
