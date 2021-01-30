package eu.menzani.test;

import eu.menzani.misc.OnlyThis;

import java.lang.reflect.AnnotatedElement;

abstract class TestElement {
    private final boolean executeOnlyThis;
    private boolean disabled;

    TestElement(AnnotatedElement reflected) {
        executeOnlyThis = reflected.getAnnotation(OnlyThis.class) != null;
    }

    boolean shouldExecuteOnlyThis() {
        return executeOnlyThis;
    }

    boolean isDisabled() {
        return disabled;
    }

    void disable() {
        disabled = true;
    }
}
