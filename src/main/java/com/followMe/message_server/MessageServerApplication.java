package com.followMe.message_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackages = "com.followMe")
@EntityScan(basePackages = "com.followMe")
public class MessageServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageServerApplication.class, args);
  }
}
