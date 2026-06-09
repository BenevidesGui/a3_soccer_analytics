package com.a3_soccer.controller;

import com.a3_soccer.entity.League;
import com.a3_soccer.service.AnalyticsService;
import com.a3_soccer.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) Integer season,
            Model model
    ) {
        int validSeason = (season == null) ? 2024 : season;

        List<League> leagues = pageService.getLeaguesFromEnum(validSeason);
        Map<String, Long> goals = analyticsService.getGoalsByLeague(validSeason);
        Map<String, Double> averageGoals = analyticsService.getAverageGoalsByLeague(validSeason);
        Map<Integer, String> bestPlayers = analyticsService.getBestPlayerByLeague(validSeason);

        model.addAttribute("cards", leagues);
        model.addAttribute("season", validSeason);
        model.addAttribute("goals", goals);
        model.addAttribute("averageGoals", averageGoals);
        model.addAttribute("bestPlayers", bestPlayers);

        return "dashboard";
    }
}