package com.packtpub.as7development.chapter3.ejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.jboss.logging.Logger;

import com.packtpub.as7development.chapter3.jpa.Seat;

@Stateful
@Remote(TheatreBookerBean.class)
public class TheatreBookerBean implements TheatreBooker {
	private static final Logger logger = Logger.getLogger(TheatreBookerBean.class);
	
	int money;
	@EJB TheatreBox box;
	
	public void createCustomer() {
		money = 100;
	}
	
	@Override
	public String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException {
		Seat seat = box.getSeatList().get(seatId);
		
		if (seat.isBooked()) {
			throw new SeatBookedException("Seat Already booked!");
		}
		if (seat.getPrice() > money) {
			throw new NotEnoughMoneyException("You don't have enough money to buy this ticket!");
		}
		
		box.buyTicket(seat);
		money = money - seat.getPrice();
		
		logger.info("Seat " + seatId + " booked.");
		return "Seat " + seatId + " booked.";
	}

}
