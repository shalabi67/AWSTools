package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesService implements Ec2Service {
	private AwsResources awsResources;

	public ResourcesService(AwsResources awsResources) {
		this.awsResources = awsResources;
	}

	@Override
	public List<Ec2Information> stopInstances() {
		return awsResources.doAction(
				Ec2Information::started,
				instance -> {
					awsResources.stopInstance(instance.getId());
					return new Ec2Information(instance.getId());
				}
		);
	}

	@Override
	public List<Ec2Information> startInstances() {
		return awsResources.doAction(
				Ec2Information::stopped,
				instance -> {
					awsResources.startInstance(instance.getId());
					return new Ec2Information(instance.getId());
				}
		);
	}

	@Override
	public List<Ec2Information> getResources() {
		return awsResources.getEc2Instances();
	}



}
