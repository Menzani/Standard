package eu.menzani.lang;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface RemovedFromStackTrace {
    String value() default "eu.menzani.error.GlobalStackTraceFilter.getInstance()";
}
