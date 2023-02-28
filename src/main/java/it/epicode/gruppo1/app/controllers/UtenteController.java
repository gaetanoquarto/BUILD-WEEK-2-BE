package it.epicode.gruppo1.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.services.UtenteService;

@RestController
@RequestMapping("/")
public class UtenteController {

	@Autowired
	private UtenteService us;
	
	@Autowired
	PasswordEncoder pwEncoder;
	
	@PostMapping("utenti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createCliente(@RequestBody Utente u) {
		String password = u.getPassword();
		u.setPassword(pwEncoder.encode(password));
		Utente utenti = us.save(u);
		
		return new ResponseEntity<Object>(utenti, HttpStatus.CREATED);
	}

	@GetMapping("auth/update_user_pw")
	@ResponseBody
	public String auth_update_user_pw() {
		int id = 2;
		
		Utente u = us.getById(id).get();
		String pw = u.getPassword();
		u.setPassword( pwEncoder.encode(pw) );
		us.save(u);
		
		return "utente aggiornato";
	}

}
