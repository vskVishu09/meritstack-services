package com.example.meritstack.util;

import java.util.Calendar;
import java.util.Date;

public class CalenderUtility {

	public static boolean isOTPExpired(Date ExpireDate) {
		Date CurrentDate =  Calendar.getInstance().getTime();
		return CurrentDate.after(ExpireDate);
	}
}
