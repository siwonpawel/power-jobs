package com.github.siwonpawel.powerjobs.async.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:async-config-test.yaml")
class AsyncConfigTest {

    @Autowired
    AsyncConfig asyncConfig;

    @Test
    void getCorePoolSize() {
        Integer corePoolSize = asyncConfig.getCorePoolSize();

        assertEquals(2, corePoolSize);
    }

    @Test
    void getThreadPrefix() {
        String threadPrefix = asyncConfig.getThreadPrefix();

        assertEquals("powerjob_worker_", threadPrefix);
    }
}