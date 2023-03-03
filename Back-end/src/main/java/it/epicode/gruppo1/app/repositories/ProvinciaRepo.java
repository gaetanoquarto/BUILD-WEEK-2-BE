package it.epicode.gruppo1.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Provincia;

@Repository
public interface ProvinciaRepo extends JpaRepository<Provincia, Integer>{
	@Query(
		    nativeQuery = true,
		    value = "SELECT * FROM province WHERE LOWER(provincia) LIKE LOWER(provincia)"
		)
	List<Provincia> findByNome(@Param("fn") String nome);
}
