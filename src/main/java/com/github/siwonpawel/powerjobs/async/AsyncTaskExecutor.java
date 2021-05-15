package com.github.siwonpawel.powerjobs.async;

import com.github.siwonpawel.powerjobs.async.config.AsyncConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class AsyncTaskExecutor {

    @NonNull AsyncConfig config;

    @Transactional
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(config.getThreadPrefix());
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.initialize();

        return executor;
    }
}
