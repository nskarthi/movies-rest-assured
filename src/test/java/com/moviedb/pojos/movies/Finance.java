package com.moviedb.pojos.movies;

public class Finance {
    private Long budget;
    private Long boxOffice;
    private Long profit;
    private String currency;
    
    public Finance() { }
    
	public Finance(Long budget, Long boxOffice, Long profit, String currency) {
		this.budget = budget;
		this.boxOffice = boxOffice;
		this.profit = profit;
		this.currency = currency;
	}

	public Long getBudget() {
		return budget;
	}

	public Finance setBudget(Long budget) {
		this.budget = budget;
		return this;
	}

	public Long getBoxOffice() {
		return boxOffice;
	}

	public Finance setBoxOffice(Long boxOffice) {
		this.boxOffice = boxOffice;
		return this;
	}

	public Long getProfit() {
		return profit;
	}

	public Finance setProfit(Long profit) {
		this.profit = profit;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public Finance setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
    
}
