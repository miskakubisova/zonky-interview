package cz.airbank.interview.controller;

import cz.airbank.interview.model.ZonkyLoan;
import cz.airbank.interview.service.ZonkyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * I used this controller only for test purposes, to test the flow before running cron job.
 */
@Data
@RestController
@AllArgsConstructor
public class ZonkyController {
    private ZonkyService zonkyService;

    @GetMapping("zonky/loan")
    public List<ZonkyLoan> getZonkyLoans(
            @RequestBody
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    String dateFrom
    ){
        return zonkyService.getNewLoans(dateFrom);
    }
}
