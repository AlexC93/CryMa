package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import model.Cryptocurrency;
import model.Currency;
import ro.wade.cryma.InternalDBInteractor.CryptoRetriever;
import ro.wade.cryma.InternalDBInteractor.constants.Constants;

@ManagedBean(name = "detailsBean")
@ViewScoped
public class DetailsBean implements Serializable {

	private static final long serialVersionUID = 3312647227363333552L;
	private List<Cryptocurrency> availableCryptocurrencies;
	private List<Cryptocurrency> availableCurrencies;
	private ro.wade.cryma.InternalDBInteractor.Cryptocurrency selectedCryptocurrency;
	private Currency selectedCurrency;
	private LineChartModel cryptocurrencyChartModel;
	private String comment;
	private List<String> comments;
	List<ro.wade.cryma.InternalDBInteractor.Cryptocurrency> detailedCryptocurrencies;

	@PostConstruct
	public void init() {
		detailedCryptocurrencies = CryptoRetriever.getCryptocurrencies(Constants.INTERNALDB_SPARQL_ENDPOINT);
		System.out.println(detailedCryptocurrencies.get(0));
		initializeChart();
		comments = new ArrayList<String>();
	}

	private void initializeChart() {
		cryptocurrencyChartModel = createLineModel();
		cryptocurrencyChartModel.setTitle("Bitcoin");
		cryptocurrencyChartModel.setLegendPosition("e");
		Axis yAxis = cryptocurrencyChartModel.getAxis(AxisType.Y);
		yAxis.setMin(-100);
		yAxis.setMax(100);
	}

	private LineChartModel createLineModel() {
		LineChartModel model = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Bitcoin");
		series1.set(1, 50);
		series1.set(10, -50);
		model.addSeries(series1);
		return model;
	}
	
	public void addComment() {
		comments.add(comment);
		System.out.println(comment);
	}
	
	public List<Cryptocurrency> getAvailableCryptocurrencies() {
		return availableCryptocurrencies;
	}

	public void setAvailableCryptocurrencies(List<Cryptocurrency> availableCryptocurrencies) {
		this.availableCryptocurrencies = availableCryptocurrencies;
	}

	public List<Cryptocurrency> getAvailableCurrencies() {
		return availableCurrencies;
	}

	public void setAvailableCurrencies(List<Cryptocurrency> availableCurrencies) {
		this.availableCurrencies = availableCurrencies;
	}

	public ro.wade.cryma.InternalDBInteractor.Cryptocurrency getSelectedCryptocurrency() {
		return selectedCryptocurrency;
	}

	public void setSelectedCryptocurrency(ro.wade.cryma.InternalDBInteractor.Cryptocurrency selectedCryptocurrency) {
		this.selectedCryptocurrency = selectedCryptocurrency;
	}

	public Currency getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(Currency selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	public LineChartModel getCryptocurrencyChartModel() {
		return cryptocurrencyChartModel;
	}

	public void setCryptocurrencyChartModel(LineChartModel cryptocurrencyChartModel) {
		this.cryptocurrencyChartModel = cryptocurrencyChartModel;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public List<ro.wade.cryma.InternalDBInteractor.Cryptocurrency> getDetailedCryptocurrencies() {
		return detailedCryptocurrencies;
	}

	public void setDetailedCryptocurrencies(
			List<ro.wade.cryma.InternalDBInteractor.Cryptocurrency> detailedCryptocurrencies) {
		this.detailedCryptocurrencies = detailedCryptocurrencies;
	}
}