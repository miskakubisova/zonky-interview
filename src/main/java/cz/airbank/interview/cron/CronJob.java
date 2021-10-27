package cz.airbank.interview.cron;

import cz.airbank.interview.service.ZonkyService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This cron job is called every 5 minutes to load loans that were published in last 5 minutes.
 */
@Component
@AllArgsConstructor
public class CronJob {
    private ZonkyService zonkyService;

    @Scheduled(fixedRateString = "PT5M")
    public void scheduleFixedRateTask() {
        var dateTime = ZonedDateTime.now().minus(5, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        var newLoans = zonkyService.getNewLoans(dateTime);

        if (newLoans.isEmpty()){
            System.out.println("No new loans in last 5 minutes, current time: " + dateTime);
        } else {
            System.out.println("In last 5 minutes (current time: " + dateTime + " ) there were these new loans:");
            newLoans.forEach(System.out::println);
        }
    }
}

