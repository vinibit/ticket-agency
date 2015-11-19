package com.packtpub.as7development.chapter4.model;

public class Seat {

	private int id;
	private String name;
	private int price;
	private boolean booked;

	public Seat(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public void setBooked(boolean b) {
		this.booked = b;
	}

	public boolean isBooked() {
		return booked;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", name=" + this.name + ", price=" + this.price + ", booked=" + this.booked + "]";

	}
}
