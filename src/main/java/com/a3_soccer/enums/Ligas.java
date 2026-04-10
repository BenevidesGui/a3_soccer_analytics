package com.a3_soccer.enums;

public enum Ligas {

    BRASILEIRAO(71, "Brasileirão"),
    PREMIER_LEAGUE(39, "Premier League"),
    LA_LIGA(140, "La Liga"),
    SERIE_A(135, "Serie A"),
    BUNDESLIGA(78, "Bundesliga");

    private final int id;
    private final String name;

    Ligas(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Ligas fromString(String value) {
        return java.util.Arrays.stream(Ligas.values())
                .filter(l -> l.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Liga não encontrada: " + value));
    }
}
