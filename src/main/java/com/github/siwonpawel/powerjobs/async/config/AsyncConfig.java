package com.github.siwonpawel.powerjobs.async.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@Validated
@PropertySource("classpath:async-config.yaml")
@ConfigurationProperties(prefix = "powerjobs")
public class AsyncConfig {

    @Min(1)
    @Max(10)
    @Value("${minThreadCount:2}")
    private Integer corePoolSize;

    @Value("${threadPrefix:powerjob_}")
    private String threadPrefix;

}
