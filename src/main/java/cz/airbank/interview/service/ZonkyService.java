package cz.airbank.interview.service;

import cz.airbank.interview.model.ZonkyLoan;
import cz.airbank.interview.provider.ZonkyProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This service might be useless in this simple scenario, but it is good practice keeping the layers apart.
 * Also, I didn't use interface for this service as I would normally do, but I found it useless this time.
 *
 * I know that it is not the best solution to use set here to store the loans, because I am only adding
 * to the set so the time complexity of comparing the newly loaded loans and the saved ones is increasing,
 * but I hope for this simple solution it would be enough
 */
@Service
@AllArgsConstructor
public class ZonkyService {
    private ZonkyProvider zonkyProvider;
    private Set<ZonkyLoan> zonkyLoans;

    public List<ZonkyLoan> getNewLoans(String dateFrom){
        var loans = zonkyProvider.getLoansFromDate(dateFrom);
        loans = loans.stream().filter(loan -> !zonkyLoans.contains(loan)).collect(Collectors.toList());
        zonkyLoans.addAll(loans);
        return loans;
    }
}
