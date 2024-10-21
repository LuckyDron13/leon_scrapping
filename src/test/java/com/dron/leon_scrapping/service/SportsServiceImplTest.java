package com.dron.leon_scrapping.service;

import com.dron.leon_scrapping.entity.LeagueDetailsResponse;
import com.dron.leon_scrapping.entity.Sport;
import com.dron.leon_scrapping.printer.SportMatchPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class SportsServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SportMatchPrinter sportMatchPrinter;

    @InjectMocks
    private SportsServiceImpl sportsServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void printTopLeaguesWithDetails() {
        // Mock the fetchSports method to return an empty list
        when(restTemplate.getForObject(anyString(), eq(Sport[].class))).thenReturn(new Sport[]{});

        // Call the method to test
        sportsServiceImpl.printTopLeaguesWithDetails();

        // Verify that the printMatches method was never called
        verify(sportMatchPrinter, never()).printMatches(any(LeagueDetailsResponse.class));
    }
}