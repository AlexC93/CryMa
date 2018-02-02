package ro.wade.cryma.internalDB.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class HistoricalValuesRetriever {

	public static String HISTORICAL_VALUES_FOLDER_PATH = "Top100Cryptos";

	public static Map<Date, Double> getHistoricalValues(String cryptocurrency) {

		Map<Date, Double> values = new TreeMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(HISTORICAL_VALUES_FOLDER_PATH + "/" + cryptocurrency + ".csv");
			br = new BufferedReader(fr);
			String sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String[] chunks = sCurrentLine.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				String unformattedDate = chunks[0].substring(1, chunks[0].length() - 1);
				Date date = sdf.parse(unformattedDate);
				Double value = Double.valueOf(chunks[4]);
				values.put(date, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return values;
	}

}
