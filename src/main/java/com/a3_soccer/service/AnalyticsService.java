package com.a3_soccer.service;

import com.a3_soccer.client.SoccerClient;
import com.a3_soccer.enums.Ligas;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private SoccerClient client;

    @Autowired
    private ObjectMapper mapper;

    @Cacheable(value = "goalsByLeague", key = "#season ?: 2023")
    public Map<String, Integer> getGoalsByLeague(Integer season) {

        int validSeason = normalizeSeason(season);

        Map<String, Integer> result = new LinkedHashMap<>();

        for (Ligas liga : Ligas.values()) {

            try {
                String json = client.getFixtures(liga.getId(), validSeason);

                JsonNode root = mapper.readTree(json);
                JsonNode fixtures = root.path("response");

                int totalGoals = 0;

                for (JsonNode match : fixtures) {
                    int home = match.path("goals").path("home").asInt(0);
                    int away = match.path("goals").path("away").asInt(0);

                    totalGoals += home + away;
                }
                System.out.println(liga.getName() + " -> " + totalGoals + " gols");

                result.put(liga.getName(), totalGoals);

            } catch (Exception e) {
                throw new RuntimeException("Erro na liga: " + liga.getName(), e);
            }
        }

        return result;
    }

    private int normalizeSeason(Integer season) {
        if (season == null) {
            return 2023;
        }
        return Math.max(2020, Math.min(2024, season));
    }
}