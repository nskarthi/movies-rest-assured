package com.moviedb.pojos.movies;

import java.util.List;

public class Movies {
	private int page;
	private int limit;
	private int total;
	private int totalPages;
	private List<Movie> data;

	public Movies() {
	}

	public Movies(int page, int limit, int total, int totalPages, List<Movie> data) {
		this.page = page;
		this.limit = limit;
		this.total = total;
		this.totalPages = totalPages;
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public Movies setPage(int page) {
		this.page = page;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public Movies setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public Movies setTotal(int total) {
		this.total = total;
		return this;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public Movies setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		return this;
	}

	public List<Movie> getData() {
		return data;
	}

	public Movies setData(List<Movie> data) {
		this.data = data;
		return this;
	}

}
