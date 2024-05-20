package org.vumc.simplejob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleJobApplication implements CommandLineRunner {

  @Value("${outcome:complete}")
  private String outcome;

  @Value("${after:0}")
  private int after;

  public static void main(String[] args) {
    SpringApplication.run(SimpleJobApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.printf("Job is running that will %s after %s seconds\n", outcome, after);

    if (after > 0) {
      Thread.sleep(after * 1000);
    }

    if ("complete".equalsIgnoreCase(outcome)) {
      System.out.println("Job complete");
    } else {
      System.out.println("Job failed");
      System.exit(47);
    }
  }
}
