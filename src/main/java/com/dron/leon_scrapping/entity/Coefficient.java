package com.dron.leon_scrapping.entity;// Coefficient.java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Coefficient {
    private String name; // Name of the team or market option
    private double price; // The coefficient value
    private long id; // Unique ID for the coefficient
}