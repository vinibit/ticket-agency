package com.packtpub.as7development.chapter4.ejb;

import java.util.ArrayList;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.packtpub.as7development.chapter4.model.Seat;

@Singleton
@Startup
public class TheatreBox {
	private ArrayList<Seat> seatList;
	
	@Inject Event<Seat> seatEvent;

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
		Seat seat = getSeatList().get(seatId);
		seat.setBooked(true);
		seatEvent.fire(seat);
	}
}
