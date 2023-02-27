package it.epicode.gruppo1.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.gruppo1.app.entities.Utente;

@Repository
public interface UtenteRepo extends JpaRepository<Utente, Integer> {

}
