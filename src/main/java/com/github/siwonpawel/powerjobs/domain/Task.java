package com.github.siwonpawel.powerjobs.domain;

import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private BigInteger base;

    private BigInteger exponent;

    private BigInteger result;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(BigInteger base, BigInteger exponent) {
        this.base = base;
        this.exponent = exponent;
        this.status = TaskStatus.PREPARED;
    }
}
