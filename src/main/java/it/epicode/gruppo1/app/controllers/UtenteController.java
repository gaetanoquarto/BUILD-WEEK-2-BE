package it.epicode.gruppo1.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.services.UtenteService;

@RestController
@RequestMapping("/")
public class UtenteController {

	@Autowired
	private UtenteService us;
	
	@PostMapping("utenti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createCliente(@RequestBody Utente u) {
		Utente utenti = us.save(u);
		
		return new ResponseEntity<Object>(utenti, HttpStatus.CREATED);
	}
}
