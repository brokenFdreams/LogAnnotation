package ru.sberbank.demo.domain;

import com.jcabi.aspects.Loggable;
import org.springframework.stereotype.Component;
import ru.sberbank.demo.SkipParameter;

import java.util.List;

@Component
public class Calc {

    @Loggable
    public int sum(@SkipParameter int a, int b, List<Integer> list) {
        return a + b;
    }
}
