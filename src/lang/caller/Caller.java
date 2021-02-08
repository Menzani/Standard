package eu.menzani.lang.caller;

import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Type;

import java.lang.reflect.Method;

import static net.bytebuddy.jar.asm.Opcodes.*;

public class Caller {
    private static final String[] staticObjectVoidInterfaces = {Type.getInternalName(StaticObjectVoid.class)};
    private static final String[] instanceVoidVoidInterfaces = {Type.getInternalName(InstanceVoidVoid.class)};

    public static StaticObjectVoid ofConstructorVoid(Class<?> clazz) {
        GeneratedClass<StaticObjectVoid> generatedClass = new GeneratedClass<>(clazz);
        String className = generatedClass.getClassName();

        ClassWriter writer = generatedClass.createClassWriter(staticObjectVoidInterfaces);

        MethodVisitor methodVisitor = writer.visitMethod(ACC_PUBLIC, "call", "()Ljava/lang/Object;", null, null);
        methodVisitor.visitCode();
        Label label = new Label();
        methodVisitor.visitLabel(label);
        methodVisitor.visitLineNumber(6, label);
        methodVisitor.visitTypeInsn(NEW, className);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, className, "<init>", "()V", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();

        return generatedClass.createInstance();
    }

    public static InstanceVoidVoid ofInstanceVoidVoid(Method method) {
        GeneratedClass<InstanceVoidVoid> generatedClass = new GeneratedClass<>(method.getDeclaringClass());
        String className = generatedClass.getClassName();
        String methodName = method.getName();

        ClassWriter writer = generatedClass.createClassWriter(instanceVoidVoidInterfaces);

        MethodVisitor methodVisitor = writer.visitMethod(ACC_PUBLIC, "call", "(Ljava/lang/Object;)V", null, null);
        methodVisitor.visitCode();
        Label label = new Label();
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

        return generatedClass.createInstance();
    }
}
