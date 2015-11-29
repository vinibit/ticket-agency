package com.packtpub.as7development.chapter4.ejb;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

import com.packtpub.as7development.chapter4.model.Seat;
import com.packtpub.as7development.chapter4.service.PollerBean;

@Stateless
public class AutomaticSellerBean {
	
	private final static Logger logger = Logger.getLogger(AutomaticSellerBean.class.getName());
	
	@Inject
	private TheatreBox theatreBox;
	
	@Inject
	private PollerBean pollerBean;
	
	@Resource
	private TimerService timerService;
	
	@Schedule(year = "*", dayOfWeek = "*", hour = "*", minute = "*", second = "*/60", persistent = false)
	public void automaticCostumer() {
		int seatId = findSeat();
		if (seatId == -1) {
			pollerBean.setPollingActive(false);
			cancelTimers();
			logger.info("Scheduler gone!");
			return;
		}
		
		theatreBox.buyTicket(seatId);
		logger.info("Somebody just booked seat number " + (seatId + 1));
	}
	
	private int findSeat() {
		ArrayList<Seat> list = theatreBox.getSeatList();
		for (Seat s: list) {
			if (!s.isBooked()) {
				return s.getId() -1;
			}
		}
		return -1;
	}
	
	private void cancelTimers() {
		for (Timer timer : timerService.getTimers()) {
			timer.cancel();
		}
	}
	
}