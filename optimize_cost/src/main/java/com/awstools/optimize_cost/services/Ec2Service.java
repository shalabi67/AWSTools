package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;

import java.util.List;

public abstract class Ec2Service {
	protected AwsResources awsResources;

	public List<Ec2Information> getResources() {
		return awsResources.getResources(ec2Information -> isValidResource(ec2Information));
	}
	public List<Ec2Information> startInstances() {
		return awsResources.doAction(
				ec2Information -> ec2Information.stopped() &&
						isValidResource(ec2Information),
				instance -> {
					Ec2Information ec2Information = new Ec2Information(instance.getId());
					try {
						awsResources.startInstance(instance.getId());
					}catch (Exception e) {
						ec2Information.setState("Error");
					}
					return ec2Information;
				}
		);
	}
	public List<Ec2Information> stopInstances() {
		return awsResources.doAction(
				ec2Information -> ec2Information.started() &&
						isValidResource(ec2Information),
				instance -> {
					Ec2Information ec2Information = new Ec2Information(instance.getId());
					try {
						awsResources.stopInstance(instance.getId());
					}catch(Exception e) {
						ec2Information.setState("Error");
					}
					return ec2Information;
				}
		);
	}

	protected abstract Boolean isValidResource(Ec2Information ec2Information);
}
