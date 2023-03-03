package it.epicode.gruppo1.app;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.epicode.gruppo1.app.config.Beans;
import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Comune;
import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.entities.Indirizzo;
import it.epicode.gruppo1.app.entities.Ruolo;
import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.entities.enums.StatoFattura;
import it.epicode.gruppo1.app.entities.enums.TipoCliente;
import it.epicode.gruppo1.app.entities.enums.TipoRuolo;
import it.epicode.gruppo1.app.services.ClienteService;
import it.epicode.gruppo1.app.services.ComuneService;
import it.epicode.gruppo1.app.services.FatturaService;
import it.epicode.gruppo1.app.services.IndirizzoService;
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
	
	@Autowired
	private ComuneService cms;
	
	@Autowired
	private IndirizzoService is;
	
	public void popolaDb() {
		Ruolo r1 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ROLE_ADMIN);
		Ruolo r2 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ROLE_USER);
		Utente u1 = (Utente)ctx.getBean("utente", "admin", "admin@mail.com", "admin", "admin", "admin");
		Utente u2 = (Utente)ctx.getBean("utente", "user", "mario@mail.com", "user", "Mario", "Rossi");
				
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
		
		List<Comune> comuni = cms.getAll();
		
		Comune cm1 = comuni.get(10);
		Comune cm2 = comuni.get(50);
		
		Indirizzo i1 = (Indirizzo)ctx.getBean("indirizzo", "Via uno", 155, "Località 1", 10020, cm1);
		Indirizzo i2 = (Indirizzo)ctx.getBean("indirizzo", "Via due", 20, "Località 2", 10020, cm1);
		Indirizzo i3 = (Indirizzo)ctx.getBean("indirizzo", "Via tre", 50, "Località 3", 10020, cm1);
		Indirizzo i4 = (Indirizzo)ctx.getBean("indirizzo", "Via quattro", 200, "Località 4", 10020, cm1);
		Indirizzo i5 = (Indirizzo)ctx.getBean("indirizzo", "Via cinque", 18, "Località 5", 10080, cm2);
		Indirizzo i6 = (Indirizzo)ctx.getBean("indirizzo", "Via sei", 75, "Località 6", 10080, cm2);
		
		is.save(i1);
		is.save(i2);
		is.save(i3);
		is.save(i4);
		is.save(i5);
		is.save(i6);
		
		Cliente c1 = (Cliente)ctx.getBean("cliente", "Cliente 1", 123456789L, "cliente1@mail.com", LocalDate.now().minusDays(3), LocalDate.now(), 400000.00, "cliente1@pec.it", 3452392816L, "cc1@mail.com", "Gianfranco", "Rossi", 7544845655L, TipoCliente.SAS, i1, i2, Arrays.asList(f1, f2));
		Cliente c2 = (Cliente)ctx.getBean("cliente", "Cliente 2", 987654321L, "cliente2@mail.com", LocalDate.now().minusDays(3), LocalDate.now(), 200000.00, "cliente2@pec.it", 3345665898L, "cc2@mail.com", "Marco", "Verdi", 1447548665L, TipoCliente.PA, i3, i4, Arrays.asList(f3, f4));
		Cliente c3 = (Cliente)ctx.getBean("cliente", "Cliente 3", 456789123L, "cliente3@mail.com", LocalDate.now().minusDays(3), LocalDate.now(), 250000.00, "cliente3@pec.it", 4578559566L, "cc3@mail.com", "Nicola", "Bruno", 3255688844L, TipoCliente.SPA, i5, i6, Arrays.asList(f5, f6));
		
		cs.save(c1);
		cs.save(c2);
		cs.save(c3);
		
		System.out.println("Db popolato!");
	}

}