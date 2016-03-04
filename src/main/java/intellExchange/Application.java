package intellExchange;

import intellExchange.database.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Yao on 2/27/16.
 */

@SpringBootApplication
public class Application {
    public static void main(String [] args) {
        DataSource.init();
        SpringApplication.run(Application.class, args);
    }
}
