package ro.wade.cryma.internalDB.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.sparql.util.Utils;
import org.apache.log4j.Logger;

import ro.wade.cryma.internalDB.beans.Comment;
import ro.wade.cryma.internalDB.beans.Cryptocurrency;
import ro.wade.cryma.internalDB.beans.HistoricalValue;
import ro.wade.cryma.internalDB.constants.Constants;
import ro.wade.cryma.internalDB.constants.Properties;

/**
 * Main class that manages a single instance of Jena's Model class. Contains all
 * RDF data stored on the server.
 * 
 * @author Alex
 */
public class MainModel {
	private Model model;
	private Property pLabel;
	private Property pInstanceOf;
	private Property pRepository;
	private Property pLogoImage;
	private Property pShortName;
	private Property pUnicodeCharacter;
	private Property pOfficialWebsite;
	private Property pSubreddit;
	private Property pQuoraTopicID;
	private Property pHadValue;
	private Property pHasComment;
	private Property pAtDate;
	private Property pValue;
	private Property pComment;
	private Logger logger = Logger.getLogger(getClass());

	public MainModel() throws IOException {

		Dataset dataset = TDBFactory.assembleDataset("tdb-assembler.ttl");
		logger.info("Loading data");
		dataset.begin(ReadWrite.WRITE);
		model = dataset.getDefaultModel();
		if (dataset.isEmpty()) {
			logger.info("Dataset empty");
			createNewData(dataset);
		} else {
			logger.info("Data already loaded");
		}
		model.write(System.out, "RDF/XML-ABBREV");
		dataset.commit();
		dataset.end();
	}

	/**
	 * Creates a new model if one does not already exist
	 */
	public void createNewModel() {
		List<Cryptocurrency> cryptocurrencies = CryptoRetriever.getCryptocurrencies();
		//List<HistoricalValue> historicalValues = loadHistoricalValues();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		// Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("wdt", "http://www.wikidata.org/prop/direct/");
		model.setNsPrefix("wikibase", "http://wikiba.se/ontology#");

		loadProperties(model);
		for (Cryptocurrency crypto : cryptocurrencies) {
			Resource cryptoResource = model.createResource(crypto.getURI());
			if (crypto.getLabel() != null) {
				cryptoResource.addLiteral(pLabel, crypto.getLabel());
			}
			if (crypto.getSourceCodeRepository() != null) {
				cryptoResource.addLiteral(pRepository, crypto.getSourceCodeRepository());
			}
			if (crypto.getLogo() != null) {
				cryptoResource.addLiteral(pLogoImage, crypto.getLogo());
			}
			if (crypto.getShortName() != null) {
				cryptoResource.addLiteral(pShortName, crypto.getShortName());
			}
			if (crypto.getSymbol() != null) {
				cryptoResource.addLiteral(pUnicodeCharacter, crypto.getSymbol());
			}
			if (crypto.getOfficialWebsite() != null) {
				cryptoResource.addLiteral(pOfficialWebsite, crypto.getOfficialWebsite());
			}
			if (crypto.getSubreddit() != null) {
				cryptoResource.addLiteral(pSubreddit, crypto.getSubreddit());
			}
			if (crypto.getQuora() != null) {
				cryptoResource.addLiteral(pQuoraTopicID, crypto.getQuora());
			}
			
			// Adding the historical values
//			Map<Date, Double> valuesForCoin = getHistoricalValuesForCoin(historicalValues, crypto.getLabel());
			Map<Date, Double> valuesForCoin = HistoricalValuesRetriever.getHistoricalValues(crypto.getLabel());
			for(Date date: valuesForCoin.keySet()) {
				Resource historicalValue = model.createResource();
				historicalValue.addLiteral(pAtDate, sdf.format(date));
				historicalValue.addLiteral(pValue, valuesForCoin.get(date));
				cryptoResource.addProperty(pHadValue, historicalValue);
			}
		}
	}

	private void loadProperties(Model model) {
		pLabel = model.createProperty(Properties.LABEL);
		pInstanceOf = model.createProperty(Properties.INSTANCE_OF);
		pRepository = model.createProperty(Properties.REPOSITORY);
		pLogoImage = model.createProperty(Properties.LOGO_IMAGE);
		pShortName = model.createProperty(Properties.SHORT_NAME);
		pUnicodeCharacter = model.createProperty(Properties.UNICODE_CHARACTER);
		pOfficialWebsite = model.createProperty(Properties.OFFICIAL_WEBSITE);
		pSubreddit = model.createProperty(Properties.SUBREDDIT);
		pQuoraTopicID = model.createProperty(Properties.QUORA_TOPIC_ID);
		pHadValue = model.createProperty(Properties.CRYMA_HAD_VALUE);
		pHasComment = model.createProperty(Properties.CRYMA_HAS_COMMENT);
		pAtDate = model.createProperty(Properties.CRYMA_AT_DATE);
		pValue = model.createProperty(Properties.CRYMA_VALUE);
		pComment = model.createProperty(Properties.CRYMA_COMMENT);
	}

	private void createNewData(Dataset dataset) throws IOException {
		logger.info("Loading new data");
		createNewModel();
		savePlainData(model, "plainData");
		logger.info("New data saved");
	}

	/**
	 * Used only to be able to see the data loaded without Jena TDB
	 * 
	 * @param model
	 */
	private void savePlainData(Model model, String path) {
		try (OutputStream rdfStream = new FileOutputStream(path + ".rdf");
				OutputStream ttlStream = new FileOutputStream(path + ".ttl")) {
			model.write(rdfStream, "RDF/XML-ABBREV");
			model.write(ttlStream, "TTL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<HistoricalValue> loadHistoricalValues() {
		List<HistoricalValue> historicalValues = new ArrayList<>();
		// DUMMY VALUES
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
		HistoricalValue value = new HistoricalValue();
		value.setCryptocoin("Bitcoin");
		try {
			value.setDate(df.parse("10-10-2017"));
		} catch (ParseException e) {
			e.printStackTrace();
			value.setDate(new Date());
		}
		value.setValue((double)7.5);
		historicalValues.add(value);
		
		value = new HistoricalValue();
		value.setCryptocoin("Bitcoin");
		value.setDate(new Date());
		value.setValue((double)10.3);
		historicalValues.add(value);
		
		return historicalValues;
	}

	private Map<Date, Double> getHistoricalValuesForCoin(List<HistoricalValue> historicalValues, String cryptocoin) {
		Map<Date, Double> values = new TreeMap<>();
		for(HistoricalValue historicalValue: historicalValues) {
			if(historicalValue.getCryptocoin().toLowerCase().equals(cryptocoin.toLowerCase())) {
				values.put(historicalValue.getDate(), historicalValue.getValue());
			}
		}
		return values;
	}

}
