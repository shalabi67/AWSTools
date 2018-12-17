package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AwsResources {
	private Ec2Client ec2Client = Ec2Client.create();

	public List<Ec2Information> getEc2Instances() {
		DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
		boolean done = false;
		List<Ec2Information> ec2InformationList = new ArrayList<>();
		while(!done) {
			DescribeInstancesResponse response = ec2Client.describeInstances(request);

			for(Reservation reservation : response.reservations()) {
				Ec2Information ec2Information = new Ec2Information();
				for(Instance instance : reservation.instances()) {
					ec2Information.setId(instance.instanceId());
					ec2Information.setItemInformation(instance);
					ec2Information.setState(instance.state().nameAsString());

					System.out.printf(
							"Found reservation with id %s, " +
									"AMI %s, " +
									"type %s, " +
									"state %s " +
									"and monitoring state %s",
							instance.instanceId(),
							instance.imageId(),
							instance.instanceType(),
							instance.state().name(),
							instance.monitoring().state());
					System.out.println("");

					ec2InformationList.add(ec2Information);
				}
			}

			if(response.nextToken() == null) {
				done = true;
			}
		}

		return ec2InformationList;
	}


	public List<Ec2Information> getResources(Predicate<Ec2Information> predicate) {
		List<Ec2Information> instances = getEc2Instances();
		return instances.stream()
				.filter(predicate)
				.collect(Collectors.toList());
	}
	public List<Ec2Information> doAction(Predicate<Ec2Information> predicate, Function<Ec2Information, Ec2Information> action) {
		List<Ec2Information> instances = getEc2Instances();
		List<Ec2Information> stoppedInstances = instances.stream()
				.filter(predicate)
				.map(action)
				.collect(Collectors.toList());

		return stoppedInstances;
	}

	public void stopInstance(String instanceId) {
		StopInstancesRequest request = StopInstancesRequest.builder()
				.instanceIds(instanceId).build();

		ec2Client.stopInstances(request);
	}

	public void startInstance(String instanceId) {
		StartInstancesRequest request = StartInstancesRequest.builder()
				.instanceIds(instanceId).build();

		ec2Client.startInstances(request);
	}


}
