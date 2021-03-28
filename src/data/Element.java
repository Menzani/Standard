package eu.menzani.data;

import eu.menzani.object.PoolObject;

public abstract class Element implements PoolObject {
    protected Element() {
    }

    public Element getElement(Path path) {
        java.lang.String[] strings = path.getStrings();
        int[] ints = path.getInts();

        Element result = this;
        for (int i = 0; i < strings.length; i++) {
            if (Path.isVoid(strings[i])) {
                result = ((Array) result).getElement(ints[i]);
            } else {
                assert Path.isVoid(ints[i]);
                result = ((Object) result).getElement(strings[i]);
            }
        }
        return result;
    }
}
