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

import it.epicode.gruppo1.app.entities.Comune;
import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.exceptions.ResponseMessage;
import it.epicode.gruppo1.app.services.ProvinciaService;

@RestController
@RequestMapping("/")
public class ProvinciaController {
	
	@Autowired
	ProvinciaService ps;

	// Metodo per inserire le province da file csv nel database
	@PostMapping("province")
	public String uploadData(@RequestParam("file") MultipartFile file) throws Exception{
		
		List<Provincia> province = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.detectFormatAutomatically();
		setting.setHeaderExtractionEnabled(true);
		CsvParser csvParser = new CsvParser(setting);
		List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);
		parseAllRecords.forEach(record -> {
			Provincia provincia = new Provincia();
			provincia.setSigla(record.getString("Sigla"));
			provincia.setProvincia(record.getString("Provincia"));
			provincia.setRegione(record.getString("Regione"));
			province.add(provincia);
		});
		ps.saveAll(province);
		return "Upload effettuato!";
	}
	
	@GetMapping("province")
	public ResponseEntity<List<Provincia>> getProvince() {
		List<Provincia> province = ps.getAll();
		
		return new ResponseEntity<>(province, HttpStatus.CREATED);
	}
	
	@GetMapping("province/{id}")
	public ResponseEntity<Object> getProvinciaById(@PathVariable int id) {
		Optional<Provincia> provinciaObj = ps.getById(id);
		
		ResponseEntity<Object> check = checkExists(provinciaObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(provinciaObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("province_page")
	public ResponseEntity<Object> getProvinceInPages(Pageable pageable) {
		Page<Provincia> province = ps.getAll_page(pageable);
		
		if(province.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(province, HttpStatus.OK);
	}
	
//	@PostMapping("province")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<Object> createProvincia(@RequestBody Provincia p) {
//		Provincia provincia = ps.save(p);
//		
//		return new ResponseEntity<Object>(provincia, HttpStatus.CREATED);
//	}
	
	@PutMapping("province/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateProvincia(@PathVariable int id, @RequestBody Provincia _provincia) {
		Optional<Provincia> provinciaObj = ps.getById(id);
		
		ResponseEntity<Object> check = checkExists(provinciaObj);
		if(check != null) return check;
		
		Provincia provincia = provinciaObj.get();
		
		provincia.setSigla(_provincia.getSigla());
		provincia.setProvincia(_provincia.getProvincia());
		provincia.setRegione(_provincia.getRegione());
		
		ps.save(provincia);
		
		return new ResponseEntity<Object>(provincia, HttpStatus.CREATED);
	}
	
	@DeleteMapping("province/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		Optional<Provincia> provinciaObj = ps.getById(id);
		
		ResponseEntity<Object> check = checkExists(provinciaObj);
		if(check != null) return check;
		
		ps.delete(provinciaObj.get());
		
		return new ResponseEntity<>(
			String.format("La Provincia con id %d è stata eliminata!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Provincia> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}

}
