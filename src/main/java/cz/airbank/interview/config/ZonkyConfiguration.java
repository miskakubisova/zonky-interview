package cz.airbank.interview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("connector.zonky")
@Data
public class ZonkyConfiguration {
    private String endpointUrl;
}
