package eu.menzani.lang;

@FunctionalInterface
public interface FloatUnaryOperator {
    float applyAsFloat(float operand);

    default FloatUnaryOperator compose(FloatUnaryOperator before) {
        Assume.notNull(before);
        return (float v) -> applyAsFloat(before.applyAsFloat(v));
    }

    default FloatUnaryOperator andThen(FloatUnaryOperator after) {
        Assume.notNull(after);
        return (float t) -> after.applyAsFloat(applyAsFloat(t));
    }

    static FloatUnaryOperator identity() {
        return t -> t;
    }
}
