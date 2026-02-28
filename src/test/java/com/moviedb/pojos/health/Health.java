package com.moviedb.pojos.health;

public class Health {
	String status;
	String timestamp;

	public Health() { }
	
	public Health(String status, String timestamp) {
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void SetTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
