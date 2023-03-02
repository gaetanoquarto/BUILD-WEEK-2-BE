package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.entities.enums.StatoFattura;
import it.epicode.gruppo1.app.services.FatturaService;

@SpringBootTest
class FatturaTest {

	@Autowired
	private FatturaService fs;

	@Test
	void testSaveAndGetFattura() {

		Fattura fattura = new Fattura();
		fattura.setAnno(2023);
		fattura.setData(LocalDate.parse("2023-07-11"));
		fattura.setImporto(30000.50);
		fattura.setNumero(7);
		fattura.setStatoFattura(StatoFattura.PAGATA);

		fs.save(fattura);

		Optional<Fattura> recuperaFattura = fs.getById(fattura.getId());

		assertTrue(recuperaFattura.isPresent());
		assertEquals(2023, recuperaFattura.get() .getAnno());
		assertEquals(LocalDate.parse("2023-07-11"), recuperaFattura.get().getData());
		assertEquals(30000.50, recuperaFattura.get().getImporto());
		assertEquals(7, recuperaFattura.get().getNumero());
		assertEquals(StatoFattura.PAGATA, recuperaFattura.get().getStatoFattura());
		
		fs.delete(fattura);

	}
	
	@Test
	void testDeleteFattura() {
		
		Fattura fattura = new Fattura();
		fattura.setAnno(2023);
		fattura.setData(LocalDate.parse("2023-07-11"));
		fattura.setImporto(30000.50);
		fattura.setNumero(7);
		fattura.setStatoFattura(StatoFattura.PAGATA);

		fs.save(fattura);
		
		fs.delete(fattura);
		
		Optional<Fattura> fatturaEliminata = fs.getById(fattura.getId());
		
		assertTrue(fatturaEliminata.isEmpty());
		
	}
	
	@Test
	void testUpdate() {
	
		Fattura fattura = new Fattura();
		fattura.setAnno(2023);
		fattura.setData(LocalDate.parse("2023-07-11"));
		fattura.setImporto(30000.50);
		fattura.setNumero(7);
		fattura.setStatoFattura(StatoFattura.PAGATA);

		fs.save(fattura);
		
		Optional<Fattura> recuperaFattura = fs.getById(fattura.getId());
		
		assertTrue(recuperaFattura.isPresent()); 
		
		Fattura f1 = recuperaFattura.get();
		
		f1.setImporto(1000.50);
		
		fs.save(f1);
		
		assertEquals(1000.50, recuperaFattura.get().getImporto());
		
		fs.delete(f1);
		
	}
	
	@Test
	void testCercaPerAnno() {
		
		Fattura fattura = new Fattura();
		fattura.setAnno(2023);
		fattura.setData(LocalDate.parse("2023-07-11"));
		fattura.setImporto(30000.50);
		fattura.setNumero(7);
		fattura.setStatoFattura(StatoFattura.PAGATA);

		fs.save(fattura);
		
		List<Fattura> f = fs.getFromAnno(2023);
		
		assertEquals(2023, f.get(0).getAnno());
		
		fs.delete(fattura);
		
	}

}
