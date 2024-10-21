package com.dron.leon_scrapping.entity;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Region {
    private List<League> leagues;
}
