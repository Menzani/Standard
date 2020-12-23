package eu.menzani.lang;

import java.lang.annotation.*;

/**
 * Use of this annotation implies that all non-annotated types are not optional, that is they may not take the
 * {@code null} value.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Optional {
}
