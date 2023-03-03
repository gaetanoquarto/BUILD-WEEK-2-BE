package it.epicode.gruppo1.app.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Ruolo;
import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.entities.enums.TipoRuolo;
import it.epicode.gruppo1.app.services.RuoloService;
import it.epicode.gruppo1.app.services.UtenteService;

@RestController
@RequestMapping("/")
public class UtenteController {

	@Autowired
	private UtenteService us;
	
	@Autowired
	PasswordEncoder pwEncoder;
	
	@GetMapping("utenti")
	public ResponseEntity<List<Utente>> getUtenti() {
		List<Utente> utenti = us.getAll();
		
		return new ResponseEntity<>(utenti, HttpStatus.CREATED);
	}
	
	@GetMapping("utenti/{id}")
	public ResponseEntity<Object> getUtenteById(@PathVariable int id) {
		Optional<Utente> utenteObj = us.getById(id);
		
		ResponseEntity<Object> check = checkExists(utenteObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(utenteObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("utenti_page")
	public ResponseEntity<Object> getUtentiInPages(Pageable pageable) {
		Page<Utente> utenti = us.getAll_page(pageable);
		
		if(utenti.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	// Metodo per criptare la password dell'utente
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
	
	@Autowired
	RuoloService rs;
	
	@PostMapping("utenti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createUtente(@RequestBody Utente u) {
		String password = u.getPassword();
		Optional<Ruolo> userOp = rs.getById(2);
		Ruolo user = userOp.get();
		u.setRuoli(new HashSet<>() {{
			add(user);
		}});
		u.setPassword(pwEncoder.encode(password));
		Utente utente = us.save(u);
		
		return new ResponseEntity<Object>(utente, HttpStatus.CREATED);
	}
	
	@PostMapping("registrazione")
	public ResponseEntity<Object> registrati(@RequestBody Utente u) {
		String password = u.getPassword();
		Optional<Ruolo> userOp = rs.getById(2);
		Ruolo user = userOp.get();
		u.setRuoli(new HashSet<>() {{
			add(user);
		}});
		u.setPassword(pwEncoder.encode(password));
		Utente utente = us.save(u);
		
		return new ResponseEntity<Object>(utente, HttpStatus.CREATED);
	}
	
	@PutMapping("utenti/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateUtente(@PathVariable int id, @RequestBody Utente _utente) {
		Optional<Utente> utenteObj = us.getById(id);
		
		ResponseEntity<Object> check = checkExists(utenteObj);
		if(check != null) return check;
		
		Utente utente = utenteObj.get();
		
		utente.setUsername(_utente.getUsername());
		utente.setEmail(_utente.getUsername());
		utente.setPassword(_utente.getPassword());
		utente.setNome(_utente.getNome());
		utente.setCognome(_utente.getCognome());
		utente.setRuoli(_utente.getRuoli());
		
		us.save(utente);
		
		return new ResponseEntity<Object>(utente, HttpStatus.CREATED);
	}
	
	@DeleteMapping("utenti/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteUtente(@PathVariable int id) {
		Optional<Utente> utenteObj = us.getById(id);
		
		ResponseEntity<Object> check = checkExists(utenteObj);
		if(check != null) return check;
		
		us.delete(utenteObj.get());
		
		return new ResponseEntity<>(
			String.format("L'Utente con id %d è stato eliminato!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Utente> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}
	
}
