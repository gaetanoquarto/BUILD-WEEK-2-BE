package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Ruolo;
import it.epicode.gruppo1.app.entities.enums.TipoRuolo;
import it.epicode.gruppo1.app.services.RuoloService;

@SpringBootTest
class RuoloTest {

	@Autowired
	private RuoloService rs;

	@Test
	void testSaveAndGet() {

		Ruolo ruolo = new Ruolo();
		ruolo.setTipoRuolo(TipoRuolo.ROLE_USER);

		rs.save(ruolo);

		Optional<Ruolo> recuperaRuolo = rs.getById(ruolo.getId());

		assertTrue(recuperaRuolo.isPresent());
		assertEquals(TipoRuolo.ROLE_USER, recuperaRuolo.get().getTipoRuolo());
		

		rs.delete(ruolo);

	}

	@Test
	void testDelete() {
		
		Ruolo ruolo = new Ruolo();
		ruolo.setTipoRuolo(TipoRuolo.ROLE_USER);
		

		rs.save(ruolo);

		rs.delete(ruolo);

		Optional<Ruolo> ruoloEliminato = rs.getById(ruolo.getId());

		assertTrue(ruoloEliminato.isEmpty());

	}

	@Test
	void testGetAll() {

		List<Ruolo> ruoli = rs.getAll();

		// inserire il numero di elementi presenti nel database.
		assertEquals(2, ruoli.size());
	}

	@Test
	void testUpdate() {

		Ruolo ruolo = new Ruolo();
		ruolo.setTipoRuolo(TipoRuolo.ROLE_USER);

		rs.save(ruolo);

		Optional<Ruolo> recuperaRuolo = rs.getById(ruolo.getId());

		assertTrue(recuperaRuolo.isPresent());

		Ruolo r1 = recuperaRuolo.get();

		r1.setTipoRuolo(TipoRuolo.ROLE_ADMIN);

		rs.save(r1);

		assertEquals(TipoRuolo.ROLE_ADMIN, recuperaRuolo.get().getTipoRuolo());

		rs.delete(ruolo);

	}

}
