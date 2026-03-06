package com.moviedb.pojos.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ReleaseDetails {
	String language;
	String country;

/*	public ReleaseDetails(String language, String country) {
		this.language = language;
		this.country = country;
	}
	
	public ReleaseDetails() { }
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
*/
}
