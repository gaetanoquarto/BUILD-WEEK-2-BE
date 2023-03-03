package it.epicode.gruppo1.app.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import it.epicode.gruppo1.app.entities.Cliente;
import it.epicode.gruppo1.app.entities.Comune;
import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.services.ComuneService;
import it.epicode.gruppo1.app.services.ProvinciaService;

@RestController
@RequestMapping("/")
public class ComuneController {
	
	@Autowired
	private ComuneService cs;
	
	@Autowired
	private ProvinciaService ps;

	// Metodo per inserire i comuni da file csv nel database
	@PostMapping("comuni")
	public String uploadData(@RequestParam("file") MultipartFile file) throws Exception{
		List<Comune> comuni = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.detectFormatAutomatically();
		setting.setHeaderExtractionEnabled(true);
		CsvParser csvParser = new CsvParser(setting);
		List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);
		parseAllRecords.forEach(record -> {
			Comune comune = new Comune();
			comune.setNome(record.getString("Denominazione in italiano"));
			comune.setCodProvincia(Integer.parseInt(record.getString("Codice Provincia (Storico)(1)")));
			comune.setProgComune(record.getString("Progressivo del Comune (2)"));
			
			List<Provincia> province = ps.getAll();
			String nomeProvincia = record.getString(3);
			
			
			for(int i = 0; i < province.size(); i++) {
				
				String nome = province.get(i).getProvincia();
				if(nome.equals(nomeProvincia)) {
					comune.setProvincia(province.get(i));
				}
			}

			comuni.add(comune);
			
		});
		cs.saveAll(comuni);
		return "Upload effettuato!";
	}
	
	@GetMapping("comuni")
	public ResponseEntity<List<Comune>> getComuni() {
		List<Comune> comuni = cs.getAll();
		
		return new ResponseEntity<>(comuni, HttpStatus.CREATED);
	}
	
	@GetMapping("comuni/{id}")
	public ResponseEntity<Object> getComuneById(@PathVariable int id) {
		Optional<Comune> comuneObj = cs.getById(id);
		
		ResponseEntity<Object> check = checkExists(comuneObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(comuneObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("comuni_page")
	public ResponseEntity<Object> getComuniInPages(Pageable pageable) {
		Page<Comune> comuni = cs.getAll_page(pageable);
		
		if(comuni.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(comuni, HttpStatus.OK);
	}
	
//	@PostMapping("comuni")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<Object> createComune(@RequestBody Comune c) {
//		Comune comune = cs.save(c);
//		
//		return new ResponseEntity<Object>(comune, HttpStatus.CREATED);
//	}
	
	@PutMapping("comuni/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateCliente(@PathVariable int id, @RequestBody Comune _comune) {
		Optional<Comune> comuneObj = cs.getById(id);
		
		ResponseEntity<Object> check = checkExists(comuneObj);
		if(check != null) return check;
		
		Comune comune = comuneObj.get();
		
		comune.setNome(_comune.getNome());
		comune.setCodProvincia(_comune.getCodProvincia());
		comune.setProgComune(_comune.getProgComune());
		comune.setProvincia(_comune.getProvincia());
		
		cs.save(comune);
		
		return new ResponseEntity<Object>(comune, HttpStatus.CREATED);
	}
	
	@DeleteMapping("comuni/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		Optional<Comune> comuneObj = cs.getById(id);
		
		ResponseEntity<Object> check = checkExists(comuneObj);
		if(check != null) return check;
		
		cs.delete(comuneObj.get());
		
		return new ResponseEntity<>(
			String.format("Il Comune con id %d è stato eliminato!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Comune> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}

}
