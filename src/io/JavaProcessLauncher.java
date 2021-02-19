package eu.menzani.io;

import eu.menzani.struct.JVMOption;
import eu.menzani.struct.MemorySize;
import eu.menzani.system.RuntimeProperty;
import eu.menzani.system.SystemPaths;

public class JavaProcessLauncher extends ProcessLauncher {
    public JavaProcessLauncher() {
        super(SystemPaths.JAVA_RUNTIME.toString());
    }

    public void addProperty(RuntimeProperty runtimeProperty) {
        addArgument(runtimeProperty.toString());
    }

    public void addProperties(RuntimeProperty... runtimeProperties) {
        for (RuntimeProperty runtimeProperty : runtimeProperties) {
            addProperty(runtimeProperty);
        }
    }

    public void addJVMOption(JVMOption jvmOption) {
        addArgument(jvmOption.toString());
    }

    public void addJVMOptions(JVMOption... jvmOptions) {
        for (JVMOption jvmOption : jvmOptions) {
            addJVMOption(jvmOption);
        }
    }

    public void setMinimumHeap(MemorySize value) {
        addJVMOption(JVMOption.xms(value));
    }

    public void setMaximumHeap(MemorySize value) {
        addJVMOption(JVMOption.xmx(value));
    }

    public void setFixedHeap(MemorySize value) {
        setMinimumHeap(value);
        setMaximumHeap(value);
    }
}
