package eu.menzani.concurrent;

import java.lang.annotation.*;

/**
 * Either the parameter's concrete type must be thread-safe, or a different instance must be supplied for each thread
 * invoking the method.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ThreadLocal {
}
