package ru.sberbank.demo.services;


import com.jcabi.aspects.Loggable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sberbank.demo.SkipArgument;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {
    private final Logger logger = LoggerFactory.getLogger(WebServiceLogger.class);


    @Around("@annotation(com.jcabi.aspects.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        String resultToString = result.toString();
        String args = Arrays.toString(point.getArgs());
        String methodName = ((MethodSignature) point.getSignature()).getMethod().getName();

        skipArgs(point);

        Loggable loggable = getLoggable(point);
        switch (loggable.value()) {
            case Loggable.TRACE:
                logger.trace(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        resultToString
                ));
                break;
            case Loggable.INFO :
                logger.info(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        resultToString
                ));
                break;
            case Loggable.DEBUG:
                logger.debug(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        resultToString
                ));
                break;
            case Loggable.WARN:
                logger.warn(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        resultToString
                ));
                break;
            case Loggable.ERROR:
                logger.error(String.format(
                        "method: %s, args: %s, result: %s",
                        methodName,
                        args,
                        resultToString
                ));
                break;
        }
        return result;
    }

    private void skipArgs(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        SkipArgument skipArgument = method.getAnnotation(SkipArgument.class);
        if (skipArgument != null)
            logger.info(Arrays.toString(skipArgument.skipArgs()));
    }

    private Loggable getLoggable(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(Loggable.class);
    }
}
