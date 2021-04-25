package eu.menzani;

import eu.menzani.build.IdeaProject;

public class Standard {
    static final String NAME = "Standard";

    public static boolean isNotLibrary() {
        return IsNotLibrary.value;
    }

    private static class IsNotLibrary {
        static final boolean value;

        static {
            IdeaProject current = IdeaProject.current();
            if (current == null) {
                value = false;
            } else {
                value = current.isNotLibrary(NAME);
            }
        }
    }
}
