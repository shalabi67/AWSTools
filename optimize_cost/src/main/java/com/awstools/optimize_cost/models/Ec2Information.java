package com.awstools.optimize_cost.models;

import software.amazon.awssdk.services.ec2.model.Instance;

public class Ec2Information {
	private String id;
	private ItemInformation itemInformation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ItemInformation getItemInformation() {
		return itemInformation;
	}

	public void setItemInformation(ItemInformation itemInformation) {
		this.itemInformation = itemInformation;
	}
	public void setItemInformation(Instance ec2instance) {
		this.itemInformation = new ItemInformation(ec2instance);
	}
}
