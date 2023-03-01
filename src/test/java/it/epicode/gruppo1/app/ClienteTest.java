package it.epicode.gruppo1.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import it.epicode.gruppo1.app.services.ClienteService;

@SpringBootTest
class ClienteTest {

    @MockBean
    private ClienteService cs;

	@Test
	void testGetClienti() {
	}

	@Test
	void testCreateCliente() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateCliente() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
