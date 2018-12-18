package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamResourcesService extends Ec2Service {
	private String teamName;

	public TeamResourcesService(AwsResources awsResources) {
		this.awsResources = awsResources;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override protected Boolean isValidResource(Ec2Information ec2Information) {
		return PredicateBuilder.isTeamResource(ec2Information, teamName);
	}

}
