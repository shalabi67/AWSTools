package com.awstools.optimize_cost.services;

import com.awstools.optimize_cost.models.Team;
import com.awstools.optimize_cost.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
	private TeamRepository teamRepository;

	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public Team addTeam(Team team) {
		Team newTeam = teamRepository.save(team);

		return newTeam;
	}

	public List<Team> getTeams() {
		return teamRepository.findAllAllByOrderByTeamNameAsc();
	}
}
