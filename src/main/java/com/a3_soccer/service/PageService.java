package com.a3_soccer.service;

import com.a3_soccer.client.SoccerClient;
import com.a3_soccer.dto.LeagueApiResponseDTO;
import com.a3_soccer.dto.LeagueWrapperDTO;
import com.a3_soccer.entity.League;
import com.a3_soccer.enums.Ligas;
import com.a3_soccer.repository.LeagueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SoccerClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public List<League> getLeaguesFromEnum(Integer season) {
        List<League> leagues = new ArrayList<>();
        int validSeason = normalizeSeason(season);

        for (Ligas liga : Ligas.values()) {
            Optional<League> leagueDb = leagueRepository.findByLeagueIdAndSeason(liga.getId(), season);

            if (leagueDb.isPresent()) {
                leagues.add(leagueDb.get());
                continue;
            }

            try {
                String response = client.getLeagues(liga.getId(), validSeason, null);
                LeagueApiResponseDTO dto = objectMapper.readValue(response, LeagueApiResponseDTO.class);

                LeagueWrapperDTO item = dto.getResponse()
                        .stream()
                        .filter(l -> l.getLeague().getId().equals(liga.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Liga " + liga.getId() + " não encontrada na API"));

                League league = dozerMapper.map(item.getLeague(), League.class);
                league.setSeason(validSeason);

                if (league.getPartidas() == null) {
                    league.setPartidas(0);
                }

                if (league.getTotalGols() == null) {
                    league.setTotalGols(0L);
                }

                leagueRepository.save(league);
                leagues.add(league);

            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar liga " + liga.getName(), e);
            }
        }

        return leagues;
    }

    private int normalizeSeason(Integer season) {
        if (season == null) {
            return 2024;
        }
        return Math.max(2020, Math.min(2026, season));
    }
}