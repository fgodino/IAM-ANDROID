package com.godino.emotion.models;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Minutes;

import android.graphics.Color;
import com.google.gson.annotations.SerializedName;

public class Status {
	
	
	
	@SerializedName("status")
	private String title;
	
	@SerializedName("color")
	private String colorString;
	
	private Date date;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return colorString;
	}

	public void setColor(String color) {
		this.colorString = color;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDifferenceDate(){
		
		DateTime thatTime = new DateTime(date,DateTimeZone.getDefault());
		DateTime today = DateTime.now();
		
		System.out.println(today);
		System.out.println(thatTime);
		
		return Integer.toString(Minutes.minutesBetween(thatTime, today).getMinutes()) + "m";
	}
}
