package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamResourcesService implements Ec2Service {
	private AwsResources awsResources;
	private String teamName;

	public TeamResourcesService(AwsResources awsResources) {
		this.awsResources = awsResources;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public List<Ec2Information> getResources() {
		return awsResources.getResources(ec2Information ->
				PredicateBuilder.isTeamResource(ec2Information, teamName));
	}

	@Override
	public List<Ec2Information> startInstances() {
		return awsResources.doAction(
				ec2Information -> ec2Information.stopped() &&
						PredicateBuilder.isTeamResource(ec2Information, teamName),
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
						PredicateBuilder.isTeamResource(ec2Information, teamName),
				instance -> {
					awsResources.stopInstance(instance.getId());
					return new Ec2Information(instance.getId());
				}
		);
	}
}
