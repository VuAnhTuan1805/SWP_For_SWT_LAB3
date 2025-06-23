package com.swpteam.smokingcessation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.class
        }
)
@EnableScheduling
@EnableCaching
public class Swp391SmokingCessationSupportPlatformBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Swp391SmokingCessationSupportPlatformBeApplication.class, args);
    }

}
