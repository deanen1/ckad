package org.vumc.simplejob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleJobApplication implements CommandLineRunner {
  private static final String COMPLETE_OUTCOME = "complete";
  private static final String FAIL_OUTCOME = "fail";

  @Value("${outcome:complete}")
  private String outcome;

  @Value("${after:0}")
  private int after;

  @Value("${exitCode:0}")
  private int exitCode;

  public static void main(String[] args) {
    SpringApplication.run(SimpleJobApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (COMPLETE_OUTCOME.equalsIgnoreCase(outcome) || FAIL_OUTCOME.equalsIgnoreCase(outcome)) {
      if (COMPLETE_OUTCOME.equalsIgnoreCase(outcome) && exitCode != 0) {
        System.out.println("Exit code must be zero when outcome is 'complete'");
        System.exit(2);
      }

      System.out.printf("Job is running that will %s after %s seconds with an exit code of %d\n",
          outcome, after, exitCode);

      if (after > 0) {
        Thread.sleep(after * 1000);
      }

      if (COMPLETE_OUTCOME.equalsIgnoreCase(outcome)) {
        System.out.println("Job complete");
      } else {
        System.out.println("Job failed");
        System.exit(exitCode);
      }
    } else {
      System.out.println(
          "Outcome must either be 'complete' or 'fail'. Defaults to 'complete' if not provided");
      System.exit(2);
    }
  }
}
