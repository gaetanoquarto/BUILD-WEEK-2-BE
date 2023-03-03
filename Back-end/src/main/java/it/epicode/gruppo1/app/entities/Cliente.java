package it.epicode.gruppo1.app.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.epicode.gruppo1.app.entities.enums.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String ragioneSociale;
	private long partitaIva;
	private String email;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private double fatturatoAnnuale;
	private String pec;
	private long telefono;
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	private long telefonoContatto;
	
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;
	
	@OneToOne
	private Indirizzo sedeLegale;
	
	@OneToOne
	private Indirizzo sedeOperativa;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Fattura> fatture;

}
