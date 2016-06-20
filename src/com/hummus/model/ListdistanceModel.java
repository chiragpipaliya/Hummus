package com.hummus.model;

import java.io.Serializable;

public class ListdistanceModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String event_id;
	String name;
	String address;
	String latitude;
	String longitude;
	String des;
	String image;
	Float rating;
	String sub_image;
	String miles;
	String website;
	String telephone;
	Float l_rating;
	public Float getL_rating() {
		return l_rating;
	}
	public void setL_rating(Float l_rating) {
		this.l_rating = l_rating;
	}
	public ListdistanceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListdistanceModel(String event_id,String image,String name,
			String address,String miles,String latitude,String longitude,Float rating,String telephone,String website,String des) {
		super();
		this.event_id=event_id;
		this.image=image;
		this.name = name;
		this.address = address;
		this.latitude=latitude;
		this.longitude=longitude;
		this.miles=miles;
		this.rating=rating;
		this.telephone=telephone;
		this.website=website;
		this.des=des;
	
	}


	@Override
	public String toString() {
		return "ListModel [event_id=" + event_id + ", name=" + name
				+ ", address=" + address + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", image=" + image + ", rating="
				+ rating + ", sub_image=" + sub_image + ", miles=" + miles
				+ ", website=" + website + ", telephone=" + telephone + "]";
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
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public ListdistanceModel(String sub_image) {
		super();
		this.sub_image = sub_image;
	}
	public String getSub_image() {
		return sub_image;
	}
	public void setSub_image(String sub_image) {
		this.sub_image = sub_image;
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
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
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
	public String getMiles() {
		return miles;
	}
	public void setMiles(String miles) {
		this.miles = miles;
	}
	

	
	

}
