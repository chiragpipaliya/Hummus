package com.hummus.model;

import java.io.Serializable;

public class MapModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String event_id;
	String name;
	Double latitude;
	Double longitude;
	String address;
	String image;
	float rating;
	String sub_image;
	String des;
	String website;
	String telephone;

	public MapModel(String event_id, String name, Double latitude,
			Double longitude, String image, float rating, 
			String des, String website, String telephone,String address) {
		super();
		this.event_id = event_id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.rating = rating;
		this.des = des;
		this.website = website;
		this.telephone = telephone;
		this.address=address;
	}
	
	@Override
	public String toString() {
		return "MapModel [event_id=" + event_id + ", name=" + name
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", image=" + image + ", rating=" + rating + ", sub_image="
				+ sub_image + ", des=" + des + ", website=" + website
				+ ", telephone=" + telephone + "]";
	}
	


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getSub_image() {
		return sub_image;
	}
	public void setSub_image(String sub_image) {
		this.sub_image = sub_image;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	

}
