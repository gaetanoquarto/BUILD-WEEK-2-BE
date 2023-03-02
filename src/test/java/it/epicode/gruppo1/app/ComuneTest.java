package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Comune;
import it.epicode.gruppo1.app.services.ComuneService;

@SpringBootTest
class ComuneTest {

	@Autowired
	ComuneService cs;

	@Test
	public void testSave() {
	    Comune comune = new Comune();
	    comune.setNome("Monopoli");
	    comune.setCodProvincia(10);
	    comune.setProgComune("108");
	    
	    cs.save(comune);
	    
	    Optional<Comune> recuperaComune = cs.getById(comune.getId());
	    
	    assertTrue(recuperaComune.isPresent());
	    assertEquals("Monopoli", recuperaComune.get().getNome());
	    assertEquals(10, recuperaComune.get().getCodProvincia());
	    assertEquals("108", recuperaComune.get().getProgComune());
	    
	    cs.delete(comune);
	}
	
	@Test
	void testDelete() {
		Comune comune = new Comune();
	    comune.setNome("Monopoli");
	    comune.setCodProvincia(10);
	    comune.setProgComune("108");

		cs.save(comune);

		cs.getById(comune.getId());

		cs.delete(comune);
		
		Optional<Comune> comuneEliminato = cs.getById(comune.getId());
		
		assertTrue(comuneEliminato.isEmpty());
	}
	
	@Test
	public void testGetAll() {
		List<Comune> comuni = cs.getAll();
		
		//inserire il numero di elementi presenti nel database.
		assertEquals(7904, comuni.size());
	}
	
	@Test
	public void testUpdate() {
	    Comune comune = new Comune();
	    comune.setNome("Monopoly");
	    comune.setCodProvincia(10);
	    comune.setProgComune("108");

	    cs.save(comune);

	    Optional<Comune> recuperaComune = cs.getById(comune.getId());

	    assertTrue(recuperaComune.isPresent());
	    
	    Comune c1 = recuperaComune.get();
	    c1.setNome("Monopoli");;
	    
	    cs.save(c1);
	    
	    assertEquals("Monopoli", recuperaComune.get().getNome());
	    
	    cs.delete(comune);
	}

}
