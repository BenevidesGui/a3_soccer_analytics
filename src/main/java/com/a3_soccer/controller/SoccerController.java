package com.a3_soccer.controller;

import com.a3_soccer.client.SoccerClient;
import com.a3_soccer.enums.Ligas;
import com.a3_soccer.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ustj")
public class SoccerController {

    private final SoccerClient client;

    public SoccerController(SoccerClient client) {
        this.client = client;
    }

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/scorers")
    public ResponseEntity<String> getScorers() {
        return ResponseEntity.ok(client.getTopScorers(71, 2023));
    }

    @GetMapping("/teams")
    public ResponseEntity<?> getTeams(
            @RequestParam String league,
            @RequestParam(required = false) Integer season,
            @RequestParam(required = false) String name
    ) {
        Ligas liga = Ligas.fromString(league);
        int currentSeason = (season != null) ? season : 2026;
        return ResponseEntity.ok(client.getTeamsByLeague(liga.getId(), currentSeason, name));
    }

    @GetMapping("/leagues")
    public ResponseEntity<String> getLeagues(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer season,
            @RequestParam(required = false) List<String> code
    ) {
        return ResponseEntity.ok(client.getLeagues(id, season, code));
    }

    @GetMapping("/test-fixtures")
    public ResponseEntity<String> testFixtures() {
        return ResponseEntity.ok(client.getFixtures(39, 2024));
    }

    @GetMapping("/analytics/goals")
    public Map<String, Long> getGoals(@RequestParam(required = false) Integer season) {
        return analyticsService.getGoalsByLeague(season);
    }

    @GetMapping("/players")
    public ResponseEntity<String> getPlayers() {
        return ResponseEntity.ok(client.getPlayersByTeam(127, 2023));
    }
}