package com.packtpub.as7development.chapter3.ejb;

import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.jboss.logging.Logger;

import com.packtpub.as7development.chapter3.jpa.Seat;

@Stateful
public class TheatreBookerBean implements TheatreBooker {
	private static final Logger logger = Logger.getLogger(TheatreBookerBean.class);
	
	int money;
	@EJB TheatreBox theatreBox;
	
	@PostConstruct
	public void createCustomer() {
		money = 100;
	}
	
	@Override
	public String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException {
		Seat seat = theatreBox.getSeatList().get(seatId);
		
		if (seat.isBooked()) {
			throw new SeatBookedException("Seat Already booked!");
		}
		if (seat.getPrice() > money) {
			throw new NotEnoughMoneyException("You don't have enough money to buy this ticket!");
		}
		
		theatreBox.buyTicket(seatId);
		money = money - seat.getPrice();
		
		logger.info("Seat " + (seatId + 1) + " booked.");
		return "Seat " + (seatId + 1) + " booked.";
	}
	
	@Override
	@Asynchronous
	public Future<String> bookSeatAsync(int seatId) throws SeatBookedException, NotEnoughMoneyException {
		Seat seat = theatreBox.getSeatList().get(seatId);
		
		if (seat.isBooked()) {
			throw new SeatBookedException("Seat Already booked!");
		}
		if (seat.getPrice() > money) {
			throw new NotEnoughMoneyException("You don't have enough money to buy this ticket!");
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		logger.info("Booking issued");
		theatreBox.buyTicket(seatId);
		
		money = money - seat.getPrice();
		
		return new AsyncResult<String>("Booked seat: " + seat + " - Money left: " + money); 
	}
}
