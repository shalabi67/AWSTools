package com.awstools.optimize_cost.controllers;

import com.awstools.optimize_cost.models.Ec2Information;
import com.awstools.optimize_cost.models.Team;
import com.awstools.optimize_cost.services.AwsResources;
import com.awstools.optimize_cost.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
	private AwsResources awsResources;
	private TeamService teamService;

	public TeamController(AwsResources awsResources, TeamService teamService) {
		this.awsResources = awsResources;
		this.teamService = teamService;
	}

	@GetMapping("/resources")
	public ResponseEntity<List<Ec2Information>> getResources(
			@RequestParam(required = false, name = "action")String action) {
		if(action == null || action.isEmpty()) {
			return new ResponseEntity<>(awsResources.getEc2Instances(), HttpStatus.OK);
		}
		switch(action) {
		case "stop": return new ResponseEntity<>(awsResources.stopInstances(), HttpStatus.OK);
		case "start": return new ResponseEntity<>(awsResources.startInstances(), HttpStatus.OK);
		}

		return new ResponseEntity<>(awsResources.getEc2Instances(), HttpStatus.OK);
	}

	@GetMapping(path = {"/", ""})
	public ResponseEntity<List<Team>> getTeams() {
		return new ResponseEntity<>(teamService.getTeams(), HttpStatus.OK);
	}

	@GetMapping("/resources/{teamName}")
	public ResponseEntity<List<Ec2Information>> getTeamResources(
			@PathVariable("teamName")String teamName,
			@RequestParam(required = false, name = "action")String action) {
		if(action == null || action.isEmpty()) {
			return new ResponseEntity<>(awsResources.getTeamResources(teamName), HttpStatus.OK);
		}
		switch(action) {
		case "stop": return new ResponseEntity<>(awsResources.stopTeamInstances(teamName), HttpStatus.OK);
		case "start": return new ResponseEntity<>(awsResources.startTeamInstances(teamName), HttpStatus.OK);
		}

		return new ResponseEntity<>(awsResources.getEc2Instances(), HttpStatus.OK);
	}


}
