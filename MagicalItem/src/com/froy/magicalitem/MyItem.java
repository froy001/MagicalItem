package com.froy.magicalitem;

public class MyItem {
	private Long id, item_id;
	private int charges;
	private String name, category;
	
	public MyItem(Long id, Long item_id, int charges, String name, String category){
		
		this.id = id;
		this.item_id = item_id;
		this.charges = charges;
		this.name = name;
		this.category=category;
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getItemId() {
		return item_id;
	}

	public void setItemId(long item_id) {
		this.item_id = item_id;
	}
	
	public int getItemCharges() {
		return charges;
	}

	public void setItemCharges(int charges) {
		this.charges = charges;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name= name;
	}
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category= category;
	}

}
