package com.dron.leon_scrapping.config;

import com.dron.leon_scrapping.service.SportsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledTasks {
    private final SportsServiceImpl sportsServiceImpl;

    @Scheduled(fixedRate = 3_000_000)
    public void printTopLeaguesWithDetails() {
        this.sportsServiceImpl.printTopLeaguesWithDetails();
    }

}