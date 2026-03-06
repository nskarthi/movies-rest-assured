package com.moviedb.pojos.movies;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(exclude = "id") // Exclude 'id' so it doesn't affect equality
//@Accessors(chain = true) // This makes setters return 'this' instead of 'void'.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {

	// WRITE_ONLY: This allows Jackson to set the ID when the API response sends it back to you (deserialization),
	// but it will skip the field when you send the request ".body(myObject).post()"
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int id;

	private String title;
	private Crew crew;
	private List<Cast> cast;
	private List<String> genres;
	private ReleaseDetails releaseDetails;
	private Finance finance;

/*	public Movie() {
	}

	// Constructor with the movie id for PUT and DESERIALISATION
	public Movie(int id, String title, Crew crew, List<Cast> cast, List<String> genres, ReleaseDetails releaseDetails,
			Finance finance) {
		this.id = id;
		this.title = title;
		this.crew = crew;
		this.cast = cast;
		this.genres = genres;
		this.releaseDetails = releaseDetails;
		this.finance = finance;
	}

	// Constructor without the movie id for POST (serialization)
	public Movie(String title, Crew crew, List<Cast> cast, List<String> genres, ReleaseDetails releaseDetails,
			Finance finance) {
		this.title = title;
		this.crew = crew;
		this.cast = cast;
		this.genres = genres;
		this.releaseDetails = releaseDetails;
		this.finance = finance;
	}

	public int getId() {
		return id;
	}

	public Movie setId(int id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Movie setTitle(String title) {
		this.title = title;
		return this;
	}

	public Crew getCrew() {
		return crew;
	}

	public Movie setCrew(Crew crew) {
		this.crew = crew;
		return this;
	}

	public List<Cast> getCast() {
		return cast;
	}

	public Movie setCast(List<Cast> cast) {
		this.cast = cast;
		return this;
	}

	public List<String> getGenres() {
		return genres;
	}

	public Movie setGenres(List<String> genres) {
		this.genres = genres;
		return this;
	}

	public ReleaseDetails getReleaseDetails() {
		return releaseDetails;
	}

	public Movie setReleaseDetails(ReleaseDetails releaseDetails) {
		this.releaseDetails = releaseDetails;
		return this;
	}

	public Finance getFinance() {
		return finance;
	}

	public Movie setFinance(Finance finance) {
		this.finance = finance;
		return this;
	}
*/
}
