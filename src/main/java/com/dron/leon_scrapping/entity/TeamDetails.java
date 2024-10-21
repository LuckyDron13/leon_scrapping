package com.dron.leon_scrapping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TeamDetails {
    private String teamName;
    private String logoUrl; // or whatever type you're using
    private List<Coefficient> coefficients;
}
