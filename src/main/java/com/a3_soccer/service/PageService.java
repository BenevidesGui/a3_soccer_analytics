package com.a3_soccer.service;

import com.a3_soccer.client.SoccerClient;
import com.a3_soccer.dto.LeagueCard;
import com.a3_soccer.enums.Ligas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class PageService {

    @Autowired
    private SoccerClient client;

    @Cacheable(value = "leagues")
    public List<String> getLeaguesFromEnum(Integer season) {

        List<String> responses = new ArrayList<>();

        if (season == null || season > 2024 ) {
            season = 2024;
        }

        for (Ligas league : Ligas.values()) {
            String response = client.getLeagues(league.getId(), season, null);
            responses.add(response);
        }

        return responses;
    }

    @Cacheable(value = "leagues_cards")
    public List<LeagueCard> getLeagueCards() {

        List<LeagueCard> cards = new ArrayList<>();

        for (Ligas liga : Ligas.values()) {
            cards.add(new LeagueCard(
                    liga.getName(),
                    liga.getId()
            ));
        }

        return cards;
    }
}
