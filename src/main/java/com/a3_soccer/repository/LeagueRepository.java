package com.a3_soccer.repository;

import com.a3_soccer.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<League> findByLeagueIdAndSeason(
            Integer leagueId,
            Integer season
    );
}