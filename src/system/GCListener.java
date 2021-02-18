package eu.menzani.system;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

public interface GCListener {
    void onTriggered(GarbageCollectionNotificationInfo notification, GcInfo info);
}
