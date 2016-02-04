package com.example.myexpenses;


public class Item {
	private String details;
	private String name;
	private String price;
	private int id;
	
	public Item(){

	}

	public Item(String i, String d, String p,int id){
		this.details = d;
		this.name = i;
		this.price = p;
		this.id= id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}
	public int getId()
	{
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}

}