package com.froy.magicalitem;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

public class Item implements Parcelable {

	private long id;
	private String name, category, special_ability, aura, caster_level, price,
			prereq, cost, full_text;
/*
 * Constructors
 */
	 /**
     * Constructors
     * @param id id of Item
     * @param name, category, special_ability, aura, caster_level, price,
			prereq, cost of Item
     * @param full_text full description of the item
     * @return success or fail
     */
	public Item(Long id,String name, String category, String special_ability,
			String aura, String caster_level, String price, String prereq,String cost, String full_text) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.special_ability = special_ability;
		this.aura =aura;
		this.caster_level=caster_level;
		this.price = price;
		this.prereq=prereq;
		this.cost=cost;
		this.full_text = full_text;
		

	}
	public Item() {
			this((Long)null, null,null,null,null,null,null,null,null,null);
	}
	
	public Item(Long id) {
		this(id, null,null,null,null,null,null,null,null,null);

	}

	public Item(Long id, String name) {
		this(id, name,null,null,null,null,null,null,null,null);

	}

	public Item(Long id, String name, String category) {
		this(id, name,category,null,null,null,null,null,null,null);

	}

	public Item(Long id, String name, String category, String special_ability) {
		this(id, name,category,special_ability,null,null,null,null,null,null);


	}

	public Item(Long id, String name, String category, String special_ability,
			String aura) {
		this(id, name,category,special_ability,aura,null,null,null,null,null);

	}

	public Item(Long id, String name, String category, String special_ability,
			String aura, String caster_level) {
		this(id, name,category,special_ability,aura,caster_level,null,null,null,null);

	}

	public Item(Long id,String name, String category, String special_ability,
			String aura, String caster_level, String price) {
		this(id, name,category,special_ability,aura,caster_level,price,null,null,null);
	}

	public Item(Long id, String name, String category, String special_ability,
			String aura, String caster_level, String price, String prereq) {
		this(id, name,category,special_ability,aura,caster_level,price,prereq,null,null);
		
	}

	public Item(Long id, String name, String category, String special_ability,
			String aura, String caster_level, String price, String prereq,String cost) {
		this(id, name,category,special_ability,aura,caster_level,price,prereq,cost,null);
		
	}


	/*
	 * Setters and Getters
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSpecialAbility() {
		return special_ability;
	}

	public void setSpecialAbility(String special_ability) {
		this.special_ability = special_ability;
	}

	public String getAura() {
		return aura;
	}

	public void setAura(String aura) {
		this.aura = aura;
	}

	public String getCasterLevel() {
		return caster_level;
	}

	public void setCasterLevel(String caster_level) {
		this.caster_level = caster_level;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPreReq() {
		return prereq;
	}

	public void setPreReq(String prereq) {
		this.prereq = prereq;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getFullText() {
		return full_text;
	}

	public void setFullText(String full_text) {
		this.full_text = full_text;
	}
	
	
	// Parcelling part
	
	public Item(Parcel in){
		String[] data = new String[10];
		
		in.readStringArray(data);
		
		this.id = Long.parseLong(data[0]);
		this.name = data[1];
		this.category = data[2];
		this.special_ability = data[3];
		this.aura =data[4];
		this.caster_level=data[5];
		this.price = data[6];
		this.prereq=data[7];
		this.cost=data[8];
		this.full_text = data[9];
		
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[]{
				Long.toString(this.id),this.name , this.category ,this.special_ability,
				this.aura, this.caster_level,this.price ,this.prereq,
				this.cost, this.full_text });
		
	}
	public static final Parcelable.Creator<Item> CREATOR= new Parcelable.Creator<Item>(){
		public Item createFromParcel (Parcel in){
			return new Item(in);
		}
		
		public Item[] newArray(int size){
			return new Item[size];
		}
	};


}
