package it.epicode.gruppo1.app.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.services.ClienteService;

@RestController
@RequestMapping("/")
public class ClienteController {
	
	@Autowired
	private ClienteService cs;
	
	@GetMapping("clienti")
	public ResponseEntity<List<Cliente>> getClienti() {
		List<Cliente> clienti = cs.getAll();
		
		return new ResponseEntity<>(clienti, HttpStatus.CREATED);
	}
	
	@GetMapping("clienti/{id}")
	public ResponseEntity<Object> getClienteById(@PathVariable int id) {
		Optional<Cliente> clienteObj = cs.getById(id);
		
		ResponseEntity<Object> check = checkExists(clienteObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(clienteObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("clienti_page")
	public ResponseEntity<Object> getClientiInPages(Pageable pageable) {
		Page<Cliente> clienti = cs.getAllInPages(pageable);
		
		if(clienti.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(clienti, HttpStatus.OK);
	}
	
	@PostMapping("clienti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createCliente(@RequestBody Cliente c) {
		Cliente clienti = cs.save(c);
		
		return new ResponseEntity<Object>(clienti, HttpStatus.CREATED);
	}
	
	@PutMapping("clienti/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateCliente(@PathVariable int id, @RequestBody Cliente _cliente) {
		Optional<Cliente> clienteObj = cs.getById(id);
		
		ResponseEntity<Object> check = checkExists(clienteObj);
		if(check != null) return check;
		
		Cliente cliente = clienteObj.get();
		
		cliente.setRagioneSociale(_cliente.getRagioneSociale());
		cliente.setPartitaIva(_cliente.getPartitaIva());
		cliente.setEmail(_cliente.getEmail());
		cliente.setDataInserimento(_cliente.getDataInserimento());
		cliente.setDataUltimoContatto(_cliente.getDataUltimoContatto());
		cliente.setFatturatoAnnuale(_cliente.getFatturatoAnnuale());
		cliente.setPec(_cliente.getPec());
		cliente.setTelefono(_cliente.getTelefono());
		cliente.setEmailContatto(_cliente.getEmailContatto());
		cliente.setNomeContatto(_cliente.getNomeContatto());
		cliente.setCognomeContatto(_cliente.getCognomeContatto());
		cliente.setTelefonoContatto(_cliente.getTelefonoContatto());
		cliente.setTipoCliente(_cliente.getTipoCliente());
		cliente.setIndirizzi(_cliente.getIndirizzi());
		cliente.setFatture(_cliente.getFatture());
		
		cs.save(cliente);
		
		return new ResponseEntity<Object>(cliente, HttpStatus.CREATED);
	}
	
	@DeleteMapping("clienti/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		Optional<Cliente> clienteObj = cs.getById(id);
		
		ResponseEntity<Object> check = checkExists(clienteObj);
		if(check != null) return check;
		
		cs.delete(clienteObj.get());
		
		return new ResponseEntity<>(
			String.format("Il Cliente con id %d è stato eliminato!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Cliente> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}
	
	@GetMapping("clienti/nome={nome}")
	public List<Cliente> nome(@PathVariable String nome) {
		return cs.findByNome(nome);
	}
	
	@GetMapping("clienti/fatturato={fatturato}")
	public List<Cliente> fatturato(@PathVariable double fatturato) {
		return cs.findByFatturatoAnnuale(fatturato);
	}
	
	@GetMapping("clienti/dataInserimento={dataInserimento}")
	public List<Cliente> dataInserimento(@PathVariable String dataInserimento) {
		return cs.findByDataInserimento(LocalDate.parse(dataInserimento));
	}
	
	@GetMapping("clienti/dataUltimoContatto={dataUltimoContatto}")
	public List<Cliente> dataUltimoContatto(@PathVariable String dataUltimoContatto) {
		return cs.findByDataUltimoContatto(LocalDate.parse(dataUltimoContatto));
	}
	
	

}
