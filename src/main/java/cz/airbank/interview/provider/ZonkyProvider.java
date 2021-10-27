package cz.airbank.interview.provider;

import cz.airbank.interview.config.ZonkyConfiguration;
import cz.airbank.interview.model.ZonkyLoan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Simple provider that calls zonky marketplace API using {@link RestTemplate}.
 * API url is read from configuration file
 */
@Slf4j
@Component
public class ZonkyProvider {
    public static final String GET_LOANS_URL = "datePublished__gte={datePublished}&fields={fields}";
    public static final String FIELDS_PARAM = "fields";
    public static final String FIELDS_SPECIFICATION = "id,url,name,purpose,amount,currency,remainingInvestment,datePublished,deadline";
    public static final String DATE_PUBLISHED_PARAM = "datePublished";
    private final ZonkyConfiguration configuration;
    private final RestTemplate restTemplate;

    public ZonkyProvider(ZonkyConfiguration configuration) {
        this.configuration = configuration;
        this.restTemplate = new RestTemplate();
    }

    public List<ZonkyLoan> getLoansFromDate(String dateFrom){
        Map<String, String> params = Map.of(DATE_PUBLISHED_PARAM, dateFrom, FIELDS_PARAM, FIELDS_SPECIFICATION);
        var uri = UriComponentsBuilder
                .fromHttpUrl(configuration.getEndpointUrl())
                .query(GET_LOANS_URL)
                .build(params);
        var response = Optional.ofNullable(restTemplate.getForObject(uri, ZonkyLoan[].class)).orElse(new ZonkyLoan[]{});
        return List.of(response);
    }
}
