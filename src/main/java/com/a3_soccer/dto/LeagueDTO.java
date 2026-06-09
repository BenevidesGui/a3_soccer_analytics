package com.a3_soccer.dto;

public class LeagueDTO {

    private Integer id;
    private String name;
    private String type;
    private String logo;

    public LeagueDTO(){}

    public LeagueDTO(Integer id, String name, String type, String logo) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
