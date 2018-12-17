package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;

public class PredicateBuilder {
	public static Boolean isTeamResource(Ec2Information ec2Information, String teamName) {
		return ec2Information.getItemInformation().getTeam().equalsIgnoreCase(teamName);
	}
	public static Boolean isTeamEnvironmentResource(Ec2Information ec2Information, String teamName, String environment) {
		return isTeamResource(ec2Information, teamName) &&
				ec2Information.getItemInformation().getEnvironment().equalsIgnoreCase(environment);
	}
}
