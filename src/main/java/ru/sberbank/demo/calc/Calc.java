package ru.sberbank.demo.calc;

import com.jcabi.aspects.Loggable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calc {

    @Loggable(value = Loggable.WARN, prepend = true)
    public int sum(int a, int b, List<Integer> list) {
        return a + b;
    }
}
