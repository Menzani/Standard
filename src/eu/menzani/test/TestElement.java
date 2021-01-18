package eu.menzani.test;

import java.lang.reflect.AnnotatedElement;

abstract class TestElement {
    private final boolean executeOnlyThis;

    TestElement(AnnotatedElement reflected) {
        executeOnlyThis = reflected.getAnnotation(OnlyThis.class) != null;
    }

    boolean shouldExecuteOnlyThis() {
        return executeOnlyThis;
    }
}
