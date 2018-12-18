package com.awstools.optimize_cost.models;

import software.amazon.awssdk.services.ec2.model.Instance;

public class Ec2Information {
	private String id;
	private String state;
	private ItemInformation itemInformation;

	public Ec2Information() {

	}
	public Ec2Information(String id) {
		this.id = id;
	}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean stopped() {
		return //state.toLowerCase().contains("terminated") ||
				state.toLowerCase().contains("stopped");
	}
	public Boolean started() {
		return state.toLowerCase().contains("running");
	}
}
