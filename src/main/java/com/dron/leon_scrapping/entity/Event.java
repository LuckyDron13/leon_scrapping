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
public class Event {
    private long id;
    private String name;
    private List<Competitor> competitors;
    private List<Market> markets;
    private long kickoff;
}