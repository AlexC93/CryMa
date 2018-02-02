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

@ManagedBean(name = "compareBean")
@ViewScoped
public class CompareBean implements Serializable {

	private static final long serialVersionUID = -2314487163800273630L;
	private Cryptocurrency selectedCurrency;
	private List<Cryptocurrency> availableCurrencies;
	private List<Currency> selectedCurrencies;
	private LineChartModel chartModel;

	@PostConstruct
	public void init() {
		chartModel = new LineChartModel();
		LineChartSeries series = new LineChartSeries();
		series.setLabel("Bitcoin");
		series.set(1, 50);
		series.set(10, -50);
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Monero");
		series1.set(40, 65);
		series1.set(24, -25);
		chartModel.addSeries(series);
		chartModel.addSeries(series1);
		chartModel.setTitle("Cryptocurrencies comparison");
		chartModel.setLegendPosition("e");
		Axis yAxis = chartModel.getAxis(AxisType.Y);
		yAxis.setMin(-100);
		yAxis.setMax(100);
	}

	public Cryptocurrency getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(Cryptocurrency selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	public List<Cryptocurrency> getAvailableCurrencies() {
		return availableCurrencies;
	}

	public void setAvailableCurrencies(List<Cryptocurrency> availableCurrencies) {
		this.availableCurrencies = availableCurrencies;
	}
	
	public List<Currency> getSelectedCurrencies() {
		return selectedCurrencies;
	}

	public void setSelectedCurrencies(List<Currency> selectedCurrencies) {
		this.selectedCurrencies = selectedCurrencies;
	}

	public LineChartModel getChartModel() {
		return chartModel;
	}

	public void setChartModel(LineChartModel chartModel) {
		this.chartModel = chartModel;
	}
	
	public void add() {
		
	}

	public void compareDay() {

	}

	public void compareWeek() {

	}

	public void compareMonth() {

	}

	public void compareYear() {

	}

	public void percentageValue() {

	}
}