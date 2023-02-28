package it.epicode.gruppo1.app;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.epicode.gruppo1.app.config.Beans;
import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.entities.Ruolo;
import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.entities.enums.StatoFattura;
import it.epicode.gruppo1.app.entities.enums.TipoCliente;
import it.epicode.gruppo1.app.entities.enums.TipoRuolo;
import it.epicode.gruppo1.app.services.ClienteService;
import it.epicode.gruppo1.app.services.FatturaService;
import it.epicode.gruppo1.app.services.RuoloService;
import it.epicode.gruppo1.app.services.UtenteService;

@SpringBootApplication
public class BuildWeek2Application implements CommandLineRunner {
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(Beans.class);

	public static void main(String[] args) {
		SpringApplication.run(BuildWeek2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		popolaDb();
//		popolaDb2();
		
		((AnnotationConfigApplicationContext)ctx).close();
	}
	
	@Autowired
	private UtenteService us;
	
	@Autowired
	private RuoloService rs;
	
	@Autowired
	private FatturaService fs;
	
	@Autowired
	private ClienteService cs;
	
	public void popolaDb() {
		Utente u1 = (Utente)ctx.getBean("utente", "admin", "admin@mail.com", "admin", "admin", "admin");
		Utente u2 = (Utente)ctx.getBean("utente", "tano", "gaetano@mail.com", "ciao", "quarto", "gaetano");
		Ruolo r1 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ROLE_ADMIN);
		Ruolo r2 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ROLE_USER);
				
		rs.save(r1);
		rs.save(r2);
		
		u1.setRuoli(new HashSet<>() {{
			add(r1);
		}});

		u2.setRuoli(new HashSet<>() {{
			add(r2);
		}});
		
		us.save(u1);
		us.save(u2);
		
		System.out.println("Db popolato!");
	}
	
	public void popolaDb2() {
		Fattura f1 = (Fattura)ctx.getBean("fattura", 2021, LocalDate.parse("2021-05-12"), 1500.50, 100, StatoFattura.DA_PAGARE);
		Fattura f2 = (Fattura)ctx.getBean("fattura", 2020, LocalDate.parse("2020-08-21"), 1900.81, 101, StatoFattura.SCADUTA);
		Fattura f3 = (Fattura)ctx.getBean("fattura", 2022, LocalDate.parse("2022-10-15"), 500.73, 102, StatoFattura.PAGATA);
		Fattura f4 = (Fattura)ctx.getBean("fattura", 2018, LocalDate.parse("2018-09-03"), 2000.27, 103, StatoFattura.DA_PAGARE);
		Fattura f5 = (Fattura)ctx.getBean("fattura", 2015, LocalDate.parse("2015-01-29"), 1600.21, 104, StatoFattura.PAGATA);
		Fattura f6 = (Fattura)ctx.getBean("fattura", 2023, LocalDate.parse("2023-12-08"), 2200.70, 105, StatoFattura.DA_PAGARE);
		
		fs.save(f1);
		fs.save(f2);
		fs.save(f3);
		fs.save(f4);
		fs.save(f5);
		fs.save(f6);
		
		Cliente c1 = (Cliente)ctx.getBean("cliente", "cliente1", 483532452345L, "cliente1@mail.com", LocalDate.now().minusDays(3), LocalDate.now(), 300000.00, "cliente1@pec.it", 3452392816L, "cc1@mail.com", "Gianfranco", "Rossi", 321235681L, TipoCliente.SAS, null, Arrays.asList(f1, f2));
		Cliente c2 = (Cliente)ctx.getBean("cliente", "cliente2", 483532452345L, "cliente2@mail.com", LocalDate.now().minusDays(3), LocalDate.now(), 300000.00, "cliente2@pec.it", 3452392816L, "cc2@mail.com", "Marco", "Verdi", 321235681L, TipoCliente.PA, null, Arrays.asList(f3, f4));
		Cliente c3 = (Cliente)ctx.getBean("cliente", "cliente3", 483532452345L, "cliente3@mail.com", LocalDate.now().minusDays(3), LocalDate.now(), 300000.00, "cliente3@pec.it", 3452392816L, "cc3@mail.com", "Nicola", "Bruno", 321235681L, TipoCliente.SPA, null, Arrays.asList(f5, f6));
		
		cs.save(c1);
		cs.save(c2);
		cs.save(c3);
		
		System.out.println("Db popolato!");
	}

}