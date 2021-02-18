package eu.menzani.system;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import eu.menzani.InternalUnsafe;
import eu.menzani.lang.StreamBuffer;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.ConcurrentObjectToggle;
import eu.menzani.struct.ObjectToggle;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Garbage {
    private static final ValuesSumAction valuesSumAction = new ValuesSumAction();
    private static final ObjectToggle<JVMListener> jvmListener = new ConcurrentObjectToggle<>();

    public static void freeMemory(long address) {
        InternalUnsafe.UNSAFE.freeMemory(address);
    }

    public static void freeMemory(Object object, long... addresses) {
        Cleaner.value.register(object, new FreeMemory(addresses));
    }

    public static void logGCs() {
        addGCListener(new LoggerGCListener());
    }

    public static long sumValuesToMB(Map<String, MemoryUsage> memoryUsage) {
        return sumValues(memoryUsage) / (1024L * 1024L);
    }

    public static long sumValues(Map<String, MemoryUsage> memoryUsage) {
        synchronized (valuesSumAction) {
            valuesSumAction.total = 0L;
            memoryUsage.values().forEach(valuesSumAction); // Should create no garbage
            return valuesSumAction.total;
        }
    }

    public static void addGCListener(GCListener listener) {
        if (jvmListener.toggleSet()) {
            MBeanServer platformServer = ManagementFactory.getPlatformMBeanServer();
            JVMListener jvmListener = new JVMListener();
            try {
                for (GarbageCollectorMXBean garbageCollectorMX : ManagementFactory.getGarbageCollectorMXBeans()) {
                    platformServer.addNotificationListener(garbageCollectorMX.getObjectName(), jvmListener, null, null);
                }
            } catch (InstanceNotFoundException e) {
                throw new UncaughtException(e);
            }
            Garbage.jvmListener.set(jvmListener);
        }
        jvmListener.get().addUserListener(listener);
    }

    private static class ValuesSumAction implements Consumer<MemoryUsage> {
        long total;

        @Override
        public void accept(MemoryUsage memoryUsage) {
            total += memoryUsage.getUsed();
        }
    }

    private static class Cleaner {
        static final java.lang.ref.Cleaner value = java.lang.ref.Cleaner.create();
    }

    private static class FreeMemory implements Runnable {
        private final long[] addresses;

        FreeMemory(long[] addresses) {
            this.addresses = addresses;
        }

        @Override
        public void run() {
            for (long address : addresses) {
                freeMemory(address);
            }
        }
    }

    private static class LoggerGCListener implements GCListener {
        private static final StreamBuffer buffer = StreamBuffer.standardOutput(128);

        @Override
        public void onTriggered(GarbageCollectionNotificationInfo notification, GcInfo info) {
            buffer.reset();
            buffer.builder.append('[');
            buffer.builder.append(notification.getGcName());
            buffer.builder.append("] ");
            buffer.builder.append(notification.getGcAction());
            buffer.builder.append(":  ");
            buffer.builder.append(sumValuesToMB(info.getMemoryUsageBeforeGc()));
            buffer.builder.append(" MB -> ");
            buffer.builder.append(sumValuesToMB(info.getMemoryUsageAfterGc()));
            buffer.builder.append(" MB  ");
            buffer.builder.append(info.getDuration());
            buffer.builder.append("ms");
            buffer.println();
        }
    }

    private static class JVMListener implements NotificationListener {
        private final List<GCListener> userListeners = new CopyOnWriteArrayList<>();

        void addUserListener(GCListener userListener) {
            userListeners.add(userListener);
        }

        @Override
        public void handleNotification(Notification notification, Object handback) {
            GarbageCollectionNotificationInfo notificationInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            GcInfo gcInfo = notificationInfo.getGcInfo();
            for (int i = 0; i < userListeners.size(); i++) {
                userListeners.get(i).onTriggered(notificationInfo, gcInfo);
            }
        }
    }
}
