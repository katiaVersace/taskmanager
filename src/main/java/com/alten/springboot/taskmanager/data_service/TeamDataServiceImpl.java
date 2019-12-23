package com.alten.springboot.taskmanager.data_service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.springboot.taskmanager.dao.TeamRepository;
import com.alten.springboot.taskmanager.entity.Team;

@Service
public class TeamDataServiceImpl implements TeamDataService {

	@Autowired
	private TeamRepository teamDao;


	@Override
	@Transactional
	public List<Team> findAll() {
		List<Team> teams = teamDao.findAll();

		
		return teams;
	}

	@Override
	@Transactional
	public Team findById(int teamId) {
		Optional<Team> result = teamDao.findById(teamId);

		
		return result.get();
	}

	@Override
	@Transactional
	public void save(Team team) {
		teamDao.save(team);

	}

	@Override
	@Transactional
	public boolean update(Team newTeam) {
		Optional<Team> result = teamDao.findById(newTeam.getId());

		if (result.isPresent()) {
			Team oldTeam = result.get();
			
			// update only if you have the last version
			int oldVersion = oldTeam.getVersion();
			if (oldVersion == newTeam.getVersion()) {
				oldTeam.setVersion(oldVersion + 1);
				oldTeam.setName(newTeam.getName());

				oldTeam.getEmployees().clear();
				oldTeam.getEmployees().addAll(newTeam.getEmployees());

				teamDao.save(oldTeam);
				return true;
			}

			else {

				throw new RuntimeException("You are trying to update an older version of this team, db:" + oldVersion
						+ ", your object: " + newTeam.getVersion());

			}
		} else {
			throw new NullPointerException("Error, team not found in the db");
		}
	}

	@Override
	@Transactional
	public void delete(int teamId) {
		Optional<Team> result = teamDao.findById(teamId);
		if (result.isPresent()) {
			teamDao.deleteById(teamId);
		} else {
			throw new NullPointerException("Error, team not found in the db");
		}

	}

	@Override
	public String populateDB() {
		
		return "ok";

	}


}