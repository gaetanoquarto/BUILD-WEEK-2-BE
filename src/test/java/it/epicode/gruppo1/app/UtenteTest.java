package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.services.UtenteService;

@SpringBootTest
class UtenteTest {

	@Autowired
	UtenteService us;

	@Test
	public void testSave() {
	    Utente utente = new Utente();
	    utente.setUsername("test");
	    utente.setEmail("test@m.it");
	    utente.setPassword("test");
	    utente.setNome("Nome");
	    utente.setCognome("Cognome");
	    	    
	    us.save(utente);
	    
	    Optional<Utente> recuperaUtente = us.getById(utente.getId());
	    
	    assertTrue(recuperaUtente.isPresent());
	    assertEquals("test", recuperaUtente.get().getUsername());
	    assertEquals("test@m.it", recuperaUtente.get().getEmail());
	    assertEquals("test", recuperaUtente.get().getPassword());
	    assertEquals("Nome", recuperaUtente.get().getNome());
	    assertEquals("Cognome", recuperaUtente.get().getCognome());
	    
	    us.delete(utente);
	}
	
	@Test
	public void testDelete() {
		Utente utente = new Utente();
	    utente.setUsername("test");
	    utente.setEmail("test@m.it");
	    utente.setPassword("test");
	    utente.setNome("Nome");
	    utente.setCognome("Cognome");

	    us.save(utente);

	    us.getById(utente.getId());

	    us.delete(utente);
		
	    Optional<Utente> recuperaUtente = us.getById(utente.getId());
	    
		assertTrue(recuperaUtente.isEmpty());
	}
	
	@Test
	public void testGetAll() {
		List<Utente> utenti = us.getAll();
		
		//inserire il numero di elementi presenti nel database.
		assertEquals(2, utenti.size());
	}
	
	@Test
	public void testUpdate() {
		Utente utente = new Utente();
	    utente.setUsername("test");
	    utente.setEmail("test@m.it");
	    utente.setPassword("test");
	    utente.setNome("Nome");
	    utente.setCognome("Cognome");

	    us.save(utente);

	    Optional<Utente> recuperaUtente = us.getById(utente.getId());

	    assertTrue(recuperaUtente.isPresent());
	    
	    Utente u1 = recuperaUtente.get();
	    u1.setPassword("test123");
	    
	    us.save(u1);
	    
	    assertEquals("test123", recuperaUtente.get().getPassword());
	    
	    us.delete(utente);
	}

}
