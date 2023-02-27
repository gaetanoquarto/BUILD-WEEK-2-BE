package it.epicode.gruppo1.app.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Comune;
import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.entities.Indirizzo;
import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.entities.Ruolo;
import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.entities.enums.TipoCliente;
import it.epicode.gruppo1.app.entities.enums.TipoRuolo;

@Configuration
public class Beans {

	@Bean
	@Scope("prototype")
	public Cliente cliente(String ragioneSociale, long partitaIva, String email, LocalDate dataIserimento, LocalDate dataUltimoContatto, double fatturatoAnnuale, String pec, long telefono, String emailContatto, String nomeContatto, String cognomeContatto, long telefonoContatto, TipoCliente tipoCliente, List<Indirizzo> indirizzi, List<Fattura> fatture) {
		return Cliente.builder()
				.ragioneSociale(ragioneSociale)
				.partitaIva(partitaIva)
				.email(emailContatto)
				.dataInserimento(dataIserimento)
				.dataUltimoContatto(dataUltimoContatto)
				.fatturatoAnnuale(fatturatoAnnuale)
				.pec(pec)
				.telefono(telefono)
				.emailContatto(emailContatto)
				.nomeContatto(nomeContatto)
				.cognomeContatto(cognomeContatto)
				.telefono(telefonoContatto)
				.tipoCliente(tipoCliente)
				.indirizzi(indirizzi)
				.fatture(fatture)
				.build();
	}
	
	@Bean
	@Scope("prototype")
	public Comune comune(String nome, Provincia provincia) {
		return Comune.builder()
				.nome(nome)
				.provincia(provincia)
				.build();
	}
	
	@Bean
	@Scope("prototype")
	public Fattura fattura(int anno, LocalDate data, double importo, int numero) { 
	return Fattura.builder()
			.anno(anno)
			.data(data)
			.importo(importo)
			.numero(numero)
			.build();
	}
	
	@Bean
	@Scope("prototype")
	public Indirizzo indirizzo(String via, int civico, String localita, int cap, Comune comune) { 
	return Indirizzo.builder()
			.via(via)
			.civico(civico)
			.localita(localita)
			.cap(cap)
			.comune(comune)
			.build();
	}
	
	@Bean
	@Scope("prototype")
	public Provincia provincia(String nome, String sigla) {
		return Provincia.builder()
				.nome(nome)
				.sigla(sigla)
				.build();
	}
	
	@Bean
	@Scope("prototype")
	public Ruolo ruolo(TipoRuolo tr) {
		return Ruolo.builder()
				.tipoRuolo(tr)
				.build();
	}
	
	@Bean
	@Scope("prototype")
	public Utente utente(String username, String email, String password, String nome, String cognome) {
		return  Utente.builder()
				.username(username)
				.email(email)
				.password(password)
				.nome(nome)
				.cognome(cognome)
				.attivo(true)
				.build();		
	}
	
}