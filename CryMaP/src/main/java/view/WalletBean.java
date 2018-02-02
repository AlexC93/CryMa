package view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import model.Cryptocurrency;
import model.Currency;

@ManagedBean(name = "walletBean")
@ViewScoped
public class WalletBean implements Serializable {

	private static final long serialVersionUID = 3569847654274571712L;
	private List<Cryptocurrency> availableCryptocurrencies;
	private List<Currency> availableCurrencies;
	private Cryptocurrency selectedCryptocurrency;
	private Currency selectedCurrency;
	private double total;
	private List<Currency> walletContents;

	@PostConstruct
	public void init() {
	}
	
	public void add() {
		
	}

	public List<Cryptocurrency> getAvailableCryptocurrencies() {
		return availableCryptocurrencies;
	}

	public void setAvailableCryptocurrencies(List<Cryptocurrency> availableCryptocurrencies) {
		this.availableCryptocurrencies = availableCryptocurrencies;
	}

	public List<Currency> getAvailableCurrencies() {
		return availableCurrencies;
	}

	public void setAvailableCurrencies(List<Currency> availableCurrencies) {
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<Currency> getWalletContents() {
		return walletContents;
	}

	public void setWalletContents(List<Currency> walletContents) {
		this.walletContents = walletContents;
	}
}