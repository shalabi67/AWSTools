package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AwsResources {
	private static Logger logger = LoggerFactory.getLogger(AwsResources.class);

	private Ec2Client ec2Client = Ec2Client.create();

	public Map<Short, List<Ec2Information>> getInstances() {
		List<Ec2Information> instances = getEc2Instances();
		Map<Short, List<Ec2Information>> orderMap = new TreeMap<>();
		for(Ec2Information instance : instances) {
			if(!instance.canAccept()) {
				continue;
			}

			addToMap(orderMap, instance);
		}

		return orderMap;
	}



	public List<Ec2Information> getResources(Predicate<Ec2Information> predicate) {
		List<Ec2Information> instances = getEc2Instances();
		return instances.stream()
				.filter(predicate)
				.collect(Collectors.toList());
	}
	public List<Ec2Information> doAction(Predicate<Ec2Information> predicate, Function<Ec2Information, Ec2Information> action) {
		Map<Short, List<Ec2Information>> orderMap = getInstances();
		List<Ec2Information> instances = new ArrayList<>();
		for(Short itemOrder : orderMap.keySet()) {
			logger.debug("instance with order " + itemOrder);

			/*
			List<Ec2Information> orderInstances = orderMap.get(itemOrder).stream()
					.filter(predicate)
					.map(action)
					.collect(Collectors.toList());

			logger.debug(orderInstances.size() + " working on");
			*/
			List<Ec2Information> orderInstances = new ArrayList<>();
			for(Ec2Information ec2Information : orderMap.get(itemOrder)) {
				if(predicate.test(ec2Information)) {
					orderInstances.add(action.apply(ec2Information));
				}
			}

			instances.addAll(orderInstances);
		}
		return instances;
	}

	public void stopInstance(String instanceId) {
		StopInstancesRequest request = StopInstancesRequest.builder()
				.instanceIds(instanceId).build();

		ec2Client.stopInstances(request);

		logger.debug("Stopping " + instanceId);
	}

	public void startInstance(String instanceId) {
		StartInstancesRequest request = StartInstancesRequest.builder()
				.instanceIds(instanceId).build();

		ec2Client.startInstances(request);

		logger.debug("Starting " + instanceId);
	}

	private void addToMap(Map<Short, List<Ec2Information>> orderMap, Ec2Information instance) {
		Short itemOrder = instance.getItemInformation().getOrderNumber();
		List<Ec2Information> orderList = orderMap.getOrDefault(itemOrder, new ArrayList<>());
		orderList.add(instance);
		orderMap.put(itemOrder, orderList);
	}

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

					/*
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
					System.out.println("");*/

					ec2InformationList.add(ec2Information);
				}
			}

			if(response.nextToken() == null) {
				done = true;
			}
		}

		return ec2InformationList;
	}
}
