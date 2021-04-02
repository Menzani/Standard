package eu.menzani.data;

import eu.menzani.object.PoolObject;

public abstract class Element implements PoolObject {
    protected Element() {
    }

    public Element getElement(Path path) {
        Element result = this;
        for (int i = 0; i < path.length(); i++) {
            if (path.isString(i)) {
                result = ((Object) result).getElement(path.getString(i));
            } else {
                result = ((Array) result).getElement(path.getInt(i));
            }
        }
        return result;
    }
}
