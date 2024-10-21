package com.dron.leon_scrapping.service;

import com.dron.leon_scrapping.entity.*;
import com.dron.leon_scrapping.printer.SportMatchPrinter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SportsServiceImpl implements SportsService {
    private final RestTemplate restTemplate;
    private final SportMatchPrinter sportMatchPrinter;

    private static final Logger logger = LoggerFactory.getLogger(SportsServiceImpl.class);
    private static final String SPORTS_URL = "https://leonbets.com/api-2/betline/sports?ctag=en-US&flags=urlv2";
    private static final String MATCHES_URL_TEMPLATE = "https://leonbets.com/api-2/betline/events/all?ctag=en-US&league_id=%d&hideClosed=true&flags=reg,urlv2,mm2,rrc,nodup";
    private static final List<String> TOP_SPORTS = List.of("football", "basketball", "tennis", "hockey");

    public void printTopLeaguesWithDetails() {
        logger.info("Fetching sports...");
        List<Sport> sports = fetchSports();

        logger.info("Filtering top leagues...");
        List<TopLeagueResponse> topLeagues = filterTopLeagues(sports);

        logger.info("Starting to fetch match details for each league...");
        List<CompletableFuture<LeagueDetailsResponse>> futures = topLeagues.stream()
                .map(league -> CompletableFuture.supplyAsync(() -> fetchLeagueDetails(league)))
                .toList();

        logger.info("Waiting for all league details to be fetched...");
        List<LeagueDetailsResponse> leaguesDetails = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        logger.info("Printing matches for all leagues...");
        leaguesDetails.forEach(sportMatchPrinter::printMatches);
    }

    private List<Sport> fetchSports() {
        try {
            logger.debug("Fetching sports from URL: {}", SPORTS_URL);
            Sport[] sportsArray = restTemplate.getForObject(SPORTS_URL, Sport[].class);
            logger.info("Fetched {} sports", (sportsArray != null) ? sportsArray.length : 0);
            return Optional.ofNullable(sportsArray).map(Arrays::asList).orElseGet(List::of);
        } catch (RestClientException e) {
            logger.error("Failed to fetch sports", e);
            return Collections.emptyList();
        }
    }

    private List<TopLeagueResponse> filterTopLeagues(List<Sport> sports) {
        return TOP_SPORTS.stream()
                .flatMap(sportName -> sports.stream()
                        .filter(sport -> sport.getName().equalsIgnoreCase(sportName))
                        .flatMap(sport -> sport.getRegions().stream()
                                .flatMap(region -> region.getLeagues().stream()
                                        .filter(League::isTop)
                                        .map(league -> TopLeagueResponse.builder()
                                                .sport(sportName)
                                                .leagueName(league.getName())
                                                .leagueUrl(league.getUrl())
                                                .leagueId(league.getId())
                                                .build()))))
                .collect(Collectors.toList());
    }

    private LeagueDetailsResponse fetchLeagueDetails(TopLeagueResponse league) {
        List<MatchDetails> matchDetails = fetchMatchesForLeague(league.getLeagueId());
        return LeagueDetailsResponse.builder()
                .sport(league.getSport())
                .leagueName(league.getLeagueName())
                .leagueUrl(league.getLeagueUrl())
                .leagueId(league.getLeagueId())
                .matchDetails(matchDetails)
                .build();
    }

    private List<MatchDetails> fetchMatchesForLeague(long leagueId) {
        String url = String.format(MATCHES_URL_TEMPLATE, leagueId);
        try {
            logger.debug("Fetching matches for league ID: {}", leagueId);
            EventResponse response = restTemplate.getForObject(url, EventResponse.class);
            logger.info("Fetched {} events for league ID: {}", Objects.requireNonNull(response).getEvents().size(), leagueId);
            return response.getEvents().stream()
                    .limit(2) // Get the first 2 matches
                    .map(this::extractMatchDetails)
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            logger.error("Failed to fetch matches for league ID: {}", leagueId, e);
            return Collections.emptyList();
        }
    }

    private MatchDetails extractMatchDetails(Event event) {
        List<TeamDetails> teamDetails = event.getCompetitors().stream()
                .map(competitor -> TeamDetails.builder()
                        .teamName(competitor.getName())
                        .logoUrl(competitor.getLogo())
                        .coefficients(extractCoefficients(event.getMarkets()))
                        .build())
                .collect(Collectors.toList());

        return MatchDetails.builder()
                .matchName(event.getName())
                .kickoff(event.getKickoff())
                .id(event.getId())
                .competitors(teamDetails)
                .markets(event.getMarkets())
                .build();
    }

    private List<Coefficient> extractCoefficients(List<Market> markets) {
        Set<Long> printedCoefficientIds = new HashSet<>();
        List<Coefficient> coefficients = new ArrayList<>();

        for (Market market : markets) {
            for (var runner : market.getRunners()) {
                if (runner.getName() != null && printedCoefficientIds.add(runner.getId())) {
                    coefficients.add(Coefficient.builder()
                            .name(runner.getName())
                            .price(runner.getPrice())
                            .id(runner.getId())
                            .build());
                }
            }
        }

        return coefficients;
    }
}