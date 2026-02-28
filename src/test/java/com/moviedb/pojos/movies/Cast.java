package com.moviedb.pojos.movies;

import java.util.List;

public class Cast {
	
	private String actorName;
	private String role;
	private List<String> awards;

    public Cast() { }
    
    public Cast(String actorName, String role, List<String> awards) {
		this.actorName = actorName;
		this.role = role;
		this.awards = awards;
	}
	
    public String getActorName() {
		return actorName;
	}

	public Cast setActorName(String actorName) {
		this.actorName = actorName;
		return this;
	}
	public String getRole() {
		return role;
	}
	public Cast setRole(String role) {
		this.role = role;
		return this;
	}
	public List<String> getAwards() {
		return awards;
	}
	public Cast setAwards(List<String> awards) {
		this.awards = awards;
		return this;
	}
    
}
