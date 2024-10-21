package com.dron.leon_scrapping.printer;

import com.dron.leon_scrapping.entity.LeagueDetailsResponse;
import com.dron.leon_scrapping.entity.MatchDetails;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CommonMatchPrinter implements SportMatchPrinter {
    @Override
    public void printMatches(LeagueDetailsResponse leagueDetails) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'");

        // Check if all matches in the league have no competitors
        boolean allMatchesNoCompetitors = leagueDetails.getMatchDetails().stream()
                .allMatch(match -> match.getCompetitors().isEmpty());

        // Skip printing if all matches have no competitors
        if (allMatchesNoCompetitors) {
            System.out.println("No competitors available for league: " + leagueDetails.getLeagueName());
            return;
        }

        // Print the sport and league name
        System.out.println(leagueDetails.getSport() + ", " + leagueDetails.getLeagueName());

        for (MatchDetails match : leagueDetails.getMatchDetails()) {
            // Print league name and match details
            System.out.println(leagueDetails.getLeagueName() + " - Match: " + match.getMatchName() + ", "
                    + dateFormat.format(new Date(match.getKickoff())) + ", "
                    + match.getId());

            // Print all markets and their odds
            for (var market : match.getMarkets()) {
                System.out.println(market.getName()); // Print the market name
                
                if (!market.getRunners().isEmpty()) {
                    for (var runner : market.getRunners()) {
                        System.out.println(runner.getName() + ", "
                                + runner.getPrice() + ", "
                                + runner.getId());
                    }
                }
            }

            // Print a line separator after each match
            System.out.println("___________________________________________________________________________________________");
        }
    }
}
