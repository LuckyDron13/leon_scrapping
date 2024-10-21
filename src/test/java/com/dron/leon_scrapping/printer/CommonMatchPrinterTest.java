package com.dron.leon_scrapping.printer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dron.leon_scrapping.entity.Coefficient;
import com.dron.leon_scrapping.entity.LeagueDetailsResponse;
import com.dron.leon_scrapping.entity.MatchDetails;
import com.dron.leon_scrapping.entity.Market;
import com.dron.leon_scrapping.entity.TeamDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class CommonMatchPrinterTest {

    @InjectMocks
    private CommonMatchPrinter printer;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testPrintMatches() {
        // Create test data
        LeagueDetailsResponse leagueDetails = createTestLeagueDetails();

        // Call the method to test
        printer.printMatches(leagueDetails);

        // Expected output
        String expectedOutput = "Football, Premier League\n" +
                "Premier League - Match: Arsenal - Chelsea, 2023-10-20 15:00:00 UTC, 1\n" +
                "Winner\n" +
                "1, 1.5, 101\n" +
                "2, 2.8, 102\n" +
                "Total\n" +
                "Over, 1.8, 201\n" +
                "Under, 1.9, 202\n" +
                "___________________________________________________________________________________________\n";

        // Normalize line separators
        String normalizedOutput = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");

        // Assert the printed output
        assertEquals(normalizedExpectedOutput, normalizedOutput);
    }

    private LeagueDetailsResponse createTestLeagueDetails() {
        // Create test markets
        Market winnerMarket = new Market("Winner", Arrays.asList(
                Coefficient.builder().name("1").price(1.5).id(101).build(),
                Coefficient.builder().name("2").price(2.8).id(102).build()
        ));

        Market totalMarket = new Market("Total", Arrays.asList(
                Coefficient.builder().name("Over").price(1.8).id(201).build(),
                Coefficient.builder().name("Under").price(1.9).id(202).build()
        ));

        // Create test match details
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        long matchTime = 0;
        try {
            matchTime = dateFormat.parse("2023-10-20 12:00:00 UTC").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MatchDetails matchDetails = MatchDetails.builder()
                .matchName("Arsenal - Chelsea")
                .kickoff(matchTime) // Use specific time for testing
                .id(1L) // Match ID
                .competitors(Arrays.asList(
                        TeamDetails.builder().teamName("Arsenal").logoUrl("logo1.png").coefficients(Arrays.asList()).build(),
                        TeamDetails.builder().teamName("Chelsea").logoUrl("logo2.png").coefficients(Arrays.asList()).build()
                ))
                .markets(Arrays.asList(winnerMarket, totalMarket))
                .build();

        // Return a league details response
        return LeagueDetailsResponse.builder()
                .sport("Football")
                .leagueName("Premier League")
                .leagueUrl("url")
                .leagueId(1L)
                .matchDetails(Arrays.asList(matchDetails))
                .build();
    }
}