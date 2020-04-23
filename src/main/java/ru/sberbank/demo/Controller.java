package ru.sberbank.demo;

import com.sun.tools.javac.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.demo.calc.Calc;

@RestController
public class Controller {
    private Calc calc;

    public Controller(Calc calc) {
        this.calc = calc;
    }

    @GetMapping("/sum")
    public int getSum(int a, int b) {
        return calc.sum(a, b, List.of(1, 2, 3, 4));
    }
}
