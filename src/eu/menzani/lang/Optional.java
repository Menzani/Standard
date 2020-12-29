package eu.menzani.lang;

import java.lang.annotation.*;

/**
 * Use of this annotation implies that all non-annotated types are not optional, that is they may not take the
 * {@code null} value.
 * <p>
 * However, non-public types may not be annotated if optionality is clear from the context.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Optional {
}
