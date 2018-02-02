package view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import model.Cryptocurrency;
import model.Currency;

@ManagedBean(name = "currencyBean")
@ViewScoped
public class CurrencyBean implements Serializable {

	private static final long serialVersionUID = -5228935839430718683L;
	private List<Cryptocurrency> availableCryptocurrencies;
	private List<Cryptocurrency> availableCurrencies;
	private Cryptocurrency selectedCryptocurrency;
	private Currency selectedCurrency;
	private LineChartModel cryptocurrencyChartModel;
	private List<Cryptocurrency> cryptocurrencies;

	@PostConstruct
	public void init() {
		initializeChart();
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
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("EU Euro");
		series2.set(10, 30);
		series2.set(15, 80);
		model.addSeries(series1);
		model.addSeries(series2);
		return model;
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

	public Cryptocurrency getSelectedCryptocurrency() {
		return selectedCryptocurrency;
	}

	public void setSelectedCryptocurrency(Cryptocurrency selectedCryptocurrency) {
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

	public List<Cryptocurrency> getCryptocurrencies() {
		return cryptocurrencies;
	}

	public void setCryptocurrencies(List<Cryptocurrency> cryptocurrencies) {
		this.cryptocurrencies = cryptocurrencies;
	}
}