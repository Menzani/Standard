package eu.menzani.test;

import eu.menzani.misc.OnlyThis;

import java.lang.reflect.AnnotatedElement;

abstract class TestElement {
    private final boolean executeOnlyThis;
    private boolean enabled = true;

    TestElement(AnnotatedElement reflected) {
        executeOnlyThis = reflected.getAnnotation(OnlyThis.class) != null;
    }

    boolean shouldExecuteOnlyThis() {
        return executeOnlyThis;
    }

    boolean isEnabled() {
        return enabled;
    }

    void disable() {
        enabled = false;
    }
}
