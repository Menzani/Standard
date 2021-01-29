module eu.menzani {
    requires jdk.unsupported;

    requires static net.bytebuddy;
    requires static jdk.management;
    requires static java.desktop;

    exports eu.menzani.atomic;
    exports eu.menzani.benchmark;
    exports eu.menzani.collection;
    exports eu.menzani.concurrent;
    exports eu.menzani.error;
    exports eu.menzani.io;
    exports eu.menzani.lang;
    exports eu.menzani.lang.mutable;
    exports eu.menzani.misc;
    exports eu.menzani.struct;
    exports eu.menzani.swing;
    exports eu.menzani.system;
    exports eu.menzani.time;
}
