package eu.menzani.lang;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.security.ProtectionDomain;
import java.util.Map;

public class ByteBuddyClassLoadingStrategy implements ClassLoadingStrategy<ClassLoader> {
    public static final ByteBuddyClassLoadingStrategy INHERIT_PROTECTION_DOMAIN = new ByteBuddyClassLoadingStrategy(null);

    private final ProtectionDomain protectionDomain;

    public ByteBuddyClassLoadingStrategy(ProtectionDomain protectionDomain) {
        this.protectionDomain = protectionDomain;
    }

    @Override
    public Map<TypeDescription, Class<?>> load(ClassLoader classLoader, Map<TypeDescription, byte[]> types) {
        return new ByteBuddyClassInjector(classLoader, protectionDomain).inject(types);
    }
}
