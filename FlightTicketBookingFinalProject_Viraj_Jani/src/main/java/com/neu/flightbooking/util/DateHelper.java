package com.neu.flightbooking.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String convertDate(String atime) {

		System.out.println("Got Model: " + atime);
		System.out.println("DATE: " + new Date());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mysqlDateString = formatter.format(new Date()) + " " + atime + ":00";
		System.out.println("mysqlDateString: " + mysqlDateString);

		return mysqlDateString;

	}
}
