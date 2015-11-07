package com.packtpub.as7development.chapter3.ejb;

import javax.ejb.Remote;

@Remote
public interface TheatreBooker {
	String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException;
}
