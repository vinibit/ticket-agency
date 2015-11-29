package com.packtpub.as7development.chapter4.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.packtpub.as7development.chapter4.ejb.TheatreBox;
import com.packtpub.as7development.chapter4.model.Seat;

@Named
@SessionScoped
public class TheatreBookerBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TheatreBookerBean.class);
	
	private int money;
	@Inject TheatreBox theatreBox;
	
	@PostConstruct
	public void createCustomer() {
		money = 100;
	}
	
	public void bookSeat(int seatId) {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		logger.info("Booking seat " + seatId);
		Seat seat = theatreBox.getSeatList().get(seatId);
		
		if (seat.isBooked()) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seat Already booked!", "");
			fc.addMessage(null, m);
			return;
		}
		if (seat.getPrice() > money) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seat Already booked!", "");
			fc.addMessage(null, m);
			return;
		}
		
		theatreBox.buyTicket(seatId);
		money = money - seat.getPrice();
		
		logger.info("Seat " + (seatId + 1) + " booked.");
		
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Booked.", "Booking succesful!");
		fc.addMessage(null, m);
		return;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
}
