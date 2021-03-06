package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;

import java.util.List;

public class TeamEnvironmentResourcesService extends Ec2Service {
	private String teamName;
	private String environment;

	public TeamEnvironmentResourcesService(AwsResources awsResources) {
		this.awsResources = awsResources;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	@Override
	protected Boolean isValidResource(Ec2Information ec2Information) {
		return PredicateBuilder.isTeamEnvironmentResource(ec2Information, teamName, environment);
	}
}
