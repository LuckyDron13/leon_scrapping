package com.dron.leon_scrapping.controller;

import com.dron.leon_scrapping.entity.LeagueDetailsResponse;
import com.dron.leon_scrapping.service.SportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SportsController {

    private final SportsService sportsService;

    @GetMapping("/top-leagues/details")
    public List<LeagueDetailsResponse> getTopLeaguesWithDetails() {
        sportsService.printTopLeaguesWithDetails();
         return List.of();
    }
}
