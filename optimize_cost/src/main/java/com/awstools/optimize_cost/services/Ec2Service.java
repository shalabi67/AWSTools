package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Ec2Information;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;

import java.util.ArrayList;
import java.util.List;

@Service
public class Ec2Service {
	//private Ec2Client ec2Client = Ec2Client.create();

	public List<Ec2Information> getEc2Instances() {
		Ec2Client ec2Client = Ec2Client.create();
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

}
