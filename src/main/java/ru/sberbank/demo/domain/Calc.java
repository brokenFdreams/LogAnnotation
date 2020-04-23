package ru.sberbank.demo.domain;

import com.jcabi.aspects.Loggable;
import org.springframework.stereotype.Component;
import ru.sberbank.demo.SkipArgument;

import java.util.List;

@Component
public class Calc {

    @Loggable
    @SkipArgument(skipArgs = 1)
    public int sum(int a, int b, List<Integer> list) {
        return a + b;
    }
}
