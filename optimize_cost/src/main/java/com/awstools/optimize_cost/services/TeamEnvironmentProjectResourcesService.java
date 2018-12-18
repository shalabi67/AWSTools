package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;

import java.util.List;
import java.util.function.Predicate;

public class TeamEnvironmentProjectResourcesService extends Ec2Service {

	private String teamName;
	private String environment;
	private String projectName;

	public TeamEnvironmentProjectResourcesService(AwsResources awsResources) {
		this.awsResources = awsResources;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	protected Boolean isValidResource(Ec2Information ec2Information) {
		return PredicateBuilder.isTeamEnvironmentProjectResource(ec2Information, teamName, environment, projectName);
	}
}
