package com.allen.onlyweather.utils;

import java.util.Collection;

/**
 * Created by Allen.
 */

public class Check {

    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.length() == 0;
    }
    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }
}
