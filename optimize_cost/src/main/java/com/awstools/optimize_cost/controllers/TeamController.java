package com.awstools.optimize_cost.controllers;

import com.awstools.optimize_cost.factories.ResourceInformationEnum;
import com.awstools.optimize_cost.factories.ResourcesFactory;
import com.awstools.optimize_cost.models.Ec2Information;
import com.awstools.optimize_cost.models.Team;
import com.awstools.optimize_cost.services.AwsResources;
import com.awstools.optimize_cost.services.Ec2Service;
import com.awstools.optimize_cost.services.ResourcesService;
import com.awstools.optimize_cost.services.TeamEnvironmentProjectResourcesService;
import com.awstools.optimize_cost.services.TeamEnvironmentResourcesService;
import com.awstools.optimize_cost.services.TeamResourcesService;
import com.awstools.optimize_cost.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

	private TeamService teamService;
	private ResourcesFactory resourcesFactory;

	public TeamController(TeamService teamService, ResourcesFactory resourcesFactory) {
		this.teamService = teamService;
		this.resourcesFactory = resourcesFactory;
	}

	@GetMapping("/resources")
	public ResponseEntity<List<Ec2Information>> getResources(
			@RequestParam(required = false, name = "action")String action) {
		List<Ec2Information> list = run(resourcesFactory.create(ResourceInformationEnum.Resource), action);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@GetMapping(path = {"/", ""})
	public ResponseEntity<List<Team>> getTeams() {
		return new ResponseEntity<>(teamService.getTeams(), HttpStatus.OK);
	}

	@GetMapping("/resources/{teamName}")
	public ResponseEntity<List<Ec2Information>> getTeamResources(
			@PathVariable("teamName")String teamName,
			@RequestParam(required = false, name = "action")String action) {
		TeamResourcesService service = (TeamResourcesService)resourcesFactory.create(ResourceInformationEnum.Team);
		service.setTeamName(teamName);

		List<Ec2Information> list = run(service, action);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/resources/{teamName}/environments/{environment}")
	public ResponseEntity<List<Ec2Information>> getTeamEnvironmentResources(
			@PathVariable("teamName")String teamName,
			@PathVariable("environment")String environment,
			@RequestParam(required = false, name = "action")String action) {
		TeamEnvironmentResourcesService service = (TeamEnvironmentResourcesService)
				resourcesFactory.create(ResourceInformationEnum.TeamEnvironment);
		service.setTeamName(teamName);
		service.setEnvironment(environment);

		List<Ec2Information> list = run(service, action);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/resources/{teamName}/environments/{environment}/projects/{projectName}")
	public ResponseEntity<List<Ec2Information>> getTeamEnvironmentProjectResources(
			@PathVariable("teamName")String teamName,
			@PathVariable("environment")String environment,
			@PathVariable("projectName")String projectName,
			@RequestParam(required = false, name = "action")String action) {
		TeamEnvironmentProjectResourcesService service = (TeamEnvironmentProjectResourcesService)
				resourcesFactory.create(ResourceInformationEnum.TeamEnvironmentProject);
		service.setTeamName(teamName);
		service.setEnvironment(environment);
		service.setProjectName(projectName);

		List<Ec2Information> list = run(service, action);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	private List<Ec2Information> run(Ec2Service ec2Service, String action) {
		List<Ec2Information> ec2Informations;
		if(action == null || action.isEmpty()) {
			ec2Informations = ec2Service.getResources();
		} else {
			switch (action) {
			case "stop":
				ec2Informations = ec2Service.stopInstances();
				break;
			case "start":
				ec2Informations = ec2Service.startInstances();
				break;
			default: ec2Informations = new ArrayList<>();
			}
		}

		return ec2Informations;

	}
}
