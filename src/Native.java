package eu.menzani;

import eu.menzani.lang.Lang;
import eu.menzani.misc.NativeLibrary;
import eu.menzani.struct.AppInfo;

public class Native {
    public static void init() {
    }

    static {
        AppInfo.setModuleDisplayName(Lang.EU_MENZANI_MODULE, Standard.NAME);
        NativeLibrary nativeLibrary = new NativeLibrary(2278256207267942924L, Lang.EU_MENZANI_MODULE);
        nativeLibrary.load();
    }
}
