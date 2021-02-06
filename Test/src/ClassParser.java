package eu.menzani.test;

import eu.menzani.misc.EndVisit;
import net.bytebuddy.jar.asm.ClassReader;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.Opcodes;

class ClassParser extends ClassVisitor {
    ClassParser() {
        super(Opcodes.ASM4);
    }

    ParsedClassFile parse(byte[] bytecode) {
        ClassReader reader = new ClassReader(bytecode);
        try {
            reader.accept(this, 0);
        } catch (EndVisit ignored) {
        }
        parsed.setBytecode(bytecode);
        return parsed;
    }

    private ParsedClassFile parsed;

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        parsed = new ParsedClassFile(name, superName, interfaces);
        throw EndVisit.INSTANCE;
    }
}
