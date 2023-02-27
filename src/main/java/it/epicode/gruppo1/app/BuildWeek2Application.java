package it.epicode.gruppo1.app;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.epicode.gruppo1.app.config.Beans;
import it.epicode.gruppo1.app.entities.Ruolo;
import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.entities.enums.TipoRuolo;
import it.epicode.gruppo1.app.services.RuoloService;
import it.epicode.gruppo1.app.services.UtenteService;

@SpringBootApplication
public class BuildWeek2Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BuildWeek2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		popolaDb();

		
	}
	@Autowired
	private UtenteService us;
	@Autowired
	private RuoloService rs;
	
	public void popolaDb() {

ApplicationContext ctx = new AnnotationConfigApplicationContext(Beans.class);
		
		Utente u1 = (Utente)ctx.getBean("utente", "admin", "admin@mail.com", "admin", "admin", "admin");
		Utente u2 = (Utente)ctx.getBean("utente", "tano", "gaetano@mail.com", "ciao", "quarto", "gaetano");
		Ruolo r1 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ADMIN);
		Ruolo r2 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.UTENTE);
				
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
		
		System.out.println("db popolato!");
		
		
		
		((AnnotationConfigApplicationContext)ctx).close();
	}

}