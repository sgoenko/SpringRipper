package hay.example.annotation.quoters;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profiling
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    public int getRepeat() {
        return repeat;
    }

    private String message = "I'll be back";

    public void setMessage(String message) {
        this.message = message;
    }

    @PostConstruct
    public void init() {
        System.out.print("Phase 2: ");
        System.out.println(repeat);
    }

    public TerminatorQuoter() {
        System.out.print("Phase 1: ");
        System.out.println(repeat);
    }

    @Override
    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3.");

        for (int i = 0; i < repeat; i++) {
            System.out.println(message);
        }
    }
}
