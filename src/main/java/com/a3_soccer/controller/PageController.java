package com.a3_soccer.controller;


import com.a3_soccer.service.AnalyticsService;
import com.a3_soccer.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class PageController {

    @Autowired
    private PageService Pageservice;

    @Autowired
    private AnalyticsService analyticsService;



    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) Integer season,
            Model model
    ) {
        model.addAttribute("cards", Pageservice.getLeagueCards());
        model.addAttribute("analytics", Pageservice.getLeaguesFromEnum(season));
        model.addAttribute("season", season);

        Map<String, Integer> goals = analyticsService.getGoalsByLeague(season);

        model.addAttribute("goals", goals);
        return "dashboard";
    }
}
