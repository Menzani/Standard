package eu.menzani.lang.caller;

import eu.menzani.error.GlobalStackTraceFilter;
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
    private final String siblingInternalName;
    private final String internalName;
    private final String fullName;

    private final ClassWriter writer = new ClassWriter(0);

    GeneratedClass(Class<?> sibling) {
        this.sibling = sibling;
        siblingInternalName = Type.getInternalName(sibling);
        String simpleName = "$Caller" + id.increment(sibling);
        internalName = siblingInternalName + simpleName;
        fullName = sibling.getName() + simpleName;
    }

    String getSiblingInternalName() {
        return siblingInternalName;
    }

    ClassWriter createClassWriter(String[] interfaces) {
        writer.visit(V11, ACC_PUBLIC | ACC_SUPER, internalName, null, "java/lang/Object", interfaces);

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

    T createInstance() {
        Class<?> generated = Unsafe.defineClass(internalName, writer.toByteArray(), sibling);

        GlobalStackTraceFilter.getInstance().addMethodToRemove(fullName, "call");

        return Lang.newInstanceCast(generated);
    }
}
