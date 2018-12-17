package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;

import java.util.List;

public class TeamEnvironmentResourcesService implements Ec2Service {
	private AwsResources awsResources;
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
	public List<Ec2Information> getResources() {
		return awsResources.getResources(ec2Information ->
				PredicateBuilder.isTeamEnvironmentResource(ec2Information, teamName, environment));
	}
	@Override
	public List<Ec2Information> startInstances() {
		return awsResources.doAction(
				ec2Information -> ec2Information.stopped() &&
						PredicateBuilder.isTeamEnvironmentResource(ec2Information, teamName, environment),
				instance -> {
					awsResources.startInstance(instance.getId());
					return new Ec2Information(instance.getId());
				}
		);
	}

	@Override
	public List<Ec2Information> stopInstances() {
		return awsResources.doAction(
				ec2Information -> ec2Information.started() &&
						PredicateBuilder.isTeamEnvironmentResource(ec2Information, teamName, environment),
				instance -> {
					awsResources.stopInstance(instance.getId());
					return new Ec2Information(instance.getId());
				}
		);
	}
}
