package eu.menzani.lang.caller;

import eu.menzani.lang.Lang;
import eu.menzani.struct.ConcurrentKeyedCounter;
import eu.menzani.struct.KeyedCounter;
import eu.menzani.system.Unsafe;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Type;

import static net.bytebuddy.jar.asm.Opcodes.*;

class GeneratedClass<T> {
    private static final KeyedCounter<Class<?>> id = new ConcurrentKeyedCounter<>();

    private final Class<?> sibling;
    private final String className;
    private final String callerClassName;
    private final ClassWriter writer = new ClassWriter(0);

    GeneratedClass(Class<?> sibling) {
        this.sibling = sibling;
        className = Type.getInternalName(sibling);
        callerClassName = className + "$Caller" + id.increment(sibling);
    }

    String getClassName() {
        return className;
    }

    ClassWriter createClassWriter(String[] interfaces) {
        writer.visit(V11, ACC_PUBLIC | ACC_SUPER, callerClassName, null, "java/lang/Object", interfaces);

        MethodVisitor methodVisitor = writer.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        Label label = new Label();
        methodVisitor.visitLabel(label);
        methodVisitor.visitLineNumber(3, label);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();

        return writer;
    }

    @SuppressWarnings("unchecked")
    T createInstance() {
        Class<?> generated = Unsafe.defineClass(callerClassName, writer.toByteArray(), sibling);
        return (T) Lang.newInstance(generated);
    }
}
