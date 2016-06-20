package com.hummus.model;

import java.io.Serializable;

public class Listdetailsmodel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String image;
	public Listdetailsmodel(String image) {
		super();
		this.image = image;
	}
	public Listdetailsmodel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Listdetailsmodel [image=" + image + "]";
	}
	
	
	

}
