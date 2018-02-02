package ro.wade.cryma.internalDB.beans;

import java.util.Date;

public class HistoricalValue {

	private String cryptocoin;
	private double value;
	private Date date;

	public String getCryptocoin() {
		return cryptocoin;
	}

	public void setCryptocoin(String cryptocoin) {
		this.cryptocoin = cryptocoin;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
