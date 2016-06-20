package com.hummus.model;

import java.io.Serializable;

public class SearchModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;
	String address;
	Double latitude;
	Double longitude;
	String miles;
	String image;
	
	String event_id;
	String des;
	Float rating;
	String website;
	String telephone;
	String sub_image;


	public SearchModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchModel(String name, String address, Double latitude,
			Double longitude, String miles, String image, String event_id,
			String des, Float rating, String website, String telephone,
			String sub_image) {
		super();
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.miles = miles;
		this.image = image;
		this.event_id = event_id;
		this.des = des;
		this.rating = rating;
		this.website = website;
		this.telephone = telephone;
		this.sub_image = sub_image;
	}
	@Override
	public String toString() {
		return "SearchModel [name=" + name + ", address=" + address
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", miles=" + miles + ", image=" + image + ", event_id="
				+ event_id + ", des=" + des + ", rating=" + rating
				+ ", website=" + website + ", telephone=" + telephone
				+ ", sub_image=" + sub_image + "]";
	}


	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
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

	public String getSub_image() {
		return sub_image;
	}

	public void setSub_image(String sub_image) {
		this.sub_image = sub_image;
	}
	public String getMiles() {
		return miles;
	}
	public void setMiles(String miles) {
		this.miles = miles;
	}
	
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

	

}
