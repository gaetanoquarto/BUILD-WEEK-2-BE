package it.epicode.gruppo1.app.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

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
	
	static Provincia provinciaId;

	
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

}
