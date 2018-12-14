package com.awstools.optimize_cost.controllers;

import com.awstools.optimize_cost.models.Ec2Information;
import com.awstools.optimize_cost.services.Ec2Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {
	private Ec2Service ec2Service;

	public TeamController(Ec2Service ec2Service) {
		this.ec2Service = ec2Service;
	}

	@GetMapping("/teams/ec2")
	public ResponseEntity<List<Ec2Information>> getEc2instances() {
		return new ResponseEntity<>(ec2Service.getEc2Instances(), HttpStatus.OK);
	}
}
