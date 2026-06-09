package com.a3_soccer.dto;

import java.util.List;

public class LeagueApiResponseDTO {

    private List<LeagueWrapperDTO> response;

    public List<LeagueWrapperDTO> getResponse() {
        return response;
    }

    public void setResponse(List<LeagueWrapperDTO> response) {
        this.response = response;
    }
}