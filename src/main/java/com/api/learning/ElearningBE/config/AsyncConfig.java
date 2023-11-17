package com.api.learning.ElearningBE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Value("${spring.pool.size}")
    private Integer threadPoolSize;
    @Value("${spring.pool.queue.size}")
    private Integer threadQueuePoolSize;

    @Bean(name = "threadPoolTaskExecutor")
    public TaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(threadQueuePoolSize);
        threadPoolTaskExecutor.setThreadNamePrefix("elearning-pool-");
        threadPoolTaskExecutor.initialize();
        return new DelegatingSecurityContextAsyncTaskExecutor(threadPoolTaskExecutor);
    }
    /**
     *
     * ThreadPoolExecutor and ThreadPoolTaskExecutor are both Executors, but they come with additional parameters as follows:
     *
     * corePoolSize: The default number of threads in the pool.
     * maxPoolSize: The maximum number of threads in the pool.
     * queueCapacity: The maximum capacity of the BlockingQueue.
     *
     * # Operating Principles
     *
     * For example, with ThreadPoolExecutor configured as:
     *
     * corePoolSize: 5
     * maxPoolSize: 15
     * queueCapacity: 100
     *
     * When a request occurs, it will create a maximum of 5 threads in the pool (corePoolSize).
     * When the number of threads exceeds 5, it will be placed in the queue.
     * When the queue reaches its full capacity of 100 (queueCapacity), only then will it start creating new threads.
     * The maximum number of new threads created is 15 (maxPoolSize).
     * If the number of requests exceeds the limit of 15 threads, the requests will be rejected!
     *
     * */
}
