package it.epicode.gruppo1.app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.gruppo1.app.entities.Fattura;
import it.epicode.gruppo1.app.entities.enums.StatoFattura;

@Repository
public interface FatturaRepo extends JpaRepository<Fattura, Integer> {

	List<Fattura> findByStatoFattura(StatoFattura stato);
	List<Fattura> findByData(LocalDate data);
	List<Fattura> findByAnno(int anno);
	List<Fattura> findByImportoBetween(@Param("min") double min, @Param("max") double max);
	
	@Query(
			nativeQuery = true,
			value = "SELECT * FROM fatture JOIN clienti_fatture cf ON cf.fatture_id = fatture.id WHERE cf.cliente_id = :id"
			)
	List<Fattura> findByCliente(@Param("id") int id);
	
}
