package com.packtpub.as7development.chapter3.jpa;

public class Seat {
	
	private int id;
	private String name;
	private int price;
	
	public Seat(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setBooked(boolean b) {
		// TODO Auto-generated method stub
	}

	public boolean isBooked() {
		// TODO Auto-generated method stub
		return false;
	}

}
