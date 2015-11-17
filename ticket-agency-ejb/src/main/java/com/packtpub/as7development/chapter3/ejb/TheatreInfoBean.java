package com.packtpub.as7development.chapter3.ejb;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.packtpub.as7development.chapter3.jpa.Seat;

/**
 * Session Bean implementation class TheatreInfoBean
 */
@Stateless
@Remote(TheatreInfo.class)
public class TheatreInfoBean implements TheatreInfo {
	
	@EJB TheatreBox box;
	
	@Override
	public String printSeatList() {
		ArrayList<Seat> seats = box.getSeatList();
		StringBuffer buffer = new StringBuffer();
		for (Seat seat : seats) {
			buffer.append(seat );
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
