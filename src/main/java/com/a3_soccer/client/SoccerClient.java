package com.a3_soccer.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class SoccerClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://v3.football.api-sports.io";
    private final String API_KEY = "53b648155e3987196c57e6721c541fa2";

    public String getTopScorers(int league, int season) {
        String url = BASE_URL + "/players/topscorers?league=" + league + "&season=" + season;
        return makeRequest(url);
    }

    public String getTeamsByLeague(int league, int season, String name) {
        String url = BASE_URL + "/teams?league=" + league + "&season=" + season;
        if (name != null && !name.isBlank()) {
            url += "&name=" + name;
        }
        return makeRequest(url);
    }

    public String getPlayersByTeam(int teamId, int season) {
        String url = BASE_URL + "/players?team=" + teamId + "&season=" + season;
        return makeRequest(url);
    }

    public String getLeagues(Integer LeagueId, Integer season, List<String> code) {
        StringBuilder url = new StringBuilder(BASE_URL + "/leagues?");
        boolean hasParam = false;

        if (LeagueId != null) {
            url.append("id=").append(LeagueId);
            hasParam = true;
        }

        if (season != null) {
            if (hasParam) url.append("&");
            url.append("season=").append(season);
            hasParam = true;
        }

        if (code != null && code.size() > 0) {
            for (String c : code) {
                if (hasParam) url.append("&");
                url.append("code=").append(c);
                hasParam = true;
            }
        }

        return makeRequest(url.toString());
    }

    public String getFixtures(int leagueId, int season) {
        String url = BASE_URL + "/fixtures?league=" + leagueId + "&season=" + season;
        return makeRequest(url);
    }


    private String makeRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apisports-key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}