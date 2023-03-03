package it.epicode.gruppo1.app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.gruppo1.app.entities.Cliente;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Integer> {
	
		List<Cliente> findByFatturatoAnnuale(double fatturatoAnnuale);
		List<Cliente> findByDataInserimento(LocalDate dataInserimento);
		List<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto);
		
		@Query(
			    nativeQuery = true,
			    value = "SELECT * FROM clienti WHERE LOWER(nome_contatto) LIKE LOWER(CONCAT('%', :fn, '%'))"
			)
		List<Cliente> findByNome(@Param("fn") String nome);

		@Query(
			    nativeQuery = true,
			    value = "SELECT c.* FROM clienti c INNER JOIN indirizzi i ON c.sede_legale_id = i.id INNER JOIN comuni cm ON i.comune_id = cm.id INNER JOIN province p ON cm.provincia_id = p.id ORDER BY p.provincia"
			)
		List<Cliente> ordinaBySedeLegale();

}
