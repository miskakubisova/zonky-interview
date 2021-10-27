package cz.airbank.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Simplified representation of zonky loan from API. I left out some fields that was not necessary for me.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonkyLoan {
    private int id;
    private String url;
    private String name;
    private String purpose;
    private int userId;
    private double amount;
    private String currency;
    private double remainingInvestment;
    private Date datePublished;
    private Date deadline;
}
