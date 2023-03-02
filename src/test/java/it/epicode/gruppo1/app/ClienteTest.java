package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.enums.TipoCliente;
import it.epicode.gruppo1.app.services.ClienteService;

@SpringBootTest
class ClienteTest {

	@Autowired
	private ClienteService cs;

	@Test
	void testSaveAndGetClienti() {

		Cliente cliente = new Cliente();
		cliente.setRagioneSociale("test");
		cliente.setPartitaIva(123456789L);
		cliente.setEmail("cliente1@mail.com");
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		cliente.setFatturatoAnnuale(400000.00);
		cliente.setPec("cliente1@pec.it");
		cliente.setTelefono(3452392816L);
		cliente.setEmailContatto("cc1@mail.com");
		cliente.setNomeContatto("Gianfranco");
		cliente.setCognomeContatto("Rossi");
		cliente.setTelefonoContatto(7544845655L);
		cliente.setTipoCliente(TipoCliente.SAS);

		cs.save(cliente);

		Optional<Cliente> recuperaCliente = cs.getById(cliente.getId());

		assertTrue(recuperaCliente.isPresent());
		assertEquals("test", recuperaCliente.get().getRagioneSociale());
		assertEquals(123456789L, recuperaCliente.get().getPartitaIva());
		assertEquals("cliente1@mail.com", recuperaCliente.get().getEmail());
		assertEquals(LocalDate.now(), recuperaCliente.get().getDataInserimento());
		assertEquals(LocalDate.now(), recuperaCliente.get().getDataUltimoContatto());
		assertEquals(400000.00, recuperaCliente.get().getFatturatoAnnuale());
		assertEquals("cliente1@pec.it", recuperaCliente.get().getPec());
		assertEquals(3452392816L, recuperaCliente.get().getTelefono());
		assertEquals("cc1@mail.com", recuperaCliente.get().getEmailContatto());
		assertEquals("Gianfranco", recuperaCliente.get().getNomeContatto());
		assertEquals("Rossi", recuperaCliente.get().getCognomeContatto());
		assertEquals(7544845655L, recuperaCliente.get().getTelefonoContatto());
		assertEquals(TipoCliente.SAS, recuperaCliente.get().getTipoCliente());

		cs.delete(cliente);

	}


	@Test
	void testDeleteCliente() {
		Cliente cliente = new Cliente();
		cliente.setRagioneSociale("test");
		cliente.setPartitaIva(123456789L);
		cliente.setEmail("cliente1@mail.com");
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		cliente.setFatturatoAnnuale(400000.00);
		cliente.setPec("cliente1@pec.it");
		cliente.setTelefono(3452392816L);
		cliente.setEmailContatto("cc1@mail.com");
		cliente.setNomeContatto("Gianfranco");
		cliente.setCognomeContatto("Rossi");
		cliente.setTelefonoContatto(7544845655L);
		cliente.setTipoCliente(TipoCliente.SAS);


		cs.save(cliente);

		cs.delete(cliente);

		Optional<Cliente> clienteEliminato = cs.getById(cliente.getId());

		assertTrue(clienteEliminato.isEmpty());

	}
		

	
	@Test
	void testGetAll() {


		List<Cliente> clienti = cs.getAll();

		// inserire il numero di elementi presenti nel database.

		assertEquals(4, clienti.size());
	}
	
	@Test
	void testUpdate() {



		Cliente cliente = new Cliente();
		cliente.setRagioneSociale("test");
		cliente.setPartitaIva(123456789L);
		cliente.setEmail("cliente1@mail.com");
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		cliente.setFatturatoAnnuale(400000.00);
		cliente.setPec("cliente1@pec.it");
		cliente.setTelefono(3452392816L);
		cliente.setEmailContatto("cc1@mail.com");
		cliente.setNomeContatto("Gianfranco");
		cliente.setCognomeContatto("Rossi");
		cliente.setTelefonoContatto(7544845655L);
		cliente.setTipoCliente(TipoCliente.SAS);


		cs.save(cliente);

		Optional<Cliente> recuperaCliente = cs.getById(cliente.getId());

		assertTrue(recuperaCliente.isPresent());

		Cliente c1 = recuperaCliente.get();

		c1.setRagioneSociale("test123");

		cs.save(c1);

		assertEquals("test123", recuperaCliente.get().getRagioneSociale());

		cs.delete(cliente);


	}
	


	@Test
	void testCercaPerNome() {
		Cliente cliente = new Cliente();
		cliente.setRagioneSociale("test");
		cliente.setPartitaIva(123456789L);
		cliente.setEmail("cliente1@mail.com");
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		cliente.setFatturatoAnnuale(400000.00);
		cliente.setPec("cliente1@pec.it");
		cliente.setTelefono(3452392816L);
		cliente.setEmailContatto("cc1@mail.com");
		cliente.setNomeContatto("test1");
		cliente.setCognomeContatto("Rossi");
		cliente.setTelefonoContatto(7544845655L);
		cliente.setTipoCliente(TipoCliente.SAS);

		cs.save(cliente);

		List<Cliente> c = cs.findByNome("test1");

		assertEquals("test1", c.get(0).getNomeContatto());


		cs.delete(cliente);
	}
}