package com.example.swarm.model;

public enum TargetType {
	
	RADAR("Radar"),
	SAM("SAM"),
	ARTILLERY("Artillery");
	
	String label; 
	private TargetType(String pLabel) {
		label = pLabel;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
