package com.sentinel.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test {

	public static void main(String[] args) {
		currentTime();
		currentDate();
		currentWeekDay();
	}

	public static String currentTime() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy
		// hh.mm.ss.S aa");
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");

		String formattedDate = dateFormat.format(new Date()).toString();
		System.out.println(formattedDate);
		return formattedDate;
	}

	public static String currentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");

		String formattedDate = dateFormat.format(new Date()).toString();
		System.out.println(formattedDate);
		return formattedDate;

	}

	public static String currentWeekDay() {

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
		System.out.println(day);
		return day;
	}
}
