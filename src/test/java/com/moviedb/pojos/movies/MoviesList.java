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
public class MoviesList {
	private int page;
	private int limit;
	private int total;
	private int totalPages;
	private List<Movie> data;

	/*
	public MoviesList() {
	}

	public MoviesList(int page, int limit, int total, int totalPages, List<Movie> data) {
		this.page = page;
		this.limit = limit;
		this.total = total;
		this.totalPages = totalPages;
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public MoviesList setPage(int page) {
		this.page = page;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public MoviesList setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public MoviesList setTotal(int total) {
		this.total = total;
		return this;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public MoviesList setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		return this;
	}

	public List<Movie> getData() {
		return data;
	}

	public MoviesList setData(List<Movie> data) {
		this.data = data;
		return this;
	}
*/
}
