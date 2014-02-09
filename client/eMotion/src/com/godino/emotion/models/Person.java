package com.godino.emotion.models;
import com.google.gson.annotations.SerializedName;

public class Person
{
	
	private long id;
	
	private String name;
    private String picture;
    
    @SerializedName("current_status")
    private Status status;
    
    public long getId() {
    	return id;
    }
    public void setId(long l) {
    	this.id = l;
    }
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public String getPicture() {
    	return picture;
    }
    public void setPicture(String picture) {
    	this.picture = picture;
    }
    public Status getStatus() {
    	return status;
    }
    public void setStatus(Status status) {
    	this.status = status;
    }
    
}