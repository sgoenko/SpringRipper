package hay.example.annotation;

import hay.example.annotation.quoters.Quoter;
import hay.example.annotation.quoters.TerminatorQuoter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class AnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

//            while (true) {
//                ctx.getBean(Quoter.class).sayQuote();
//                Thread.sleep(100);
//            }
        };
    }

}
