package net.jovacorp.retroboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class RetroboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(RetroboardApplication.class, args);
  }
}
