package eu.menzani.lang;

import eu.menzani.struct.ConcurrentKeyedCounter;
import eu.menzani.struct.KeyedCounter;
import net.bytebuddy.ByteBuddy;

import java.lang.reflect.Modifier;

/**
 * Copies a class to allow inlining of polymorphic calls.
 *
 * <pre>{@code
 * CopiedClass<Api> copiedClass = CopiedClass.of(Impl.class);
 *
 * Invokable<Api> invokable = copiedClass.getConstructor(int.class);
 * Invokable<Api> invokable = copiedClass.getFactoryMethod("getInstance", int.class);
 *
 * Api api = invokable.call(5);
 * }</pre>
 *
 * @param <T> a superclass or superinterface used to represent the object
 */
public class CopiedClass<T> {
    private static final ByteBuddy byteBuddy = new ByteBuddy();
    private static final KeyedCounter<Class<?>> id = new ConcurrentKeyedCounter<>();

    private final Class<T> copy;
    private final Class<?> original;

    public static <T> CopiedClass<T> of(Class<?> original) {
        if (Modifier.isAbstract(original.getModifiers())) {
            throw new IllegalArgumentException("original must be concrete.");
        }
        return new CopiedClass<>(byteBuddy
                .redefine(original)
                .name(original.getName() + "$Copy" + id.increment(original))
                .make()
                .load(original.getClassLoader(), ByteBuddyClassLoadingStrategy.INHERIT_PROTECTION_DOMAIN)
                .getLoaded(), original);
    }

    @SuppressWarnings("unchecked")
    private CopiedClass(Class<?> copy, Class<?> original) {
        Assert.notEqual(copy, original);
        this.copy = (Class<T>) copy;
        this.original = original;
    }

    public Class<T> getCopy() {
        return copy;
    }

    public Class<?> getOriginal() {
        return original;
    }

    public Invokable<T> getConstructor(Class<?>... parameterTypes) {
        Invokable<T> constructor = Invokable.ofConstructor(copy, parameterTypes);
        constructor.forceAccessible();
        return constructor;
    }

    /**
     * The method must be static, and it must return a subtype of the original class.
     */
    public Invokable<T> getFactoryMethod(String name, Class<?>... parameterTypes) {
        Method<T> method = Invokable.ofMethod(copy, name, parameterTypes);
        if (!method.isStatic()) {
            throw new IllegalArgumentException("Method must be static.");
        }
        Class<?> returnType = method.getExecutable().getReturnType();
        if (returnType != copy && !original.isAssignableFrom(returnType)) {
            throw new IllegalArgumentException("Method must be a factory.");
        }
        method.forceAccessible();
        return method;
    }
}
