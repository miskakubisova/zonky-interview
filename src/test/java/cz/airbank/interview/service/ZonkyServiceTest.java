package cz.airbank.interview.service;

import cz.airbank.interview.model.ZonkyLoan;
import cz.airbank.interview.provider.ZonkyProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZonkyServiceTest {

    @Mock
    ZonkyProvider zonkyProvider;

    private ZonkyService zonkyService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
        zonkyService = new ZonkyService(zonkyProvider, new HashSet<>());
    }

    /**
     * The purpose is to test that it does not fail when no zonky loans are returned from API.
     */
    @Test
    public void getNewLoans_empty(){
        String dateFrom = ZonedDateTime.now().minus(95, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Mockito.when(zonkyProvider.getLoansFromDate(dateFrom)).thenReturn(List.of());

        var actual = zonkyService.getNewLoans(dateFrom);
        Assertions.assertThat(actual).isEmpty();
    }

    /**
     * The purpose is to test that when you call method for the first time, all loans should be returned.
     */
    @Test
    public void getNewLoans_notEmptyNoDuplicate(){
        var now = Instant.now();
        var zonkyLoans = getZonkyLoans(now);
        String dateFrom = ZonedDateTime.now().minus(95, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Mockito.when(zonkyProvider.getLoansFromDate(dateFrom)).thenReturn(zonkyLoans);

        var actual = zonkyService.getNewLoans(dateFrom);
        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(zonkyLoans);
    }

    /**
     * The purpose is to test that when you call the method multiple times with the same date, there are no data returned after the second call.
     * If there would be some new data it would be returned of course.
     */
    @Test
    public void getNewLoans_notEmptyDuplicate(){
        var now = Instant.now();
        var zonkyLoans = getZonkyLoans(now);
        String dateFrom = ZonedDateTime.now().minus(95, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Mockito.when(zonkyProvider.getLoansFromDate(dateFrom)).thenReturn(zonkyLoans);

        zonkyService.getNewLoans(dateFrom);
        var actual = zonkyService.getNewLoans(dateFrom);
        Assertions.assertThat(actual).isEmpty();
    }

//
// Private helper methods
//

    private List<ZonkyLoan> getZonkyLoans(Instant now){
        return List.of(
                new ZonkyLoan(1, "http://lala.sk/1", "pujcka 1", "neznamy", 12, 1455600.0, "CZK", 25630.0, Date.from(now.minus(105, ChronoUnit.MINUTES)), Date.from(now.plus(2, ChronoUnit.DAYS))),
                new ZonkyLoan(2, "http://lala.sk/2", "pujcka 2", "neznamy", 13, 69500.0, "CZK", 50000.0, Date.from(now.minus(80, ChronoUnit.MINUTES)), Date.from(now.plus(2, ChronoUnit.DAYS))),
                new ZonkyLoan(3, "http://lala.sk/3", "pujcka 3", "neznamy", 14, 780000.0, "CZK", 8000.0, Date.from(now.minus(100, ChronoUnit.MINUTES)), Date.from(now.plus(2, ChronoUnit.DAYS))),
                new ZonkyLoan(4, "http://lala.sk/4", "pujcka 4", "neznamy", 15, 95200.0, "CZK", 20000.0, Date.from(now.minus(15, ChronoUnit.MINUTES)), Date.from(now.plus(2, ChronoUnit.DAYS))),
                new ZonkyLoan(5, "http://lala.sk/5", "pujcka 5", "neznamy", 16, 325000.0, "CZK", 65250.0, Date.from(now.minus(246, ChronoUnit.MINUTES)), Date.from(now.plus(2, ChronoUnit.DAYS)))
        );
    }
}