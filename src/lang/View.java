package eu.menzani.lang;

import java.lang.annotation.*;

/**
 * When applied to a method, the returned object must not be modified.
 * When applied to a field, the field must not be written to.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface View {
}
