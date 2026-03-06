package com.moviedb.pojos.movies;

import java.util.List;

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
public class MoviesSearch {
	private int total;
	private List<Movie> data;

/*	public MoviesSearch() {
	}

	public MoviesSearch(int page, int limit, int total, int totalPages, List<Movie> data) {
		this.total = total;
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public MoviesSearch setTotal(int total) {
		this.total = total;
		return this;
	}

	public List<Movie> getData() {
		return data;
	}

	public MoviesSearch setData(List<Movie> data) {
		this.data = data;
		return this;
	}
*/
}
