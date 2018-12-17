package com.awstools.optimize_cost.repositories;

import com.awstools.optimize_cost.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamRepository extends JpaRepository<Team, Long> {

	List<Team> findAllAllByOrderByTeamNameAsc();
}
