package com.packtpub.as7development.chapter4.ejb;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.packtpub.as7development.chapter4.model.Seat;

@Singleton
@Startup
public class TheatreBox {
	private ArrayList<Seat> seatList;
	
	@Inject Event<Seat> seatEvent;
	
	@PostConstruct
	public void setupTheatre() {
		seatList = new ArrayList<Seat>();
		int seatId = 0;
		for (int i = 0; i < 5; i++) {
			Seat seat = new Seat(++seatId, "Stalls", 40);
			seatList.add(seat);
		}
		for (int i = 0; i < 5; i++) {
			Seat seat = new Seat(++seatId, "Circle", 20);
			seatList.add(seat);
		}
		for (int i = 0; i < 5; i++) {
			Seat seat = new Seat(++seatId, "Balcony", 10);
			seatList.add(seat);
		}
	}

	@Lock(LockType.READ)
	public ArrayList<Seat> getSeatList() {
		return seatList;
	}

	@Lock(LockType.READ)
	public int getSeatPrice(int id) {
		return getSeatList().get(id).getPrice();
	}

	@Lock(LockType.WRITE)
	public void buyTicket(int seatId) {
		Seat seat = getSeatList().get(seatId - 1);
		seat.setBooked(true);
		seatEvent.fire(seat);
	}
}
