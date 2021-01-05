module eu.menzani {
    requires jdk.unsupported;

    requires static net.bytebuddy;
    requires static jdk.management;
    requires static java.desktop;
    requires static javafx.graphics;

    exports eu.menzani.atomic;
    exports eu.menzani.benchmark;
    exports eu.menzani.buffer;
    exports eu.menzani.concurrent;
    exports eu.menzani.error;
    exports eu.menzani.io;
    exports eu.menzani.javafx;
    exports eu.menzani.lang;
    exports eu.menzani.lang.mutable;
    exports eu.menzani.misc;
    exports eu.menzani.struct;
    exports eu.menzani.swing;
    exports eu.menzani.system;
}
