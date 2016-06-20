package com.hummus.model;

import java.io.Serializable;

public class Namesearchmodel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String address;
	String image;
	
	public Namesearchmodel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Namesearchmodel(String name, String address, String image) {
		super();
		this.name = name;
		this.address = address;
		this.image = image;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
