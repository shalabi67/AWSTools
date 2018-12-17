package com.awstools.optimize_cost.factories;

import com.awstools.optimize_cost.services.AwsResources;
import com.awstools.optimize_cost.services.Ec2Service;
import com.awstools.optimize_cost.services.ResourcesService;
import com.awstools.optimize_cost.services.TeamEnvironmentResourcesService;
import com.awstools.optimize_cost.services.TeamResourcesService;
import org.springframework.stereotype.Service;

@Service
public class ResourcesFactory {
	private AwsResources awsResources;

	public ResourcesFactory(AwsResources awsResources) {
		this.awsResources = awsResources;
	}

	public Ec2Service create(ResourceInformationEnum resourceInformationEnum) {
		switch(resourceInformationEnum) {
		case Team:
			return new TeamResourcesService(awsResources);
		case TeamEnvironment:
			return new TeamEnvironmentResourcesService(awsResources);
		case Resource:
		default:
			return new ResourcesService(awsResources);
		}
	}

}
