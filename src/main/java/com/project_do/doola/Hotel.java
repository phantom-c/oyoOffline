package com.project_do.doola;

import org.springframework.stereotype.Component;

@Component
public class Hotel {
	
	private String coordinates;
	public void setCoordinates(String c) {
		this.coordinates=c;
	}
	public String getCoordinates() {
		return coordinates;
	}

}
