package it.epicode.gruppo1.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.gruppo1.app.entities.Indirizzo;

@Repository
public interface IndirizzoRepo extends JpaRepository<Indirizzo, Integer>{

}
