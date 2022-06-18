package com.jungeunhong.datajpa.team.command.repository;

import com.jungeunhong.datajpa.team.command.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamCommandRepository extends JpaRepository<Team,Long> {
}
