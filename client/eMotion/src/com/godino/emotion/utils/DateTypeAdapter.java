package com.godino.emotion.utils;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
	private final DateFormat dateFormat;

	public DateTypeAdapter() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override 
	public JsonElement serialize(Date date, Type type,
			JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(dateFormat.format(date));
	}

	@Override 
	public Date deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext) {
		System.out.println(jsonElement.getAsString());
		try {
			return dateFormat.parse(jsonElement.getAsString());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}
