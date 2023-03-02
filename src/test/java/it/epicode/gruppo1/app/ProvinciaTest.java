package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.entities.enums.TipoCliente;
import it.epicode.gruppo1.app.services.ClienteService;
import it.epicode.gruppo1.app.services.ProvinciaService;

@SpringBootTest
class ProvinciaTest {

	@Autowired
	private ProvinciaService ps;

	@Test
	void SetAndGet() {

		Provincia provincia = new Provincia();
		provincia.setProvincia("test");
		provincia.setRegione("test2");
		provincia.setSigla("test3");

		ps.save(provincia);

		Optional<Provincia> recuperaProvincia = ps.getById(provincia.getId());

		assertTrue(recuperaProvincia.isPresent());
		assertEquals("test", recuperaProvincia.get().getProvincia());
		assertEquals("test2", recuperaProvincia.get().getRegione());
		assertEquals("test3", recuperaProvincia.get().getSigla());

		ps.delete(provincia);
	}

	@Test
	void delete() {
		Provincia provincia = new Provincia();
		provincia.setProvincia("test");
		provincia.setRegione("test2");
		provincia.setSigla("test3");

		ps.save(provincia);
		ps.delete(provincia);

		Optional<Provincia> provinciaEliminata = ps.getById(provincia.getId());

		assertTrue(provinciaEliminata.isEmpty());
	}

	@Test
	void getAll() {
		List<Provincia> province = ps.getAll();

		// inserire il numero di elementi presenti nel database.
		assertEquals(110, province.size());

	}

	@Test
	void update() {
		Provincia provincia = new Provincia();
		provincia.setProvincia("test");
		provincia.setRegione("test2");
		provincia.setSigla("test3");

		ps.save(provincia);

		Optional<Provincia> recuperaProvincia = ps.getById(provincia.getId());

		assertTrue(recuperaProvincia.isPresent());

		Provincia p1 = recuperaProvincia.get();
		p1.setProvincia("test4");

		ps.save(p1);
		
		assertEquals("test4", recuperaProvincia.get().getProvincia());
		
		ps.delete(provincia);

	}
	
	@Test
	void cercaPerNome() {
		Provincia provincia = new Provincia();
		provincia.setProvincia("test");
		provincia.setRegione("test2");
		provincia.setSigla("test3");

		ps.save(provincia);
		
		List<Provincia> p = ps.findByNome("test");
		
		//inserire l'index del nuovo oggetto creato
		assertEquals("test", p.get(110).getProvincia());
		
		ps.delete(provincia);

	}

}
