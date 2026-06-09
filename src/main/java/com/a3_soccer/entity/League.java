package com.a3_soccer.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "league",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"league_id", "season"})
        }
)
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseId;

    @Column(name = "league_id")
    private Integer leagueId;

    private String name;

    private String type;

    private String logo;

    @Column(nullable = false)
    private Integer season;

    @Column(nullable = false)
    private Integer partidas;

    @Column(name = "total_gols", nullable = false)
    private Long totalGols;

    @Column(name = "best_player_name")
    private String bestPlayerName;

    @Column(name = "best_player_goals")
    private Integer bestPlayerGoals;



    public League() {}

    public League(Long databaseId, Integer leagueId, Integer season,
                  String name, String type, String logo,
                  Integer partidas, Long totalGols,
                  String bestPlayerName, Integer bestPlayerGoals) {

        this.databaseId = databaseId;
        this.leagueId = leagueId;
        this.season = season;
        this.name = name;
        this.type = type;
        this.logo = logo;
        this.partidas = partidas;
        this.totalGols = totalGols;
        this.bestPlayerName = bestPlayerName;
        this.bestPlayerGoals = bestPlayerGoals;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public Integer getId() {
        return leagueId;
    }

    public void setId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getPartidas() {
        return partidas;
    }

    public void setPartidas(Integer partidas) {
        this.partidas = partidas;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getTotalGols() {
        return totalGols;
    }

    public void setTotalGols(Long totalGols) {
        this.totalGols = totalGols;
    }

    public Integer getBestPlayerGoals() {
        return bestPlayerGoals;
    }

    public void setBestPlayerGoals(Integer bestPlayerGoals) {
        this.bestPlayerGoals = bestPlayerGoals;
    }

    public String getBestPlayerName() {
        return bestPlayerName;
    }

    public void setBestPlayerName(String bestPlayerName) {
        this.bestPlayerName = bestPlayerName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return Objects.equals(databaseId, league.databaseId) && Objects.equals(leagueId, league.leagueId) && Objects.equals(name, league.name) && Objects.equals(type, league.type) && Objects.equals(logo, league.logo) && Objects.equals(season, league.season) && Objects.equals(partidas, league.partidas) && Objects.equals(totalGols, league.totalGols) && Objects.equals(bestPlayerName, league.bestPlayerName) && Objects.equals(bestPlayerGoals, league.bestPlayerGoals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseId, leagueId, name, type, logo, season, partidas, totalGols, bestPlayerName, bestPlayerGoals);
    }
}