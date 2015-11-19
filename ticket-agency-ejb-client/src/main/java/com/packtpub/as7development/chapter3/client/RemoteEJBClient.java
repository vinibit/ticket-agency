package com.packtpub.as7development.chapter3.client;

import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.packtpub.as7development.chapter3.ejb.NotEnoughMoneyException;
import com.packtpub.as7development.chapter3.ejb.SeatBookedException;
import com.packtpub.as7development.chapter3.ejb.TheatreBooker;
import com.packtpub.as7development.chapter3.ejb.TheatreInfo;
import com.packtpub.as7development.chapter3.utils.IOUtils;

public class RemoteEJBClient {
	private static final Logger logger = Logger.getLogger(RemoteEJBClient.class.getName());

	private static final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();

	public static void main(String[] args) throws Exception {
		Logger.getLogger("org.jboss").setLevel(Level.SEVERE);
		Logger.getLogger("org.xnio").setLevel(Level.SEVERE);
		testRemoteEJB();
	}

	private static void testRemoteEJB() throws NamingException {
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

		final TheatreInfo info = lookupTheatreInfoEJB();
		final TheatreBooker booker = lookupTheatreBookerEJB();

		Future<String> futureResult = null;
		String command = "";

		while (true) {
			dumpWelcomeMessage();
			command = IOUtils.readLine("Enter Command > ");
			if (command.equals("book")) {
				int seatId = 0;
				try {
					seatId = IOUtils.readInt("Enter SeatId > ");
				} catch (NumberFormatException e1) {
					logger.info("Wrong seatid format!");
					continue;
				}
				try {
					String retVal = booker.bookSeat(seatId - 1);
					System.out.println(retVal);
				} catch (SeatBookedException e) {
					logger.info(e.getMessage());
					continue;
				} catch (NotEnoughMoneyException e) {
					logger.info(e.getMessage());
					continue;
				}
			} else if (command.equals("bookasync")) {

				int seatId = 0;

				try {
					seatId = IOUtils.readInt("Enter SeatId > ");
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					logger.info("Wrong seatid format!");
					continue;
				}

				try {
					futureResult = booker.bookSeatAsync(seatId - 1);
					logger.info("Booking issued. Verify your mail!");
				} catch (SeatBookedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotEnoughMoneyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (command.equals("mail")) {
				if (futureResult == null || (!futureResult.isDone())) {
					logger.info("No mail received!");
					continue;
				} else {
					try {
						String result = futureResult.get();
						logger.info("Last mail received: " + result);

					} catch (InterruptedException e) {
						logger.severe(e.getCause().getMessage());
					} catch (ExecutionException e) {
						logger.warning(e.getCause().getMessage());
					}
					continue;
				}
			} else if (command.equals("list")) {
				logger.info(info.printSeatList());
				continue;
			} else if (command.equals("quit")) {
				logger.info("Bye");
				break;
			} else {
				logger.info("Unknown command " + command);
			}
		}

	}

	private static TheatreInfo lookupTheatreInfoEJB() throws NamingException {
		final Context context = new InitialContext(jndiProperties);
		return (TheatreInfo) context
				.lookup("ejb:/ticket-agency-ejb//TheatreInfoBean!com.packtpub.as7development.chapter3.ejb.TheatreInfo");
	}

	private static TheatreBooker lookupTheatreBookerEJB() throws NamingException {
		final Context context = new InitialContext(jndiProperties);
		return (TheatreBooker) context.lookup(
				"ejb:/ticket-agency-ejb//TheatreBookerBean!com.packtpub.as7development.chapter3.ejb.TheatreBooker?stateful");
	}

	private static void dumpWelcomeMessage() {
		System.out.println("Theatre booking system");
		System.out.println("=====================================");
		System.out.println("Commands: book, bookasync, list, mail, quit");
	}
}
