package charViewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

/**
 * Main class to bootstrap the Spring Context including the embedded webserver used to deliver the website. The
 * resources will be delivered automatically so no configuration required for now.
 */
@SpringBootApplication
public class ApplicationStarter {

  public static void main(final String... args) {
    SpringApplication app = new SpringApplication(ApplicationStarter.class);
    app.addListeners(new ApplicationPidFileWriter());

    app.run(args);
  }
}
