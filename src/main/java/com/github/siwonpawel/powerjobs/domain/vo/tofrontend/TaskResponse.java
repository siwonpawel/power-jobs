package com.github.siwonpawel.powerjobs.domain.vo.tofrontend;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {

    Long id;
    BigInteger base;
    BigInteger exponent;
    BigInteger result;
    String status;
    String progress;

}
