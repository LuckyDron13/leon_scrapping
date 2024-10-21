package com.dron.leon_scrapping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MatchDetails {
    private String matchName;
    private long kickoff; // or Date if you're using Date
    private long id;
    private List<TeamDetails> competitors;
    private List<Market> markets; // Add this line for markets
}