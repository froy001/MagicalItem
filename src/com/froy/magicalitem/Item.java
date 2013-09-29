package com.froy.magicalitem;

import android.text.Html;

public class Item {

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


}
