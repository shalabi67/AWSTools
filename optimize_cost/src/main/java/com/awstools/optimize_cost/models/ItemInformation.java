package com.awstools.optimize_cost.models;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Tag;

import java.util.HashMap;

public class ItemInformation {
	private String environment;
	private String team;
	private String project;
	private String name;
	private Boolean participate;
	private Short orderNumber;

	public ItemInformation() {

	}
	public ItemInformation(Instance ec2) {
		HashMap<String, String> hashMap = new HashMap<>();
		for(Tag tag : ec2.tags()) {
			hashMap.put(tag.key().toLowerCase(), tag.value());
		}

		environment = hashMap.getOrDefault("environment", "");
		team = hashMap.getOrDefault("team", "");
		project = hashMap.getOrDefault("project", "");
		name = hashMap.getOrDefault("name", "");
		participate = Boolean.valueOf(hashMap.getOrDefault("participate", "false"));
		orderNumber = Short.valueOf(hashMap.getOrDefault("order", "0"));
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getParticipate() {
		return participate;
	}

	public void setParticipate(Boolean participate) {
		this.participate = participate;
	}

	public Short getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Short orderNumber) {
		this.orderNumber = orderNumber;
	}
}
