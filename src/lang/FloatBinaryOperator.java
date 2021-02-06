package eu.menzani.lang;

@FunctionalInterface
public interface FloatBinaryOperator {
    float applyAsFloat(float left, float right);
}
