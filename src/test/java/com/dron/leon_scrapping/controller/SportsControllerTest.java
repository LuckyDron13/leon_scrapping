package com.dron.leon_scrapping.controller;

import com.dron.leon_scrapping.entity.LeagueDetailsResponse;
import com.dron.leon_scrapping.service.SportsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class SportsControllerTest {

    @Mock
    private SportsServiceImpl sportsServiceImpl;

    @InjectMocks
    private SportsController sportsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTopLeaguesWithDetails() {
        List<LeagueDetailsResponse> result = sportsController.getTopLeaguesWithDetails();
        assertTrue(result.isEmpty());
        verify(sportsServiceImpl).printTopLeaguesWithDetails();
    }
}