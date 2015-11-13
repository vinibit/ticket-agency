package com.packtpub.as7development.chapter3.ejb;

import java.util.concurrent.Future;

import javax.ejb.Asynchronous;
import javax.ejb.Remote;

@Remote
public interface TheatreBooker {
	String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException;
	@Asynchronous
	Future<String> bookSeatAsync(int seatId) throws SeatBookedException, NotEnoughMoneyException;
}
