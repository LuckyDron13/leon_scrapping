package com.dron.leon_scrapping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueDetailsResponse {
    private String sport;
    private String leagueName;
    private String leagueUrl; // URL of the league
    private long leagueId; // ID of the league
    private List<MatchDetails> matchDetails;
}
