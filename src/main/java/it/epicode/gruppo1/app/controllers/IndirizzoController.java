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

import it.epicode.gruppo1.app.entities.Indirizzo;
import it.epicode.gruppo1.app.services.IndirizzoService;

@RestController
@RequestMapping("/")
public class IndirizzoController {

	@Autowired
	private IndirizzoService is;
	
	@GetMapping("indirizzi")
	public ResponseEntity<List<Indirizzo>> getIndirizzi() {
		List<Indirizzo> indirizzi = is.getAll();
		
		return new ResponseEntity<>(indirizzi, HttpStatus.CREATED);
	}
	
	@GetMapping("indirizzi/{id}")
	public ResponseEntity<Object> getIndirizzoById(@PathVariable int id) {
		Optional<Indirizzo> indirizzoObj = is.getById(id);
		
		ResponseEntity<Object> check = checkExists(indirizzoObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(indirizzoObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("indirizzi_page")
	public ResponseEntity<Object> getIndirizziInPages(Pageable pageable) {
		Page<Indirizzo> indirizzi = is.getAll_page(pageable);
		
		if(indirizzi.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(indirizzi, HttpStatus.OK);
	}
	
	@PostMapping("indirizzi")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createIndirizzo(@RequestBody Indirizzo i) {
		Indirizzo indirizzo = is.save(i);
		
		return new ResponseEntity<Object>(indirizzo, HttpStatus.CREATED);
	}
	
	@PutMapping("indirizzi/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateIndirizzo(@PathVariable int id, @RequestBody Indirizzo _indirizzo) {
		Optional<Indirizzo> indirizzoObj = is.getById(id);
		
		ResponseEntity<Object> check = checkExists(indirizzoObj);
		if(check != null) return check;
		
		Indirizzo indirizzo = indirizzoObj.get();
		
		indirizzo.setVia(_indirizzo.getVia());
		indirizzo.setCivico(_indirizzo.getCivico());
		indirizzo.setLocalita(_indirizzo.getLocalita());
		indirizzo.setCap(_indirizzo.getCap());
		indirizzo.setComune(_indirizzo.getComune());
		
		is.save(indirizzo);
		
		return new ResponseEntity<Object>(indirizzo, HttpStatus.CREATED);
	}
	
	@DeleteMapping("indirizzi/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteIndirizzo(@PathVariable int id) {
		Optional<Indirizzo> indirizzoObj = is.getById(id);
		
		ResponseEntity<Object> check = checkExists(indirizzoObj);
		if(check != null) return check;
		
		is.delete(indirizzoObj.get());
		
		return new ResponseEntity<>(
			String.format("L'indirizzo con id %d è stato eliminato!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Indirizzo> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}

}
