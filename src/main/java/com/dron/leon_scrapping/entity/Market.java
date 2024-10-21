package com.dron.leon_scrapping.entity;// Market.java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Market {
    private String name; // Name of the market, e.g., "Draw No Bet"
    private List<Coefficient> runners; // List of coefficients for this market
}