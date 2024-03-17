package zerovase.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication

public class StockApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(StockApplication.class, args);
    }
}