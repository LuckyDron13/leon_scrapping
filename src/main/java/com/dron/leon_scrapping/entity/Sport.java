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
public class Sport {
    private long id;
    private String name;
    private int weight;
    private String family;
    private List<Region> regions;
}
