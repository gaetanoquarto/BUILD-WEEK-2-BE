package it.epicode.gruppo1.app.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.gruppo1.app.entities.Provincia;

public class CsvHelperProvincia {
	
	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "Sigla", "Provincia", "Regione" };

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<Provincia> csvToProvincia(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	      List<Provincia> province = new ArrayList<Provincia>();

	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	        Provincia provincia = new Provincia(
	              csvRecord.get("Sigla"),
	              csvRecord.get("Provincia"),
	              csvRecord.get("Regione")
	              );

	        province.add(provincia);
	      }

	      return province;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	  }

}
