package ru.sberbank.demo.services;


import com.jcabi.aspects.Loggable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sberbank.demo.SkipParameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {
    private final Logger logger = LoggerFactory.getLogger(WebServiceLogger.class);

    @Around("@annotation(com.jcabi.aspects.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = ((MethodSignature) point.getSignature()).getMethod().getName();
        String args = getArgsToString(point);
        Object result = point.proceed();
        String resultToString = result.toString();

        log(methodName, args, resultToString, getLoggable(point));
        return result;
    }

    private String getArgsToString(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = point.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(SkipParameter.class))
                args[i] = null;
        }
        return Arrays.toString(args);
    }

    private void log(String methodName, String args, String result, Loggable loggable) {
        switch (loggable.value()) {
            case Loggable.TRACE:
                logger.trace(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        result
                ));
                break;
            case Loggable.INFO :
                logger.info(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        result
                ));
                break;
            case Loggable.DEBUG:
                logger.debug(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        result
                ));
                break;
            case Loggable.WARN:
                logger.warn(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        result
                ));
                break;
            case Loggable.ERROR:
                logger.error(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        result
                ));
                break;
        }

    }

    private Loggable getLoggable(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(Loggable.class);
    }
}
