package eu.menzani.lang.caller;

import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.ConcurrentKeyedCounter;
import eu.menzani.struct.KeyedCounter;
import eu.menzani.system.Unsafe;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Type;

import java.lang.reflect.Method;

import static net.bytebuddy.jar.asm.Opcodes.*;

public class Caller {
    private static final KeyedCounter<Class<?>> id = new ConcurrentKeyedCounter<>();
    private static final String[] instanceVoidVoidInterfaces = {Type.getInternalName(InstanceVoidVoid.class)};

    public static InstanceVoidVoid ofInstanceVoidVoid(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        String className = Type.getInternalName(clazz);
        String callerClassName = className + "$Caller" + id.increment(clazz);
        String methodName = method.getName();

        ClassWriter writer = new ClassWriter(0);
        writer.visit(V11, ACC_PUBLIC | ACC_SUPER, callerClassName, null, "java/lang/Object", instanceVoidVoidInterfaces);

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

        methodVisitor = writer.visitMethod(ACC_PUBLIC, "call", "(Ljava/lang/Object;)V", null, null);
        methodVisitor.visitCode();
        label = new Label();
        methodVisitor.visitLabel(label);
        methodVisitor.visitLineNumber(6, label);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitTypeInsn(CHECKCAST, className);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, className, methodName, "()V", false);
        label = new Label();
        methodVisitor.visitLabel(label);
        methodVisitor.visitLineNumber(7, label);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 2);
        methodVisitor.visitEnd();

        Class<?> generatedClass = Unsafe.defineClass(callerClassName, writer.toByteArray(), clazz);
        try {
            return (InstanceVoidVoid) generatedClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new UncaughtException(e);
        }
    }
}
