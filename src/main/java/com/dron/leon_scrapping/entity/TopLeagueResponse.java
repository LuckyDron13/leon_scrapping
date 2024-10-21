package com.dron.leon_scrapping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopLeagueResponse {
    private String sport;
    private String leagueName;
    private String leagueUrl; // URL of the league
    private long leagueId; // ID of the league
}
