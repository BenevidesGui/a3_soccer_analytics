package com.a3_soccer.service;

import com.a3_soccer.client.SoccerClient;
import com.a3_soccer.entity.League;
import com.a3_soccer.enums.Ligas;
import com.a3_soccer.repository.LeagueRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalyticsService {

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    private SoccerClient client;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);

    @Value("${app.api.delay-ms:500}")
    private long apiDelayMillis;

    public Map<String, Long> getGoalsByLeague(Integer season) {

        int validSeason = normalizeSeason(season);

        // ✔ BEST PLAYERS (cache em memória da execução)
        Map<Integer, String> bestPlayers = getBestPlayerByLeague(validSeason);

        System.out.println(bestPlayers);

        Map<String, Long> result = new LinkedHashMap<>();

        for (Ligas liga : Ligas.values()) {

            try {

                Optional<League> existingLeague =
                        leagueRepository.findByLeagueIdAndSeason(
                                liga.getId(),
                                validSeason
                        );

                League league = existingLeague.orElseGet(() -> {
                    League newLeague = new League();
                    newLeague.setId(liga.getId());
                    newLeague.setName(liga.getName());
                    newLeague.setSeason(validSeason);
                    return newLeague;
                });

                // ✔ se não existe ou precisa atualizar gols
                if (league.getTotalGols() == null || league.getTotalGols() == 0) {

                    String json = client.getFixtures(liga.getId(), validSeason);

                    JsonNode root = mapper.readTree(json);
                    JsonNode fixtures = root.path("response");

                    long totalGoals = calculateGoals(fixtures);

                    int totalMatches =
                            root.path("results").asInt(fixtures.size());

                    league.setTotalGols(totalGoals);
                    league.setPartidas(totalMatches);
                }

                // ✔ sempre garante best player atualizado
                if (league.getBestPlayerName() == null || league.getBestPlayerName().equals("N/A")) {
                    String player = bestPlayers.getOrDefault(liga.getId(), null);

                    if (player != null && !player.equals("N/A")) {
                        league.setBestPlayerName(player);
                    }
                }

                leagueRepository.save(league);

                result.put(liga.getName(), league.getTotalGols());

                Thread.sleep(apiDelayMillis);

            } catch (Exception e) {

                logger.error(
                        "Erro ao processar liga={} season={}",
                        liga.getName(),
                        validSeason,
                        e
                );

                result.put(liga.getName(), 0L);
            }
        }

        return result;
    }

    private long calculateGoals(JsonNode fixtures) {

        long totalGoals = 0;
        for (JsonNode match : fixtures) {
            int home =
                    match.path("goals")
                            .path("home")
                            .asInt(0);
            int away =
                    match.path("goals")
                            .path("away")
                            .asInt(0);
            totalGoals += home + away;
        }
        return totalGoals;
    }

    public Map<String, Long> seasonLeague(League liga){
        Map<String, Long> result = new LinkedHashMap<>();
        try {
            // Tentar com season 2023 se 2024 falhar
            String json = client.getFixtures(liga.getId(), 2023);

            JsonNode root = mapper.readTree(json);
            JsonNode fixtures = root.path("response");

            Long totalGoals = 0L;

            for (JsonNode match : fixtures) {
                int home = match.path("goals").path("home").asInt(0);
                int away = match.path("goals").path("away").asInt(0);

                totalGoals += home + away;
            }
            result.put(liga.getName(), totalGoals);
        } catch (Exception e2) {
            logger.error("Erro ao processar liga: {} mesmo com season 2023 - {}", liga.getName(), e2.getMessage());
            result.put(liga.getName(), 0L);
        }
        return result;
    }

    public Map<String, Double> getAverageGoalsByLeague(Integer season) {

        int validSeason = normalizeSeason(season);

        Map<String, Double> result = new LinkedHashMap<>();

        for (Ligas liga : Ligas.values()) {

            leagueRepository
                    .findByLeagueIdAndSeason(
                            liga.getId(),
                            validSeason
                    )
                    .ifPresent(league -> {

                        double average = 0.0;

                        if (league.getPartidas() != null
                                && league.getPartidas() > 0) {

                            average =
                                    (double) league.getTotalGols()
                                            / league.getPartidas();
                        }

                        result.put(
                                liga.getName(),
                                Math.round(average * 100.0) / 100.0
                        );
                    });
        }

        System.out.println(result);
        return result;
    }

    public Map<Integer, String> getBestPlayerByLeague(Integer season) {

        int validSeason = normalizeSeason(season);

        Map<Integer, String> result = new LinkedHashMap<>();

        for (Ligas liga : Ligas.values()) {

            try {

                Optional<League> leagueOpt =
                        leagueRepository.findByLeagueIdAndSeason(liga.getId(), validSeason);

                League league = leagueOpt.orElse(null);

                String bestPlayer = null;


                if (league != null) {
                    bestPlayer = league.getBestPlayerName();
                }


                if (bestPlayer == null || bestPlayer.equals("N/A") || bestPlayer.isBlank()) {

                    String json = client.getTopScorers(liga.getId(), validSeason);

                    JsonNode root = mapper.readTree(json);
                    JsonNode response = root.path("response");

                    if (!response.isEmpty()) {

                        bestPlayer = response.get(0)
                                .path("player")
                                .path("name")
                                .asText("N/A");

                    } else {
                        bestPlayer = "N/A";
                    }


                    if (league == null) {
                        league = new League();
                        league.setId(liga.getId());
                        league.setSeason(validSeason);
                        league.setName(liga.getName());
                    }

                    league.setBestPlayerName(bestPlayer);
                    leagueRepository.save(league);
                }

                result.put(liga.getId(), bestPlayer);

            } catch (Exception e) {

                logger.error(
                        "Erro ao buscar top player liga={} season={}",
                        liga.getName(),
                        validSeason,
                        e
                );

                result.put(liga.getId(), "N/A");
            }
        }

        return result;
    }

    private int normalizeSeason(Integer season) {
        if (season == null) {
            return 2024;
        }
        return Math.max(2020, Math.min(2026, season));
    }
}