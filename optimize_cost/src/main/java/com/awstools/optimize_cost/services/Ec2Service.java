package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;

import java.util.List;

public interface Ec2Service {
	List<Ec2Information> getResources();
	List<Ec2Information> startInstances();
	List<Ec2Information> stopInstances();
}
