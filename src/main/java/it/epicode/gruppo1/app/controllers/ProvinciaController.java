package it.epicode.gruppo1.app.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.exceptions.ResponseMessage;
import it.epicode.gruppo1.app.services.ProvinciaService;

@RestController
@RequestMapping("/")
public class ProvinciaController {
	
	@Autowired
	ProvinciaService ps;


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
}
