package com.moviedb.pojos.movies;

public class Crew {
	private String director;
	private String musicDirector;
	private String productionCompany;

	private Crew() { }
	
	public Crew(String director, String musicDirector, String productionCompany) {
		this.director = director;
		this.musicDirector = musicDirector;
		this.productionCompany = productionCompany;
	}

	public String getDirector() {
		return director;
	}

	public Crew setDirector(String director) {
		this.director = director;
		return this;
	}

	public String getMusicDirector() {
		return musicDirector;
	}

	public Crew setMusicDirector(String musicDirector) {
		this.musicDirector = musicDirector;
		return this;
	}

	public String getProductionCompany() {
		return productionCompany;
	}

	public Crew setProductionCompany(String productionCompany) {
		this.productionCompany = productionCompany;
		return this;
	}

}
