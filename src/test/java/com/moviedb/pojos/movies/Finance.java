package com.moviedb.pojos.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Finance {
    private Long budget;
    private Long boxOffice;
    private Long profit;
    private String currency;
    
	/*
	 * public Finance() { }
	 * 
	 * public Finance(Long budget, Long boxOffice, Long profit, String currency) {
	 * this.budget = budget; this.boxOffice = boxOffice; this.profit = profit;
	 * this.currency = currency; }
	 */
    
}
