package com.github.siwonpawel.powerjobs.domain.vo.fromfrontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTaskBody {

    BigInteger base;
    BigInteger exponent;

}
