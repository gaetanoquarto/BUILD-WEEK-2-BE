package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Indirizzo;
import it.epicode.gruppo1.app.services.IndirizzoService;

@SpringBootTest
public class IndirizzoTest {

	@Autowired
	IndirizzoService is;

	@Test
	public void testSave() {
	    Indirizzo indirizzo = new Indirizzo();
	    indirizzo.setVia("Via Roma");
	    indirizzo.setCivico(10);
	    indirizzo.setLocalita("Località");
	    indirizzo.setCap(12345);
	    
	    is.save(indirizzo);
	    
	    Optional<Indirizzo> recuperaIndirizzo = is.getById(indirizzo.getId());
	    
	    assertTrue(recuperaIndirizzo.isPresent());
	    assertEquals("Via Roma", recuperaIndirizzo.get().getVia());
	    assertEquals(10, recuperaIndirizzo.get().getCivico());
	    assertEquals("Località", recuperaIndirizzo.get().getLocalita());
	    assertEquals(12345, recuperaIndirizzo.get().getCap());
	    
	    is.delete(indirizzo);
	}
	
	@Test
	void testDelete() {
		Indirizzo indirizzo = new Indirizzo();
	    indirizzo.setVia("Via Roma");
	    indirizzo.setCivico(10);
	    indirizzo.setLocalita("Località");
	    indirizzo.setCap(12345);

		is.save(indirizzo);

		is.getById(indirizzo.getId());

		is.delete(indirizzo);
		
		Optional<Indirizzo> indirizzoEliminato = is.getById(indirizzo.getId());
		
		assertTrue(indirizzoEliminato.isEmpty());
	}
	
	@Test
	public void testGetAll() {
		List<Indirizzo> indirizzi = is.getAll();
		
		//inserire il numero di elementi presenti nel database.
		assertEquals(6, indirizzi.size());
	}
	
	@Test
	public void testUpdate() {
		Indirizzo indirizzo = new Indirizzo();
	    indirizzo.setVia("Via Roma");
	    indirizzo.setCivico(10);
	    indirizzo.setLocalita("Località");
	    indirizzo.setCap(12345);

	    is.save(indirizzo);

	    Optional<Indirizzo> recuperaIndirizzo = is.getById(indirizzo.getId());

	    assertTrue(recuperaIndirizzo.isPresent());
	    
	    Indirizzo i1 = recuperaIndirizzo.get();
	    i1.setVia("Via Milano");
	    
	    is.save(i1);
	    
	    assertEquals("Via Milano", recuperaIndirizzo.get().getVia());
	    
	    is.delete(indirizzo);
	}

}

