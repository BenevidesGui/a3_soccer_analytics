package com.a3_soccer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class LeagueCard {

    private String name;
    private int id;
    private String logo;

    public LeagueCard(String name, int id) {
        this.name = name;
        this.id = id;
        this.logo = "https://media.api-sports.io/football/leagues/" + id + ".png";
    }
}
